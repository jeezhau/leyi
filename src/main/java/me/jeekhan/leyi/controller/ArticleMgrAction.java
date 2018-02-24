package me.jeekhan.leyi.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.core.util.StringUtils;

import me.jeekhan.leyi.common.ErrorCodesPropUtil;
import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.common.SysPropUtil;
import me.jeekhan.leyi.dto.Operator;
import me.jeekhan.leyi.model.ArticleBrief;
import me.jeekhan.leyi.model.ArticleDetail;
import me.jeekhan.leyi.model.ReviewApply;
import me.jeekhan.leyi.model.ReviewLog;
import me.jeekhan.leyi.model.ThemeClass;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.service.ArticleService;
import me.jeekhan.leyi.service.ReviewCheck;
import me.jeekhan.leyi.service.ThemeService;
import me.jeekhan.leyi.service.UserService;
/**
 * 文章管理控制类
 * 1、显示用户的主题管理首页：/
 * 2、文章信息的展示：/(detail|review|edit)//{articleId}；
 * 3、文章信息的修改保存：/update/{articleId}；
 * 4、用户删除自己的文章：/delete/{articleId}；
 * 5、管理员审核文章：/approval/{articleId}；
 * @author Jee Khan
 *
 */
@Controller
@RequestMapping("/{username}/article_mgr")
@SessionAttributes({"currTheme","operator"})//在进入方法时将属性从session添加进model，退出方法时从model将属性添加进session
public class ArticleMgrAction {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ThemeService themeService;
	@Autowired
	private UserService userService;
	@Autowired
	private ReviewCheck reviewCheck;
	
	private static String REDIRECT_SYS_ERROR_PAGE = "redirect:" + SysPropUtil.getParam("SYSTEM_ERROR_PAGE");	//跳转至错误页面
	/**
	 * 显示文章详细信息
	 * 根据访问请求模式进行相应的权限控制和数据展示
	 * 【权限】
	 * 	1、用户自己：访问模式为“detail”或“edit”，显示详细信息；
	 *  2、管理员：访问模式为“detail”或“review”，显示详细信息；
	 *  3、其他人：访问模式为“detail”，用户文章已通过审核，则显示详细信息;否则返回错误提示“文章不存在或正在审核中...”,跳转至用户个人首页；
	 * 【功能说明】
	 * 	1.取文章信息，如果文章不存在则返回个人主页；
	 * 	2.保存显示模式（detail-详情显示；review-审核显示，edit-编辑）；
	 *  3.根据用户的相应权限完成文章信息内容显示;
	 *  4.如果访问模式是"review",但是当前登录用户不是审核管理员或未登录，则将返回个人主页；
	 *  5.如果访问模式是"edit",但是当前登录用户不是用户自己用户或未登录，则将返回个人主页；
	 *  6."edit"模式包括新增和修改，新增时“articleId”为0，修改时“articleId”为目标文章ID；
	 * 【输入输出】
	 * @param username	访问的目标用户
	 * @param mode	访问模式
	 * @param articleId	文章ID
	 * @param map	返回数据集
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/{mode}/{articleId}",method=RequestMethod.GET)
	public String showArticle(@PathVariable("username")String username,@PathVariable("mode")String mode,
			@PathVariable("articleId")Long articleId,Map<String,Object> map) throws UnsupportedEncodingException{
		//目标用户检查
		UserFullInfo targetUser = (UserFullInfo) userService.getUserFullInfo(username);
		if(targetUser == null) {//无该用户
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("操作错误：用户【"+ username +"】不存在！","utf-8");	//跳转至系统主页
		}
		//模式检查
		if(!("detail".equals(mode) || "review".equals(mode) || "edit".equals(mode))) {//访问模式非法
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("操作错误：系统无该操作【"+ mode +"】！","utf-8");	//跳转到个人首页
		}
		//审核模式权限验证
		if("review".equals(mode)){
			Operator operator = (Operator) map.get("operator");
			//if(operator == null || operator.getLevel() < 9){//非管理员或未登录
				return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您无权执行该操作！","utf-8");	//跳转到个人首页
			//}
		}
		//编辑模式验证
		if("edit".equals(mode)) {
			Operator operator = (Operator) map.get("operator");
			if(operator == null || !operator.getUsername().equals(username)) {//操作员非用户自己或未登录
				return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您无权执行该操作！","utf-8");	//跳转到个人首页
			}
		}
		//文章检查		
		ArticleBrief brief = null;
		ArticleDetail detail = null;
		if(articleId != 0) {//非新增则取文章内容
			brief = articleService.getArticleBref(articleId);
			detail = articleService.getArticleDetail(articleId);
			if(brief == null || brief.getOwnerId() != targetUser.getId()) {//文章不存在或文章不是该用户的
				return REDIRECT_SYS_ERROR_PAGE + "?error=" +URLEncoder.encode("操作错误：该文章不存在！","utf-8");
			}
		}else {
			brief = new ArticleBrief();
			brief.setId(0L);
		}
		map.put("brief", brief);
		map.put("detail", detail);
		
		map.put("mode", mode);
		map.put("targetUser", targetUser);
			
		if("edit".equals(mode)) {
			return "article/articleEdit";	//返回文章编辑页面
		}
		return "article/articleShow";	//返回文章详情显示页面
		
	}

	/**
	 * 保存文章
	 * 【权限】
	 * 	1.登录用户
	 * 【功能说明】
	 * 	1、保存用户新增的文章；
	 *  2、保存用户的文章修改；
	 * 【输入输出】 
	 * @param username	目标用户名称
	 * @param brief		文章简介概要
	 * @param result4Brief	文章简介概要数据格式验证结果
	 * @param detail		文章详细内容
	 * @param result4Detail	文章详细内容数据验证结果
	 * @param map
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String saveArticle(@PathVariable("username")String username,@Valid ArticleBrief brief,BindingResult result4Brief,
			@Valid ArticleDetail detail,BindingResult result4Detail,@ModelAttribute("operator")Operator operator,Map<String,Object> map) throws UnsupportedEncodingException{
		//目标用户检查
		UserFullInfo targetUser = userService.getUserFullInfo(username);
		if(targetUser == null) {//用户不存在
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("操作错误：用户【"+ username +"】不存在！","utf-8");	//跳转至系统错误页面
		}
		if(operator == null || targetUser.getId() != operator.getUserId()) {	//目标用户与当前登录用户不一致
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您未登录或访问路径错误！","utf-8");	//跳转至系统错误页面
		}
		
		map.put("brief", brief);//返回提交的信息，错误回显使用
		map.put("detail", detail);
		
		//格式验证错误处理
		if(result4Brief.hasErrors() || result4Detail.hasErrors()){	//验证出错
			List<ObjectError> list = result4Brief.getAllErrors();
			for(ObjectError e :list){
				String field = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);	//字段名称
				map.put("valid."+field, e.getDefaultMessage());
			}
			List<ObjectError> list2 = result4Detail.getAllErrors();
			for(ObjectError e :list2){
				String field = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);	//字段名称
				map.put("valid."+field, e.getDefaultMessage());
			}
			return "article/articleEdit";	//返回文章编辑页面
		}

		//主题检查
		Long themeId = brief.getThemeId();
		ThemeClass theme = themeService.getTheme(themeId);
		if(theme == null || theme.getOwnerId() != operator.getUserId()) {//主题与用户的归属错误
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您没有该主题！","utf-8");	//跳转至系统错误页面
		}
		//文章检查
		if(brief.getId()>0){//文章修改
			ArticleBrief old = articleService.getArticleBref(brief.getId());
			if(old == null || old.getOwnerId() != operator.getUserId()){ //无该文章或非属主
				return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您无权限执行该操作！","utf-8");
			}
		}
		//数据处理保存
		if(detail == null ) {
			detail = new ArticleDetail();
			if(detail.getContent() == null) {
				detail.setContent("");
			}else {
				detail.setContent(detail.getContent().trim());
			}
		}
		brief.setOwnerId(operator.getUserId());
		Long id = articleService.saveArticleInfo(brief, detail);
		if(id <= 0){//保存失败
			map.put("error", ErrorCodesPropUtil.getParam(id.toString()));//添加保存错误信息
			return "article/articleEdit";	//返回文章编辑页面
		}else {
			return "redirect:/" + username +"/article_mgr/?condParams=" 
					+ URLEncoder.encode("{\"themeId\":" + "\"" + theme.getId() + "\"}", "utf-8");	//跳转至文章管理主页
		}
	}
	
	/**
	 * 删除指定文章
	 * 【权限】
	 * 1、登录用户自己
	 * 【功能说明】
	 * 
	 * 【输入输出】
	 * @param username	目标用户名称
	 * @param articleId	文章ID
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/delete/{articleId}",method=RequestMethod.GET)
	public String deleteArticle(@PathVariable("username")String username,@PathVariable("articleId")Long articleId,
			@ModelAttribute("operator")Operator operator,Map<String,String> map) throws UnsupportedEncodingException{
		//目标用户检查
		UserFullInfo targetUser = userService.getUserFullInfo(username);
		if(targetUser == null) {//用户不存在
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("操作错误：用户【"+ username +"】不存在！","utf-8");	//跳转至系统错误页面
		}
		if(operator == null || targetUser.getId() != operator.getUserId()) {	//目标用户与当前登录用户不一致
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您未登录或访问路径错误！","utf-8");	//跳转至系统错误页面
		}
		//文章检查
		ArticleBrief old = articleService.getArticleBref(articleId);
		if(old == null || old.getOwnerId() != operator.getUserId()){ //无该文章或非属主
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您没有该文章！","utf-8");
		}
		//文章删除
		Long id = articleService.deleteArticle(articleId);
		if(id <= 0){//保存失败
			return "redirect:/" + username +"/article_mgr/" 
				+ "?error=" + URLEncoder.encode(ErrorCodesPropUtil.getParam(id.toString()),"utf-8");	//添加保存错误信息
		}else {
			return "redirect:/" + username +"/article_mgr/?condParams="
				+ URLEncoder.encode("{\"themeId\":" + "\"" + old.getThemeId() + "\"}", "utf-8");	//跳转至文章管理主页
		}
	}
	
	/**
	 * 文章审核
	 * 【权限】
	 * 	1、仅登录的管理员可执行该操作；
	 * 【功能说明】
	 *  1、判断审核的文章是否存在；
	 *  2、判断审核申请是否存在；
	 * @param articleId
	 * @param remark
	 * @param operator
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/approval/{articleId}",method=RequestMethod.POST)
	public String approvalArticle(@PathVariable("username")String username,@PathVariable("articleId")Long articleId,
			@Valid ReviewLog reviewLog,BindingResult result,
			@ModelAttribute("operator")Operator operator,Map<String,Object> map) throws UnsupportedEncodingException{
		String forwardUrl = "forward:/"+operator.getUsername()+"/article_mgr/review/" + articleId;	//转发至文章审核页面
		//目标用户检查
		UserFullInfo targetUser = userService.getUserFullInfo(username);
		if(targetUser == null) {//用户不存在
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("操作错误：用户【"+ username +"】不存在！","utf-8");	//跳转至系统错误页面
		}
//		if(operator == null){ //无权限
//			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您未登录或无权限执行该操作！","utf-8");
//		}
		//审批信息验证结果处理
		if(result.hasErrors()){	//
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError e :list){
				String filed = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);
				map.put("valid." + filed, e.getDefaultMessage());
			}			
			return forwardUrl;	//返回审批页面
		}
		//拒绝则意见不可为空
		if("R".equals(reviewLog.getStatus()) && (StringUtils.isNullOrEmpty(reviewLog.getReviewInfo()) || reviewLog.getReviewInfo().trim().length()<1)){ //审核说明为空
			map.put("valid.reviewInfo", "审批意见：不可为空！");
			return forwardUrl;
		}
		//文章检查
		ArticleBrief brief = articleService.getArticleBref(articleId);
		if(brief == null){ //无该文章
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("操作错误：系统中无该文章信息！","utf-8");
		}
		if(!"0".equals(brief.getStatus())) {
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("操作错误：该文章未处于待审核状态！","utf-8");
		}
		//审批申请检查
		ReviewApply  reviewApply = reviewCheck.getReviewApply(reviewLog.getApplyId());
		if(reviewApply == null || !"0".equals(reviewApply.getStatus())) {
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("操作错误：系统中无指定的待审核用户！","utf-8");
		}
		//审批结果保存
		reviewLog.setReviewOpr(operator.getUserId());
		Long id = articleService.reviewArticle(articleId,reviewLog);
		if(id <= 0){//审批失败
			return "redirect:/"+operator.getUsername()+ "/review/" 
				+ "?error=" + URLEncoder.encode(ErrorCodesPropUtil.getParam(id.toString()),"utf-8");	//添加审批错误信息
		}else {
			return "redirect:/"+operator.getUsername()+ "/review/";	//跳转至审核页面
		}
	}

	/**
	 * 文章管理首页
	 * 【权限】
	 *  1、登录用户自己
	 * 【功能说明】
	 * @param username 目标用户名
	 * @param condParams	查询条件JSON
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String accessArticlesMgr(@PathVariable("username")String username,String condParams,
			Map<String,Object> map,HttpSession session) throws JsonParseException, JsonMappingException, IOException{
		Operator operator = (Operator) map.get("operator");
		//用户权限控制
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		
		if(operator == null || userInfo == null || userInfo.getId() != operator.getUserId()) {
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您无权限执行该操作！","utf-8");
		}
		//查询条件参数解析
		ObjectMapper jsonObj = new ObjectMapper();
		Map<String,Object> params = new HashMap<String,Object>();
		if(condParams != null && condParams.length()>0) {
			condParams = URLDecoder.decode(condParams, "utf-8");
			params = jsonObj.readValue(condParams, HashMap.class);
		}
		Long themeId = null;
		try{
			if(params.get("themeId") != null && ((String)params.get("themeId")).length()>0) {
				themeId = new Long((String) params.get("themeId"));
			}
		}catch(Exception e) {
			themeId = null;
		}
		params.put("themeId", themeId);
		//主题检查
		ThemeClass currTheme = null;
		if(themeId != null) {
			currTheme = themeService.getTheme(themeId);		
			if(currTheme == null || currTheme.getOwnerId() != operator.getUserId()) {
				return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您没有该主题！","utf-8");
			}
		}
		//创建主题树链（从顶级到当前级）			
		List<ThemeClass> themeTreeUp = new ArrayList<ThemeClass>();	
		String parentSeq = "/";
		if(currTheme != null) {
			parentSeq = currTheme.getParentSeq();
		}
		String[] arrThemeIds = parentSeq.split("/");
		for(int i=1;i<arrThemeIds.length;i++) {
			ThemeClass theme = themeService.getTheme(new Long(arrThemeIds[i]));
			themeTreeUp.add(theme);
		}
		themeTreeUp.add(currTheme);
		
		//当前主题的直接下级主题
		String themeSeq = ("/".equals(parentSeq)?"":parentSeq) + "/" + (themeId==null?"":themeId);
		List<ThemeClass> childrenThemes = themeService.getThemes(userInfo.getId(), themeSeq, true);
		//获取当前主题的文章
		PageCond pageCond = new PageCond();
		if(params.get("begin") != null) {
			try {
				int begin = new Integer((String)params.get("begin")).intValue();
				pageCond.setBegin(begin);
			}catch(Exception e) {
				;
			}
		}
		if(params.get("pageSize") != null) {
			try {
				int pageSize = new Integer((String)params.get("pageSize")).intValue();
				pageCond.setPageSize(pageSize);
			}catch(Exception e) {
				;
			}
		}
		int cnt = articleService.countArticlesByUser(userInfo.getId(), true, params);
		pageCond.setCount(cnt);
		List<ArticleBrief> articles = null;
		if(cnt>0) {
			articles = articleService.getArticlesByUser(userInfo.getId(), true, params, pageCond);
		}
		session.removeAttribute("childrenThemes");
		session.removeAttribute("themeTreeUp");
		session.removeAttribute("currTheme");
		session.removeAttribute("articles");
		map.put("themeTreeUp",themeTreeUp);
		map.put("childrenThemes", childrenThemes);
		map.put("currTheme", currTheme);
		map.put("articles", articles);
		map.put("pageCond", pageCond);
		return "article/articleMgr";
	}
}



