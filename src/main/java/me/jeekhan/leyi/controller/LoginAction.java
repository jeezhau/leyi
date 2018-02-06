package me.jeekhan.leyi.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import me.jeekhan.leyi.common.ErrorCodesPropUtil;
import me.jeekhan.leyi.common.SysPropUtil;
import me.jeekhan.leyi.dto.Operator;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.service.UserService;

/**
 * 用户注册、登录、注销等用户管理类
 * @author jeekhan
 *
 */
@Controller
@SessionAttributes({"operator"})
public class LoginAction {
	@Autowired
	private UserService userService;

	/**
	 * 用户登录
	 * 【权限】
	 * 		所用人
	 * 【功能说明】
	 * 		1、用户使用用户名或邮箱、密码进行登录；
	 * 		2、登录成功重定向至个人主页，否则返回首页
	 * 【输入输出】
	 * @param username	用户名
	 * @param password	密码
	 * @param map
	 * @return	目标页面
	 */
	@RequestMapping(value="/login")
	public String login(String username,String password,Map<String,Object>map,HttpServletRequest request){
		if(userService.authentification(username, password)){	//用户验证成功
			UserFullInfo userInfo = userService.getUserFullInfo(username);
			Operator operator = new Operator();
			//operator.setLevel(9);	设置用户级别
			operator.setUserId(userInfo.getId());
			operator.setUsername(username);
			map.put("operator", operator);
			map.put("userInfo", userInfo);
			return "redirect:/" + username;
		}else{
			return "forward:/login.jsp";	//转发至登录页面
		}
	}
	
	/**
	 * 用户注销登录
	 * 【权限】
	 * 		已登录用户
	 * 【功能说明】
	 * 		1、清除登录信息；
	 * 		2、重定向至系统主页；
	 * 【输入输出】
	 * @param map
	 * @return	目标页面
	 */
	@RequestMapping(value="/logout")
	public String logout(Map<String,Object>map,SessionStatus session){
		Operator operator = (Operator) map.get("operator");
		if(operator != null && operator.getUserId()>0){
			session.setComplete();	//清除用户session
		}
		return "redirect:/";
	}
	
	/**
	 * 用户注册
	 * 【权限】
	 * 		受邀请人
	 * 【功能说明】
	 * 		1、验证用户名或邮箱唯一；
	 * 		2、注册成功重定向至个人主页，否则返回注册页面并显示相关错误信息；
	 * 		3、保存用户图片至指定路径；
	 * 【输入输出】
	 * @param userInfo	用户基本注册信息
	 * @param file	头像图片
	 * @param map	返回数据集
	 * @param request	HTTP请求对象
	 * @return	
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	@RequestMapping(value="/createUser",method=RequestMethod.POST)
	public String addUser(@Valid UserFullInfo userInfo,BindingResult result,@RequestParam(value="picFile",required=false)MultipartFile file,
			Map<String,Object>map,HttpServletRequest request) throws NoSuchAlgorithmException, IOException{
		//用户信息验证结果处理
		if(result.hasErrors()){	//
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError e :list){
				String filed = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);
				map.put(filed, e.getDefaultMessage());
			}			
			return "register";	//返回注册页面
		}

		//邀请码验证
//		userInfo.setInviteCode(userInfo.getInviteCode().replace(",", ""));
//		InviteInfo inviteInfo = inviteInfoService.get(userInfo.getInviteCode());
//		if(inviteInfo == null || "1".equals(inviteInfo.getStatus())){
//			map.put("inviteCode", "邀请码不正确或已被使用");
//			return "register";
//		}
		//头像文件重命名
		String fileName = null;
		if(file != null && !file.isEmpty()){
			fileName = java.util.UUID.randomUUID().toString() + "." + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')+1);
			userInfo.setPicture(fileName);
		}
		//数据保存至数据库
		userInfo.setPicture(fileName);
		Long id = userService.saveUser(userInfo);
		if(id<=0){	//保存失败
			String errMsg = ErrorCodesPropUtil.getParam(id.toString());
			map.put("error", errMsg);
			return "forward:/register.jsp";	//返回注册页面
		}

		//文件保存至用户自己的文件夹
		if(file != null && !file.isEmpty()){
			String path = SysPropUtil.getParam("DIR_USER_UPLOAD") + "USER_" + id.longValue() + "/";  //用户文件夹
			File dir = new File(path);
			if(!dir.exists()){
				dir.mkdirs();
			}
			
			FileOutputStream out = new FileOutputStream(path + fileName);
			InputStream in = file.getInputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while((n=in.read(buf))>0){
				out.write(buf, 0, n);
			}
			out.close();
		}
		//更新登录操作员信息
		Operator operator = new Operator();
		operator.setLevel(new Integer(SysPropUtil.getParam("USER_INIT_LVL")));	//设置用户初始级别
		operator.setUserId(id);
		operator.setUsername(userInfo.getUsername());
		map.put("operator", operator);
		
		return "redirect:/" + userInfo.getUsername();
	}
}

