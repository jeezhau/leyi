package me.jeekhan.leyi.controller.theme;

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
import me.jeekhan.leyi.model.ReviewInfo;
import me.jeekhan.leyi.model.ThemeInfo;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.service.ThemeService;
import me.jeekhan.leyi.service.UserService;

/**
 * 主题管理相关访问控制类
 * 1、显示用户的主题管理首页：/；
 * 2、用户添加自己的主题：add；
 * 3、用户修改自己的主题：update；
 * 4、用户删除自己的主题：delete；
 * 5、管理员审核主题通过：accept；
 * 6、管理员审核主题拒绝：refuse；
 * @author Jee Khan
 *
 */
@RequestMapping("/{username}/theme_mgr")
@Controller
@SessionAttributes("operator")
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
	public String addTheme(@PathVariable("username")String username,@Valid ThemeInfo theme,BindingResult result,
			@ModelAttribute("operator") Operator operator,Map<String,Object> map){
		String forwardUrl = "forward:/" + operator.getUsername() + "/theme_mgr/";	//转发至主题管理首页
		//用户权限控制
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		if(operator == null || userInfo == null || !userInfo.getUsername().equals(operator.getUsername())) {
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + "权限错误：您无权限执行该操作！";
		}
		map.put("mode", "add");	//添加模式信息，用于页面回显控制
		//主题信息格式验证结果处理
		if(result.hasErrors()){
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError e :list){
				String field = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);
				map.put("valid."+field, e.getDefaultMessage());
			}
			return forwardUrl;		//返回主题管理首页
		}
		//数据处理
		theme.setOwnerId(operator.getUserId());
		String oriParentId = theme.getParentId();
		oriParentId = oriParentId.trim();
		if(!oriParentId.startsWith("/")) {
			oriParentId = "/" + oriParentId;
		}
		if(oriParentId.endsWith("/") && oriParentId.length()>1) {
			oriParentId = oriParentId.substring(0, oriParentId.length()-1);
		}
		String[] arrIds = oriParentId.split("/");
		for(String strId:arrIds) {
			try {
				if(strId != null && strId.trim().length()>0) {
					new Long(strId);
				}
			}catch(Exception e) {
				map.put("valid.parentId", "上级主题ID序列格式不正确！");
				return forwardUrl;		//返回主题管理首页
			}
		}
		//主题信息保存
		Long id = themeService.saveTheme(theme);
		if(id>0){
			if("/".equals(theme.getParentId())) {
				operator.setTopThemes(themeService.getThemes(userInfo.getId(), "/", true));
			}
		}else{//保存失败
			String errMsg = ErrorCodesPropUtil.getParam(id.toString());
			map.put("error", errMsg);
		}
		return forwardUrl;		//返回主题管理首页
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
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String updateTheme(@PathVariable("username")String username,@Valid ThemeInfo theme,BindingResult result,
			@ModelAttribute("operator") Operator operator,Map<String,String> map){
		String forwardUrl = "forward:/" + operator.getUsername() + "/theme_mgr/";	//转发至主题管理首页
		//用户权限控制
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		if(operator == null || userInfo == null || !userInfo.getUsername().equals(operator.getUsername())) {
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + "权限错误：无权限执行该操作！";
		}
		map.put("mode", "update");	//添加模式信息，用于页面回显控制
		//主题信息格式验证结果处理
		if(result.hasErrors()){
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError e :list){
				String field = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);
				map.put("valid."+field, e.getDefaultMessage());
			}
			return forwardUrl;		//返回主题管理首页
		}
		//主题验证
		if(theme == null || theme.getId()==null){
			return REDIRECT_SYS_ERROR_PAGE + "?error=操作错误：没有指定主题！";
		}
		//
		ThemeInfo old = themeService.getTheme(theme.getId());		
		if(old != null && old.getOwnerId() == operator.getUserId()){
			//数据处理
			theme.setOwnerId(operator.getUserId());
			String oriParentId = theme.getParentId();
			oriParentId = oriParentId.trim();
			if(!oriParentId.startsWith("/")) {
				oriParentId = "/" + oriParentId;
			}
			if(oriParentId.endsWith("/") && oriParentId.length()>1) {
				oriParentId = oriParentId.substring(0, oriParentId.length()-1);
			}
			String[] arrIds = oriParentId.split("/");
			for(String strId:arrIds) {
				try {
					if(strId != null && strId.trim().length()>0) {
						new Long(strId);
					}
				}catch(Exception e) {
					map.put("valid.parentId", "上级主题ID序列格式不正确！");
					return forwardUrl;		//返回主题管理首页
				}
			}
			//数据保存
			Long id = themeService.saveTheme(theme);
			if(id>0){
				if("/".equals(theme.getParentId())) {
					operator.setTopThemes(themeService.getThemes(userInfo.getId(), "/", true));
				}
				return forwardUrl;
			}else{
				String errMsg = ErrorCodesPropUtil.getParam(id.toString());
				map.put("error", errMsg);
				return forwardUrl;		//返回主题管理首页
			}
		}else{	//他人主题
			return REDIRECT_SYS_ERROR_PAGE + "?error=权限错误：您无权限执行该操作！";
		}
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
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public String deleteTheme(@PathVariable("username")String username,ThemeInfo theme,
			@ModelAttribute("operator") Operator operator,Map<String,Object> map){
		String forwardUrl = "forward:/" + operator.getUsername() + "/theme_mgr/";	//转发至主题管理首页
		//用户权限控制
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		if(operator == null || userInfo == null || !userInfo.getUsername().equals(operator.getUsername())) {
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + "权限错误：无权限执行该操作！";
		}
		if(theme == null || theme.getId()==null){
			return REDIRECT_SYS_ERROR_PAGE + "?error=操作错误：没有指定主题！";
		}
		ThemeInfo tmp = themeService.getTheme(theme.getId());
		if(tmp!=null && tmp.getOwnerId() == operator.getUserId()){
			Long id = themeService.deleteTheme(theme.getId());
			if(id>0){
				//更新session
				if("/".equals(tmp.getParentId())) {
					operator.setTopThemes(themeService.getThemes(userInfo.getId(), "/", true));
				}
				return forwardUrl;
			}else{
				String errMsg = ErrorCodesPropUtil.getParam(id.toString());
				map.put("mode", "delete");
				map.put("error", errMsg);
				return forwardUrl;
			}
		}else{//他人主题
			return REDIRECT_SYS_ERROR_PAGE + "?error=权限错误：您无权限执行该操作！";
		}
	}
	
	/**
	 * 主题管理首页
	 * 【权限】
	 *  1、登录用户
	 * 【功能说明】
	 * 	1、取用户的全部主题信息
	 * @param username	目标用户名
	 * @return
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String accessUserThemes(@PathVariable("username")String username,@ModelAttribute("operator") Operator operator,Map<String,Object> map){
		//用户权限控制
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		if(operator == null || userInfo == null || !userInfo.getUsername().equals(operator.getUsername())) {
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + "权限错误：无权限执行该操作！";
		}
		return "themeMgr";
	}
	
	/**
	 * 主题审核：通过
	 * 【权限】
	 * 	1、仅登录的管理员可执行该操作；
	 * 【功能说明】
	 *  1、判断审核的主题是否存在；
	 *  2、执行审核通过
	 * @param themeId
	 * @param remark
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/accept",method=RequestMethod.POST)
	public String accept(Long themeId,String remark,@ModelAttribute("operator")Operator operator,Map<String,Object> map){
		String forwardUrl = "forward:/"+operator.getUsername()+"/theme_mgr/review/" + themeId;	//转发至主题审核页面
		if(operator == null || operator.getLevel() < 9){ //无权限
			return REDIRECT_SYS_ERROR_PAGE + "?error=您无权限执行该操作！";
		}
		//数据合规检查
		if(themeId == null){ //主题为空
			map.put("valid.themeId", "主题ID：不可为空！");
			return forwardUrl;
		}
		if(remark !=null && remark.trim().length()>600){
			map.put("valid.remark", "审批意见：不可超过600个字符！");
			return forwardUrl;
		}
		//主题检查
		ThemeInfo theme = themeService.getTheme(themeId);
		if(theme == null){ //无该主题
			return REDIRECT_SYS_ERROR_PAGE + "?error=操作错误：系统中无该主题信息！";
		}
		if(!"0".equals(theme.getStatus())) {
			return REDIRECT_SYS_ERROR_PAGE + "?error=操作错误：该主题未处于待审核状态！";
		}
		
		remark = remark.trim();
		ReviewInfo reviewInfo = new ReviewInfo();
		reviewInfo.setReviewInfo(remark);
		reviewInfo.setReviewOpr(operator.getUserId());
		Long id = themeService.reviewTheme(themeId, "A",reviewInfo);
		if(id <= 0){//审批失败
			return "redirect:/"+operator.getUsername()+ "/review/" + "?error=" + ErrorCodesPropUtil.getParam(id.toString());	//添加审批错误信息
		}else {
			return "redirect:/"+operator.getUsername()+ "/review/";	//跳转至审核页面
		}
	}
	
	/**
	 * 主题审核：拒绝
	 * 【权限】
	 * 	1、仅登录的管理员可执行该操作；
	 * 【功能说明】
	 *  1、判断审核的主题是否存在；
	 *  2、执行审核拒绝
	 * @param themeId
	 * @param remark
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/refuse",method=RequestMethod.POST)
	public String refuse(Long themeId,String remark,@ModelAttribute("operator")Operator operator,Map<String,Object> map){
		String forwardUrl = "forward:/"+operator.getUsername()+"/theme_mgr/review/" + themeId;	//转发至主题审核页面
		if(operator == null || operator.getLevel() < 9){ //无权限
			return REDIRECT_SYS_ERROR_PAGE + "?error=权限错误：您无权限执行该操作！";
		}
		//数据合规检查
		if(themeId == null ){ //主题ID为空
			map.put("valid.themeId", "主题ID：不可为空！");
			return forwardUrl;
		}
		if( remark == null || remark.trim().length()<6) {//审核说明为空
			map.put("valid.remark", "审批意见：不可为空或大于6个非空字符！");
			return forwardUrl;
		}
		if(remark.length()>600){
			map.put("valid.remark", "审批意见：审核说明不可超过600个字符！");
			return forwardUrl;
		}
		//主题检查
		ThemeInfo theme = themeService.getTheme(themeId);
		if(theme == null){ //无该主题
			return REDIRECT_SYS_ERROR_PAGE + "?error=操作错误：系统中无该主题信息！";
		}
		if(!"0".equals(theme.getStatus())) {
			return REDIRECT_SYS_ERROR_PAGE + "?error=操作错误：该主题未处于待审核状态！";
		}
		
		remark = remark.trim();
		ReviewInfo reviewInfo = new ReviewInfo();
		reviewInfo.setReviewInfo(remark);
		reviewInfo.setReviewOpr(operator.getUserId());
		Long id = themeService.reviewTheme(themeId, "R",reviewInfo);
		if(id <= 0){//审批失败
			return "redirect:/"+operator.getUsername()+ "/review/" + "?error=" + ErrorCodesPropUtil.getParam(id.toString());	//添加审批错误信息
		}else {
			return "redirect:/"+operator.getUsername()+ "/review/";	//跳转至审核页面
		}
	}
}


