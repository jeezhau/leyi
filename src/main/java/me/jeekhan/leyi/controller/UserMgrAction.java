package me.jeekhan.leyi.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.cj.core.util.StringUtils;

import me.jeekhan.leyi.common.ErrorCodesPropUtil;
import me.jeekhan.leyi.common.FileFilter;
import me.jeekhan.leyi.common.SunSHAUtils;
import me.jeekhan.leyi.common.SysPropUtil;
import me.jeekhan.leyi.dto.Operator;
import me.jeekhan.leyi.model.InviteCode;
import me.jeekhan.leyi.model.ReviewApply;
import me.jeekhan.leyi.model.ReviewLog;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.service.ReviewCheck;
import me.jeekhan.leyi.service.UserService;

/**
 * 个人信息管理控制类
 * 1、包括用户信息的展示：detail,review,edit；
 * 2、用户基本信息的修改保存：updateBasic；
 * 3、用户密码的修改保存：updatePwd；
 * 4、用户头像的修改保存：changePic；
 * 5、注册功能于 LoginAction 中
 * 6、用户审核结果保存：approval；
 * @author Jee Khan
 *
 */
@RequestMapping("/{username}/user_mgr")
@Controller
@SessionAttributes("operator")
public class UserMgrAction {
	@Autowired
	private UserService userService;
	@Autowired
	private ReviewCheck reviewCheck;
	
	private static String REDIRECT_SYS_ERROR_PAGE = "redirect:" + SysPropUtil.getParam("SYSTEM_ERROR_PAGE");	//跳转至错误页面
	/**
	 * 显示用户详细信息
	 * 根据访问请求模式进行相应的权限控制和数据展示
	 * 【权限说明】
	 * 	1、用户自己：访问模式为“detail”或“edit”,显示详细信息，包括邀请码；
	 *  2、管理员：访问模式为“detail”或“review”，显示详细信息；
	 *  3、其他人：用户如果已通过审核，则显示详细信息;否则返回错误提示“用户不存在或正在审核中...”,跳转至系统首页；
	 * 【功能说明】
	 * 	1.取用户信息，如果用户不存在则返回应用主页；
	 * 	2.保存显示模式（detail-详情显示；review-审核显示，edit-编辑）；
	 *  3.根据用户的相应权限完成用户信息内容显示;
	 *  4.如果访问模式是"review",但是当前登录用户不是审核管理员或未登录，则返回系统主页；
	 *  5.如果访问模式是“edit”，但是当前登录用户不是不是用户自己或用户未登录，则返回系统主页；
	 * @param mode	访问模式
	 * @param username	用户名
	 * @param map	返回数据集
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/{mode}",method=RequestMethod.GET)
	public String showUser(@PathVariable("mode")String mode,@PathVariable("username")String username,Map<String,Object> map) throws UnsupportedEncodingException{	
		if(!("detail".equals(mode) || "review".equals(mode) || "edit".equals(mode))) {//访问模式非法
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("操作错误：系统无该操作【"+ mode +"】！","utf-8");	
		}
		//目标用户检查
		UserFullInfo targetUser = userService.getUserFullInfo(username);
		if(targetUser == null){	//用户不存在或正在审核中...
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("用户【"+ username +"】不存在！","utf-8");	
		}
		//审核模式权限验证
		if("review".equals(mode)){
			Operator operator = (Operator) map.get("operator");
			if(operator == null || operator.getLevel() < 9){//非管理员或未登录
				return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("您无权执行该操作！","utf-8");	
			}
		}
		//编辑模式验证
		if("edit".equals(mode)) {
			Operator operator = (Operator) map.get("operator");
			if(operator == null || !operator.getUsername().equals(username)) {//非用户自己或未登录
				return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("您无权执行该操作！","utf-8");	
			}
		}
		
		map.put("mode", mode);
		map.put("targetUser", targetUser);
		if("edit".equals(mode)) {
			InviteCode inviteCode = userService.getAvailableCodeByUser(targetUser.getId());
			String code = (inviteCode == null) ? "" : inviteCode.getCode().toString();
			map.put("availCode", code);//取用户的可用邀请码
			return "user/userEdit";	//返回用户编辑页面
		}
		return "user/userShow";		//返回用户信息显示页面
	}
	

	/**
	 * 用户审核
	 * 【权限】
	 * 	1、仅登录的管理员可执行该操作；
	 * 【功能说明】
	 *  1、判断审核的用户是否存在；
	 *  2、执行审核通过
	 * @param username	待审批用户名
	 * @param reviewLog	审批信息
	 * @param operator
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/approval",method=RequestMethod.POST)
	public String approvalUser(@PathVariable("username")String username,@Valid ReviewLog reviewLog,BindingResult result,
			@ModelAttribute("operator")Operator operator,Map<String,Object> map) throws UnsupportedEncodingException{
		String forwardUrl = "forward:/"+operator.getUsername()+"/user_mgr/review/";	//转发至用户审核页面
		
		if(operator == null || operator.getLevel() < 9){ //无权限
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您无权限执行该操作！","utf-8");
		}
		
		//用户检查
		UserFullInfo user = userService.getUserFullInfo(username);
		if(user == null){ //无该用户
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("操作错误：系统中无该用户信息！","utf-8");
		}

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
		//审批申请检查
		ReviewApply  reviewApply = reviewCheck.geReviewApplytByID(reviewLog.getApplyId());
		if(reviewApply == null || !"0".equals(reviewApply.getStatus())) {
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("操作错误：系统中无指定的待审核用户！","utf-8");
		}
		//审批结果保存
		reviewLog.setReviewOpr(operator.getUserId());
		Long id = userService.reviewUser(user.getId(),reviewLog);
		if(id <= 0){//审批失败
			return "redirect:/"+operator.getUsername()+ "/review/" + "?error=" + ErrorCodesPropUtil.getParam(id.toString());	//添加审批错误信息
		}else {
			return "redirect:/"+operator.getUsername()+ "/review/";	//跳转至审核页面
		}
	}

	
	/**
	 * 保存用户基本信息（不包含密码和头像）的修改结果
	 * 【权限】
	 * 1、仅登录的用户自己可以修改自己的用户信息；
	 * @param username	旧的用户名
	 * @param userInfo	待保存用户信息
	 * @param operator	当前系统登录人员
	 * @param result		用户信息的格式验证结果
	 * @param map		待返回数据集
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping(value="/updateBasic",method=RequestMethod.POST)
	public String updateBasic(@PathVariable("username")String username,@Valid UserFullInfo userInfo,BindingResult result,
			@ModelAttribute("operator")Operator operator,Map<String,Object> map) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		//用户与编辑权限检查
		UserFullInfo oldInfo = userService.getUserFullInfo(username);
		if(operator == null || oldInfo == null || operator.getUserId() != userInfo.getId() || oldInfo.getId() != userInfo.getId()){ //无权限	
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您无权限执行该操作！","utf-8");
		}
		
		map.put("targetUser", userInfo);
		map.put("mode","editBasic");		//模式：编辑基本信息
		//数据格式验证处理
		if(result.hasErrors()){
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError e :list){
				String filed = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);
				map.put("valid." + filed, e.getDefaultMessage());
			}
			userInfo.setPicture(oldInfo.getPicture());
			map.put("mode","editBasic");
			return "user/userEdit";	//返回用户信息编辑页面
		}
		//数据保存
		userInfo.setInviteCode(oldInfo.getInviteCode());
		userInfo.setPasswd(oldInfo.getPasswd());
		userInfo.setPicture(oldInfo.getPicture());
		Long id = userService.saveUser(userInfo);
		if(id <= 0){	//用户信息保存错误
			String errMsg = ErrorCodesPropUtil.getParam(id.toString());
			map.put("error", errMsg);
			return "user/userEdit";
		}
		//更新操作员信息
		operator.setUsername(userInfo.getUsername());
		
		return "redirect:/"+operator.getUsername()+ "/user_mgr/detail";	//返回用户详情显示页面
	}
	
	/**
	 * 修改密码
	 * 【权限】
	 * 1、仅登录用户可以修改自己的密码
	 * @param old_passwd	旧密码
	 * @param new_passwd	新密码
	 * @param operator	当前登录操作员
	 * @param map	返回数据集
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/updatePwd",method=RequestMethod.POST)
	public String updatePwd(@PathVariable("username")String username,String old_passwd,String new_passwd,@ModelAttribute("operator")Operator operator,Map<String,Object> map) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		//用户与编辑权限检查
		UserFullInfo oldInfo = userService.getUserFullInfo(username);
		if(operator == null || oldInfo == null || operator.getUserId() != oldInfo.getId()){ //无权限	
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您无权限执行该操作！","utf-8");
		}
		map.put("targetUser", oldInfo);
		map.put("mode","editPwd");
		//密码验证
		String oldPwd = SunSHAUtils.encodeSHA512Hex(old_passwd);		
		if(!oldPwd.equals(oldInfo.getPasswd())){
			map.put("error","原密码不正确！");
			return "user/userEdit";
		}
		//数据保存
		String newPwd = SunSHAUtils.encodeSHA512Hex(new_passwd);
		oldInfo.setPasswd(newPwd);
		Long id = userService.saveUser(oldInfo);
		if(id <= 0){	//用户信息保存错误
			String errMsg = ErrorCodesPropUtil.getParam(id.toString());
			map.put("error", errMsg);
			return "user/userEdit";
		}
		return "redirect:/"+operator.getUsername()+ "/user_mgr/detail";	//返回用户详情显示页面
	}
	
	/**
	 * 保存用户图片编辑
	 * 【权限】
	 * 1、仅登录用户可以修改自己的头像照片
	 * @param file
	 * @param operator
	 * @param map
	 * @return
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping(value="/changePic",method=RequestMethod.POST)
	public String changePic(@PathVariable("username")String username, MultipartFile picFile,@ModelAttribute("operator")Operator operator,Map<String,Object> map) throws IOException, NoSuchAlgorithmException{
		//用户与编辑权限检查
		UserFullInfo oldInfo = userService.getUserFullInfo(username);
		if(operator == null || oldInfo == null || operator.getUserId() != oldInfo.getId()){ //无权限	
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您无权限执行该操作！","utf-8");
		}
		map.put("targetUser", oldInfo);
		//图片信息为空
		if(picFile.isEmpty()){
			map.put("error","图片信息为空！");
			map.put("mode","editPic");	//模式：头像变更
			return "user/userEdit";	//返回用户信息编辑页面
		}
		//文件处理：创建用户自己的文件夹、图片重命名
		String path = SysPropUtil.getParam("DIR_USER_UPLOAD") + "USER_" + oldInfo.getId().longValue() + "/";  
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdirs();
		}
		String oldPicName = oldInfo.getPicture();
		String picName = java.util.UUID.randomUUID().toString() 
				+ picFile.getOriginalFilename().substring(picFile.getOriginalFilename().lastIndexOf('.'));

		//保存图片至用户文件夹
		FileOutputStream out = new FileOutputStream(path + picName);
		InputStream in = picFile.getInputStream();
		byte[] buf = new byte[1024];
		int n = 0;
		while((n=in.read(buf))>0){
			out.write(buf, 0, n);
		}
		out.close();
		//修改头像图片的数据库信息
		oldInfo.setPicture(picName);
		Long id = userService.saveUser(oldInfo);
		if(id <= 0) {
			map.put("error","数据库保存图片信息失败！错误码：" + id);
			map.put("mode","editPic");	//模式：头像变更
			return "user/userEdit";	//返回用户信息编辑页面
		}
		//删除旧的图像		
		File[] files = dir.listFiles(new FileFilter(oldPicName));
		if(files != null && files.length>0){
			for(File file:files){
				file.delete();
			}
		}
		
		return "redirect:/"+operator.getUsername()+ "/user_mgr/detail";	//返回用户详情显示页面
	}
	
	/**
	 * 获取用户的可用邀请码
	 * 1、如果用户现有可用的邀请码则直接返回；
	 * 2、否则，生成新的邀请码返回；
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/createAvailCode")
	@ResponseBody
	public Object createAvailCode(@PathVariable("username")String username, @ModelAttribute("operator")Operator operator) throws UnsupportedEncodingException{
		UserFullInfo oldInfo = userService.getUserFullInfo(username);
		if(operator == null || oldInfo == null || operator.getUserId() != oldInfo.getId()){ //无权限	
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您无权限执行该操作！","utf-8");
		}
		Map<String,String> ret = new HashMap<String,String>();
		InviteCode inviteCode = userService.getAvailableCodeByUser(oldInfo.getId());
		BigDecimal code = null;
		if(inviteCode == null) {
			code = userService.createNewCode(oldInfo.getId());
		}else {
			code = inviteCode.getCode();
		}
		ret.put("code", code.toString());
		return ret;
	}
}



