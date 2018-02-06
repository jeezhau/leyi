package me.jeekhan.leyi.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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

import me.jeekhan.leyi.common.ErrorCodesPropUtil;
import me.jeekhan.leyi.common.SysPropUtil;
import me.jeekhan.leyi.dto.Operator;
import me.jeekhan.leyi.model.ThemeClass;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.service.ThemeService;
import me.jeekhan.leyi.service.UserService;

/**
 * 主题管理相关访问控制类
 * 1、显示用户的主题管理首页：/、/theme/{themeId}；
 * 2、用户添加自己的主题：add；
 * 3、用户修改自己的主题：update；
 * 4、用户删除自己的主题：delete；
 * @author Jee Khan
 *
 */
@RequestMapping("/{username}/theme_mgr")
@Controller
@SessionAttributes({"operator","themeTreeUp","childrenThemes","currTheme"})
public class ThemeMgrAction {
	@Autowired
	private ThemeService themeService;
	@Autowired
	private UserService userService;

	private static String REDIRECT_SYS_ERROR_PAGE = "redirect:" + SysPropUtil.getParam("SYSTEM_ERROR_PAGE");	//跳转至错误页面
	/**
	 * 添加主题
	 * 【权限】
	 *  1、登录用户
	 * 【功能说明】
	 *  1、有Id则更新，无ID则新插；
	 *  2、同层下不可存在同名的主题；
	 * 【输入输出】
	 * @param username	用户名
	 * @param theme	待添加的主题信息
	 * @param result	主题信息格式验证结果
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addTheme(@PathVariable("username")String username,@Valid ThemeClass theme,BindingResult result,
			@ModelAttribute("operator") Operator operator,Map<String,Object> map){
		//用户权限控制
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		if(operator == null || userInfo == null || !userInfo.getUsername().equals(operator.getUsername())) {
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + "权限错误：您无权限执行该操作！";
		}
		map.put("mode", "add");	//添加模式信息，用于页面回显控制
		//上级主题ID序列格式验证
		String oriParentSeq = theme.getParentSeq();
		oriParentSeq = oriParentSeq.trim();
		if(!oriParentSeq.startsWith("/")) {
			oriParentSeq = "/" + oriParentSeq;
		}
		if(oriParentSeq.endsWith("/") && oriParentSeq.length()>1) {
			oriParentSeq = oriParentSeq.substring(0, oriParentSeq.length()-1);
		}
		String[] arrIds = oriParentSeq.split("/");
		for(int i=1;i<arrIds.length;i++) {
			try {
				new Long(arrIds[i]);
			}catch(Exception e) {
				map.put("valid.parentSeq", "上级主题ID序列格式不正确！");
				return "theme/themeMgr";		//跳转至主题管理首页
			}
		}
		theme.setParentSeq(oriParentSeq);
		
		//主题信息格式验证结果处理
		if(result.hasErrors()){
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError e :list){
				String field = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);
				map.put("valid."+field, e.getDefaultMessage());
			}
			return "theme/themeMgr";		//返回主题管理首页
		}
		
		//主题信息保存
		theme.setOwnerId(operator.getUserId());
		Long id = themeService.saveTheme(theme);
		if(id<=0){//保存失败
			String errMsg = ErrorCodesPropUtil.getParam(id.toString());
			map.put("error", errMsg);
			map.put("theme", theme);
			return "theme/themeMgr";
		}
		//更新session
		if("/".equals(theme.getParentSeq())) {
			operator.setTopThemes(themeService.getThemes(userInfo.getId(), "/", true));
		}
		//设置返回
		String redirectUrl = "redirect:/" + operator.getUsername() + "/theme_mgr/";	//跳转至主题管理首页
		redirectUrl += "theme/" + id;//返回当级主题显示页面
		return redirectUrl;
	}
	
	/**
	 * 更新主题
	 * 【权限】
	 *  1、登录用户
	 * 【功能说明】
	 *  1、验证权限：仅拥有者可更新；
	 * @param theme
	 * @param operator
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String updateTheme(@PathVariable("username")String username,@Valid ThemeClass theme,BindingResult result,
			@ModelAttribute("operator") Operator operator,Map<String,Object> map) throws UnsupportedEncodingException{
		//用户权限控制
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		if(operator == null || userInfo == null || !userInfo.getUsername().equals(operator.getUsername())) {
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您无权限执行该操作！","utf-8");
		}
		map.put("mode", "update");	//添加模式信息，用于页面回显控制
		//主题验证
		ThemeClass old = themeService.getTheme(theme.getId());
		if(theme == null || theme.getId()==null || old == null || old.getOwnerId() != operator.getUserId()){
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("操作错误：您没有该指定主题！","utf-8");
		}
		
		//主题信息格式验证结果处理
		if(result.hasErrors()){
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError e :list){
				String field = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);
				map.put("valid."+field, e.getDefaultMessage());
			}
			return "theme/themeMgr";		//返回主题管理首页
		}
		//数据处理
		theme.setOwnerId(operator.getUserId());
		theme.setParentSeq(old.getParentSeq());
		//数据保存
		Long id = themeService.saveTheme(theme);
		if(id<=0){//出现错误
			String errMsg = ErrorCodesPropUtil.getParam(id.toString());
			map.put("error", errMsg);
			map.put("theme", theme); 
			return "theme/themeMgr";	
		}
		//更新session
		if("/".equals(theme.getParentSeq())) {
			operator.setTopThemes(themeService.getThemes(userInfo.getId(), "/", true));
		}
		//设置返回
		String redirectUrl = "redirect:/" + operator.getUsername() + "/theme_mgr/";	//跳转至主题管理首页
		redirectUrl += "theme/" + id;//返回当级主题显示页面
		return redirectUrl;
	}
	
	/**
	 * 删除主题
	 * 【权限】
	 *  1、登录用户
	 * 【功能说明】
	 *  1、验证权限：仅拥有者可删除；
	 * @param themeId
	 * @param operator
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public String deleteTheme(@PathVariable("username")String username,ThemeClass theme,
			@ModelAttribute("operator") Operator operator,Map<String,Object> map) throws UnsupportedEncodingException{
		
		//用户权限控制
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		if(operator == null || userInfo == null || !userInfo.getUsername().equals(operator.getUsername())) {
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您无权限执行该操作！","utf-8");
		}
		if(theme == null || theme.getId()==null){
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("操作错误：您没有该指定主题！","utf-8");
		}
		//主题检查
		ThemeClass tmp = themeService.getTheme(theme.getId());
		if(tmp == null || tmp.getOwnerId() != operator.getUserId()){
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您无权限执行该操作！","utf-8");
		}
		//数据删除
		Long id = themeService.deleteTheme(theme.getId());
		if(id<=0){//出现错误
			String errMsg = ErrorCodesPropUtil.getParam(id.toString());
			map.put("mode", "delete");
			map.put("theme", theme);
			map.put("error", errMsg);
			return "theme/themeMgr";	
		}
		//更新session
		if("/".equals(tmp.getParentSeq())) {
			operator.setTopThemes(themeService.getThemes(userInfo.getId(), "/", true));
		}
		//设置返回路径
		String redirectUrl = "redirect:/" + operator.getUsername() + "/theme_mgr/";	//跳转至主题管理首页
		if(!"/".equals(tmp.getParentSeq())) {
			String[] ids = tmp.getParentSeq().split("/");
			redirectUrl += "theme/" + ids[ids.length-1];//返回上级主题显示页面
		}
		return redirectUrl;
	}
	
	/**
	 * 主题管理首页
	 * 【权限】
	 *  1、登录用户用户自己
	 * 【功能说明】
	 * 	1、取用户的全部一级主题信息
	 * @param username	目标用户名
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String accessArticlesMgr(@PathVariable("username")String username,Map<String,Object> map,HttpSession session) throws UnsupportedEncodingException{
		//用户权限控制
		Operator operator = (Operator) map.get("operator");
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		if(operator == null || userInfo == null || !userInfo.getUsername().equals(operator.getUsername())) {
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您无权限执行该操作！","utf-8");
		}
		
		//获取所有顶级主题
		List<ThemeClass> childrenThemes = themeService.getThemes(userInfo.getId(), "/", true);
		session.removeAttribute("childrenThemes");
		session.removeAttribute("themeTreeUp");
		session.removeAttribute("currTheme");
		map.put("childrenThemes", childrenThemes);
		map.put("themeTreeUp", null);
		map.put("currTheme", null);
		return "theme/themeMgr";
	}
	
	/**
	 * 文章管理首页：指定主题
	 * 【权限】
	 *  1、登录用户自己
	 * 【功能说明】
	 * @param username 目标用户名
	 * @param themeId 当前选中主题ID
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/theme/{themeId}",method=RequestMethod.GET)
	public String mgrThemes4Theme(@PathVariable("username")String username,@PathVariable("themeId")Long themeId,
			Map<String,Object> map,HttpSession session) throws UnsupportedEncodingException{
		Operator operator = (Operator) map.get("operator");
		//用户权限控制
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		
		if(operator == null || userInfo == null || userInfo.getId() != operator.getUserId()) {
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您无权限执行该操作！","utf-8");
		}
		//主题检查
		ThemeClass currTheme = themeService.getTheme(themeId);		
		if(currTheme == null || currTheme.getOwnerId() != operator.getUserId()) {
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您没有该主题！","utf-8");
		}
		//创建主题树链（从顶级到当前级）			
		List<ThemeClass> themeTreeUp = new ArrayList<ThemeClass>();	
		String parentSeq = currTheme.getParentSeq();
		String[] arrThemeIds = parentSeq.split("/");
		for(int i=1;i<arrThemeIds.length;i++) {
			ThemeClass theme = themeService.getTheme(new Long(arrThemeIds[i]));
			themeTreeUp.add(theme);
		}
		themeTreeUp.add(currTheme);
		
		//当前主题的直接下级主题
		String themeSeq = ("/".equals(currTheme.getParentSeq())?"":currTheme.getParentSeq()) + "/" + themeId;
		List<ThemeClass> childrenThemes = themeService.getThemes(userInfo.getId(), themeSeq, true);
		session.removeAttribute("childrenThemes");
		session.removeAttribute("themeTreeUp");
		session.removeAttribute("currTheme");
		map.put("themeTreeUp",themeTreeUp);
		map.put("childrenThemes", childrenThemes);
		map.put("currTheme", currTheme);
		
		return "theme/themeMgr";
	}
}


