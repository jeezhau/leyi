package me.jeekhan.leyi.controller.article;

import java.util.List;
import java.util.Map;

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

import me.jeekhan.leyi.common.ErrorCodesPropUtil;
import me.jeekhan.leyi.common.SysPropUtil;
import me.jeekhan.leyi.dto.Operator;
import me.jeekhan.leyi.model.ArticleBrief;
import me.jeekhan.leyi.model.ArticleDetail;
import me.jeekhan.leyi.model.ReviewInfo;
import me.jeekhan.leyi.model.ThemeInfo;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.service.ArticleService;
import me.jeekhan.leyi.service.ThemeService;
import me.jeekhan.leyi.service.UserService;
/**
 * 文章管理控制类
 * 1、显示用户的主题管理首页：/；
 * 2、文章信息的展示：detail,review,edit；
 * 3、文章信息的修改保存：update；
 * 4、用户删除自己的文章：delete；
 * 5、管理员审核文章通过：accept；
 * 6、管理员审核文章拒绝：refuse；
 * @author Jee Khan
 *
 */
@Controller
@RequestMapping("/{username}/article_mgr")
@SessionAttributes({"currTheme","operator","topThemes"})
public class ArticleMgrAction {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ThemeService themeService;
	@Autowired
	private UserService userService;
	
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
	 * 【输入输出】
	 * @param username	访问的目标用户
	 * @param mode	访问模式
	 * @param articleId	文章ID
	 * @param map	返回数据集
	 * @return
	 */
	@RequestMapping(value="/{mode}/{articleId}",method=RequestMethod.GET)
	public String showArticle(@PathVariable("username")String username,@PathVariable("mode")String mode,
			@PathVariable("articleId")Long articleId,Map<String,Object> map){
		//目标用户检查
		UserFullInfo targetUser = (UserFullInfo) userService.getUserFullInfo(username);
		if(targetUser == null) {//无该用户
			return REDIRECT_SYS_ERROR_PAGE + "?error=操作错误：用户【"+ username +"】不存在！";	//跳转至系统主页
		}
		//模式检查
		if(!("detail".equals(mode) || "review".equals(mode) || "edit".equals(mode))) {//访问模式非法
			return REDIRECT_SYS_ERROR_PAGE + "?error=操作错误：系统无该操作【"+ mode +"】！";	//跳转到个人首页
		}
		//审核模式权限验证
		if("review".equals(mode)){
			Operator operator = (Operator) map.get("operator");
			if(operator == null || operator.getLevel() < 9){//非管理员或未登录
				return REDIRECT_SYS_ERROR_PAGE + "?error=权限错误：您无权执行该操作！";	//跳转到个人首页
			}
		}
		//编辑模式验证
		if("edit".equals(mode)) {
			Operator operator = (Operator) map.get("operator");
			if(operator == null || !operator.getUsername().equals(username)) {//操作员非用户自己或未登录
				return REDIRECT_SYS_ERROR_PAGE + "?error=权限错误：您无权执行该操作！";	//跳转到个人首页
			}
		}
		//文章检查		
		ArticleBrief brief = articleService.getArticleBref(articleId);
		if(brief == null || brief.getOwnerId() != targetUser.getId()) {//文章不存在或文章不是该用户的
			return REDIRECT_SYS_ERROR_PAGE + "?error=操作错误：该文章不存在！";
		}

		map.put("mode", mode);
		map.put("brief", brief);
		map.put("targetUser", targetUser);
		ArticleDetail content = articleService.getArticleDetail(articleId);
		if(content != null){
			map.put("content", content);
		}
		if("edit".equals(mode)) {
			return "articleEdit";	//返回文章编辑页面
		}
		return "articleShow";	//返回文章详情显示页面
		
	}

	/**
	 * 保存新增文章
	 * 【权限】
	 * 	1.登录用户
	 * 【功能说明】
	 * 	保存用户新增的文章
	 * 【输入输出】 
	 * @param username	目标用户名称
	 * @param brief		文章简介概要
	 * @param result		文章详细内容
	 * @param content	文章内容
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addArticle(@PathVariable("username")String username,@Valid ArticleBrief brief,BindingResult result,
			String content,@ModelAttribute("operator")Operator operator,Map<String,Object> map){
		//目标用户检查
		UserFullInfo targetUser = userService.getUserFullInfo(username);
		if(targetUser == null) {//用户不存在
			return REDIRECT_SYS_ERROR_PAGE + "?error=操作错误：用户【"+ username +"】不存在！";	//跳转至系统错误页面
		}
		if(operator == null || targetUser.getId() != operator.getUserId()) {	//目标用户与当前登录用户不一致
			return REDIRECT_SYS_ERROR_PAGE + "?error=权限错误：您未登录或访问路径错误！";	//跳转至系统错误页面
		}
		//格式验证错误处理
		if(result.hasErrors()){	//验证出错
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError e :list){
				String field = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);
				map.put("valid."+field, e.getDefaultMessage());
			}
			return "articleEdit";	//返回文章编辑页面
		}
		if(content == null) {
			content = "";
		}
		content = content.trim();
		if(content.length()>10240){
			map.put("valid.content", "内容：最大长度为10K个字符！");
			return "articleEdit";	//返回文章编辑页面
		}
		//主题检查
		Long themeId = brief.getThemeId();
		ThemeInfo theme = themeService.getTheme(themeId);
		if(theme == null || theme.getOwnerId() != operator.getUserId()) {//主题与用户的归属错误
			return REDIRECT_SYS_ERROR_PAGE + "?error=权限错误：您没有该主题！";	//跳转至系统错误页面
		}
		//数据保存
		ArticleDetail articleDetail = null;
		if(content.length() > 0) {
			articleDetail = new ArticleDetail();
			articleDetail.setContent(content);
		}
		Long id = articleService.saveArticleInfo(brief, articleDetail);
		if(id <= 0){//保存失败
			map.put("error", ErrorCodesPropUtil.getParam(id.toString()));//添加保存错误信息
			return "articleEdit";	//返回文章编辑页面
		}else {
			return "redirect:/" + username +"/article_mgr/";	//跳转至文章管理主页
		}
	}
	
	/**
	 * 保存文章修改
	 * 【权限】
	 * 1、登录用户自己
	 * 【功能说明】
	 * 
	 * 【输入输出】
	 * @param username	目标用户信息
	 * @param brief		文章简介信息
	 * @param result		文章信息验证结果
	 * @param content	文章内容
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String editArticle(@PathVariable("username")String username,@Valid ArticleBrief brief,BindingResult result,
			String content,@ModelAttribute("operator")Operator operator,Map<String,String> map){
		//目标用户检查
		UserFullInfo targetUser = userService.getUserFullInfo(username);
		if(targetUser == null) {//用户不存在
			return REDIRECT_SYS_ERROR_PAGE + "?error=操作错误：用户【"+ username +"】不存在！";	//跳转至系统错误页面
		}
		if(operator == null || targetUser.getId() != operator.getUserId()) {	//目标用户与当前登录用户不一致
			return REDIRECT_SYS_ERROR_PAGE + "?error=权限错误：您未登录或访问路径错误！";	//跳转至系统错误页面
		}
		//文章字段格式验证结果处理
		if(result.hasErrors()){	//验证出错
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError e :list){
				String field = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);
				map.put("valid." + field, e.getDefaultMessage());
			}
			return "articleEdit";	//返回文章编辑页面
		}
		if(content == null) {
			content = "";
		}
		content = content.trim();
		if(content.length()>10240){
			map.put("valid.content", "内容：最大长度为10K个字符！");
			return "articleEdit";	//返回文章编辑页面
		}
		//主题检查
		Long themeId = brief.getThemeId();
		ThemeInfo theme = themeService.getTheme(themeId);
		if(theme == null || theme.getOwnerId() != operator.getUserId()) {//主题与用户的归属错误
			return REDIRECT_SYS_ERROR_PAGE + "?error=权限错误：您没有该主题！";	//跳转至系统错误页面
		}
		//文章检查
		ArticleBrief old = articleService.getArticleBref(brief.getId());
		if(old == null || old.getOwnerId() != operator.getUserId()){ //无该文章或非属主
			return REDIRECT_SYS_ERROR_PAGE + "?error=权限错误：您无权限执行该操作！";
		}
		//文章保存
		brief.setOwnerId(operator.getUserId());
		ArticleDetail articleDetail = null;
		if(content.length()>0) {
			articleDetail = 	new ArticleDetail();
			articleDetail.setContent(content);
		}
		Long id = articleService.saveArticleInfo(brief, articleDetail);
		if(id <= 0){//保存失败
			map.put("error", ErrorCodesPropUtil.getParam(id.toString()));//添加保存错误信息
			return "articleEdit";	//返回文章编辑页面
		}else {
			return "redirect:/" + username +"/article_mgr/";	//跳转至文章管理主页
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
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String deleteArticle(@PathVariable("username")String username,Long articleId,
			@ModelAttribute("operator")Operator operator,Map<String,String> map){
		//目标用户检查
		UserFullInfo targetUser = userService.getUserFullInfo(username);
		if(targetUser == null) {//用户不存在
			return REDIRECT_SYS_ERROR_PAGE + "?error=操作错误：用户【"+ username +"】不存在！";	//跳转至系统错误页面
		}
		if(operator == null || targetUser.getId() != operator.getUserId()) {	//目标用户与当前登录用户不一致
			return REDIRECT_SYS_ERROR_PAGE + "?error=权限错误：您未登录或访问路径错误！";	//跳转至系统错误页面
		}
		//文章检查
		ArticleBrief old = articleService.getArticleBref(articleId);
		if(old == null || old.getOwnerId() != operator.getUserId()){ //无该文章或非属主
			return REDIRECT_SYS_ERROR_PAGE + "?error=权限错误：您没有该文章！";
		}
		//文章删除
		Long id = articleService.deleteArticle(articleId);
		if(id <= 0){//保存失败
			return "redirect:/" + username +"/article_mgr/" + "?error=" + ErrorCodesPropUtil.getParam(id.toString());	//添加保存错误信息
		}else {
			return "redirect:/" + username +"/article_mgr/";	//跳转至文章管理主页
		}
	}
	
	/**
	 * 文章审核：通过
	 * 【权限】
	 * 	1、仅登录的管理员可执行该操作；
	 * 【功能说明】
	 *  1、判断审核的文章是否存在；
	 *  2、执行审核通过
	 * 【输入输出】
	 * @param username	目标用户名称
	 * @param articleId	待审核文章ID
	 * @param remark		审批意见
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/accept",method=RequestMethod.POST)
	public String accept(@PathVariable("username")String username,Long articleId,String remark,
			@ModelAttribute("operator")Operator operator,Map<String,Object> map){
		String forwardUrl = "forward:/"+operator.getUsername()+"/article_mgr/review/" + articleId;	//转发至文章审核页面
		//目标用户检查
		UserFullInfo targetUser = userService.getUserFullInfo(username);
		if(targetUser == null) {//用户不存在
			return REDIRECT_SYS_ERROR_PAGE + "?error=操作错误：用户【"+ username +"】不存在！";	//跳转至系统错误页面
		}
		if(operator == null || operator.getLevel() < 9){ //无权限
			return REDIRECT_SYS_ERROR_PAGE + "?error=权限错误：您未登录或无权限执行该操作！";
		}
		//数据合规检查
		if(articleId == null){ //文章为空
			map.put("valid.articleId", "文章ID：不可为空！");
			return forwardUrl;
		}
		if(remark !=null && remark.length()>600){
			map.put("valid.remark", "审批意见：不可超过600个字符！");
			return forwardUrl ;	//返回文章审核页面
		}
		//文章检查
		ArticleBrief brief = articleService.getArticleBref(articleId);
		if(brief == null){ //无该文章
			return REDIRECT_SYS_ERROR_PAGE + "?error=操作错误：系统中无该文章信息！";
		}
		if(!"0".equals(brief.getStatus())) {
			return REDIRECT_SYS_ERROR_PAGE + "?error=操作错误：该文章未处于待审核状态！";
		}
		
		ReviewInfo reviewInfo = new ReviewInfo();
		reviewInfo.setReviewInfo(remark);
		reviewInfo.setReviewOpr(operator.getUserId());
		Long id = articleService.reviewArticle(articleId,"A",reviewInfo);
		if(id <= 0){//审批失败
			return "redirect:/"+operator.getUsername()+ "/review/" + "?error=" + ErrorCodesPropUtil.getParam(id.toString());	//添加审批错误信息
		}else {
			return "redirect:/"+operator.getUsername()+ "/review/";	//跳转至审核页面
		}
	}
	
	/**
	 * 文章审核：拒绝
	 * 【权限】
	 * 	1、仅登录的管理员可执行该操作；
	 * 【功能说明】
	 *  1、判断审核的文章是否存在；
	 *  2、执行审核拒绝
	 * @param articleId
	 * @param remark
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/refuse",method=RequestMethod.POST)
	public String refuse(@PathVariable("username")String username,Long articleId,String remark,
			@ModelAttribute("operator")Operator operator,Map<String,Object> map){
		String forwardUrl = "forward:/"+operator.getUsername()+"/article_mgr/review/" + articleId;	//转发至文章审核页面
		//目标用户检查
		UserFullInfo targetUser = userService.getUserFullInfo(username);
		if(targetUser == null) {//用户不存在
			return REDIRECT_SYS_ERROR_PAGE + "?error=操作错误：用户【"+ username +"】不存在！";	//跳转至系统错误页面
		}
		if(operator == null || operator.getLevel() < 9){ //无权限
			return REDIRECT_SYS_ERROR_PAGE + "?error=权限错误：您未登录或无权限执行该操作！";
		}
		//数据合规检查
		if(articleId == null ){ //文章Id为空
			map.put("valid.articleId", "文章ID：不可为空！");
			return forwardUrl;
		}
		if(remark == null || remark.trim().length()<6){ //审批意见为空
			map.put("valid.remark", "审批意见：不可为空或大于6个非空字符！");
			return forwardUrl;
		}
		if(remark !=null && remark.length()>600){
			map.put("valid.remark", "审批意见：不可超过600个字符！");
			return forwardUrl ;	//返回文章审核页面
		}
		//文章检查
		ArticleBrief brief = articleService.getArticleBref(articleId);
		if(brief == null){ //无该文章
			return REDIRECT_SYS_ERROR_PAGE + "?error=操作错误：系统中无该文章信息！";
		}
		if(!"0".equals(brief.getStatus())) {
			return REDIRECT_SYS_ERROR_PAGE + "?error=操作错误：该文章未处于待审核状态！";
		}
		
		ReviewInfo reviewInfo = new ReviewInfo();
		reviewInfo.setReviewInfo(remark);
		reviewInfo.setReviewOpr(operator.getUserId());
		Long id = articleService.reviewArticle(articleId,"A",reviewInfo);
		if(id <= 0){//审批失败
			return "redirect:/"+operator.getUsername()+ "/review/" + "?error=" + ErrorCodesPropUtil.getParam(id.toString());	//添加审批错误信息
		}else {
			return "redirect:/"+operator.getUsername()+ "/review/";	//跳转至审核页面
		}
	}
	
	/**
	 * 文章管理首页
	 * 【权限】
	 *  1、登录用户自己
	 * 【功能说明】
	 * @param username	目标用户名
	 * @return
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String accessArticlesMgr(@PathVariable("username")String username,@ModelAttribute("operator") Operator operator,Map<String,Object> map){
		//用户权限控制
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		if(operator == null || userInfo == null || !userInfo.getUsername().equals(operator.getUsername())) {
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + "权限错误：无权限执行该操作！";
		}
		return "articleMgr";
	}

}



