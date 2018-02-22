package me.jeekhan.leyi.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import me.jeekhan.leyi.common.ErrorCodesPropUtil;
import me.jeekhan.leyi.common.SysPropUtil;
import me.jeekhan.leyi.dto.Operator;
import me.jeekhan.leyi.model.FuncInfo;
import me.jeekhan.leyi.model.RoleInfo;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.service.RoleService;
import me.jeekhan.leyi.service.UserService;

@RequestMapping("/{username}/role_mgr")
@Controller
@SessionAttributes({"operator"})
public class RoleMgrAction {
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	
	private static String REDIRECT_SYS_ERROR_PAGE = "redirect:" + SysPropUtil.getParam("SYSTEM_ERROR_PAGE");	//跳转至错误页面
	/**
	 * 角色管理首页
	 * 【权限】
	 *  1、超级管理员
	 * 【功能说明】
	 * @param username 目标用户名
	 * @param condParams	查询条件JSON
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String accessArticlesMgr(@PathVariable("username")String username,String condParams,
			Map<String,Object> map,HttpSession session) throws JsonParseException, JsonMappingException, IOException{
		Operator operator = (Operator) map.get("operator");
		//用户权限控制
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		
		if(operator == null || userInfo == null || userInfo.getId() != operator.getUserId()) {
			return REDIRECT_SYS_ERROR_PAGE + "?error=" + URLEncoder.encode("权限错误：您无权限执行该操作！","utf-8");
		}
		List<RoleInfo> allRoles = roleService.getAllRoles();
		List<FuncInfo> allFuncs = roleService.getAllFuncs();
		map.put("allRoles", allRoles);
		map.put("allFuncs", allFuncs);
		
		return "role/roleMgr";
	}
	
	/**
	 * 获取功能信息及所拥有的角色信息
	 * @param funcId
	 * @return
	 */
	@RequestMapping("/getRoles4Func")
	@ResponseBody
	public Object getRoles4Func(Integer funcId){
		FuncInfo func = roleService.getRoles4Func(funcId);
		return func;
	}
	
	/**
	 * 获取系统中的所有角色信息
	 * @return
	 */
	@RequestMapping("/getAllRoles")
	@ResponseBody
	public Object getAllRoles() {
		List<RoleInfo> roles = roleService.getAllRoles();
		return roles;
	}
	
	/**
	 * 保存角色信息
	 *【权限】
	 * 1、超级管理员
	 *【功能说明】
	 * 1、参数中的ID=0视为新增，ID>0则视为修改；
	 *【输入输出】
	 * @param roleInfo	待添加的角色信息
	 * @param result		提交的角色信息字段验证结果
	 * @param operator
	 * @return {roleId,result,valid.*,error}
	 */
	@RequestMapping("/saveRole")
	@ResponseBody
	public Object saveRole(@Valid RoleInfo roleInfo,BindingResult result,@ModelAttribute("operator") Operator operator) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", "failure");//操作结果：失败
		if(result.hasErrors()){	//
			List<ObjectError> list = result.getAllErrors();
			Map<String,Object> valid = new HashMap<String,Object>();
			for(ObjectError e :list){
				String filed = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);
				valid.put(filed, e.getDefaultMessage());
			}
			map.put("valid", valid);	//返回字段验证错误信息
			return map;
		}
		//修改：角色检查
		if(roleInfo.getId() > 0) {
			RoleInfo old = roleService.getRoleByID(roleInfo.getId());
			if(old == null) {
				String errMsg = "操作错误：该角色不存在！";
				map.put("error", errMsg);
				return map;
			}
		}
		roleInfo.setUpdateOpr(operator.getUserId());
		roleInfo.setStatus("A");
		Long ret = null;
		if(roleInfo.getId() > 0) {
			ret = roleService.updateRole(roleInfo);
		}else {
			roleInfo.setId(null);
			ret = roleService.addRole(roleInfo);
		}
		if(ret < 0) {
			String errMsg = ErrorCodesPropUtil.getParam(ret.toString());
			map.put("error", errMsg);
			return map;
		}
		map.put("roleId", ret);
		map.put("result", "success");//操作结果：成功
		return map;
	}
	
	
	/**
	 * 逻辑删除角色信息
	 *【权限】
	 * 1、超级管理员
	 *【输入输出】
	 * @param roleId		待删除的角色ID
	 * @param operator
	 * @return {result,error}
	 */
	@RequestMapping("/deleteRole")
	@ResponseBody
	public Object deleteRole(Integer roleId,@ModelAttribute("operator")Operator operator) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", "failure");//操作结果：失败
		//角色检查
		RoleInfo old = roleService.getRoleByID(roleId);
		if(old == null) {
			String errMsg = "操作错误：该角色不存在！";
			map.put("error", errMsg);
			return map;
		}
		//数据保存
		old.setUpdateOpr(operator.getUserId());
		old.setStatus("D");
		Long ret = roleService.updateRole(old);
		if(ret < 0) {
			String errMsg = ErrorCodesPropUtil.getParam(ret.toString());
			map.put("error", errMsg);
			return map;
		}
		map.put("result", "success");//操作结果：成功
		return map;
	}
	
	/**
	 * 保存功能信息
	 *【权限】
	 * 1、超级管理员
	 *【功能说明】
	 * 1、参数中的ID=0视为新增，ID>0则视为修改；
	 *【输入输出】
	 * @param funcInfo	待保存的功能信息
	 * @param result		提交的功能信息字段验证结果
	 * @param operator
	 * @return {funcId,result,valid.*,error}
	 */
	@RequestMapping("/saveFunc")
	@ResponseBody
	public Object saveFunc(@Valid FuncInfo funcInfo,BindingResult result,String rolesId,@ModelAttribute("operator") Operator operator) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", "failure");//操作结果：失败
		if(result.hasErrors()){	//
			List<ObjectError> list = result.getAllErrors();
			Map<String,Object> valid = new HashMap<String,Object>();
			for(ObjectError e :list){
				String filed = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);
				valid.put(filed, e.getDefaultMessage());
			}
			map.put("valid", valid);	//返回字段验证错误信息
			return map;
		}
		//修改：功能检查
		if(funcInfo.getId() > 0) {
			FuncInfo old = roleService.getFuncByID(funcInfo.getId());
			if(old == null) {
				String errMsg = "操作错误：该功能不存在！";
				map.put("error", errMsg);
				return map;
			}
		}
		//角色检查
		if(rolesId == null) {
			rolesId = "";
		}
		String[] arrId = rolesId.split(",");
		List<RoleInfo> roleList = new ArrayList<RoleInfo>();
		Set<String> setId = new HashSet<String>(Arrays.asList(arrId));//去重
		Set<String> errId = new HashSet<String>();
		for(String roleId:setId) {
			roleId = roleId.trim();
			try {
				if(roleId.length()>0) {
					RoleInfo role = roleService.getRoleByID(new Integer(roleId));
					if(role == null) {
						errId.add(roleId);
					}else {
						roleList.add(role);
					}
				}
			}catch(Exception e) {
				errId.add(roleId);
			}
		}
		if(!errId.isEmpty()) {
			String errMsg = "操作错误：角色" + errId.toString() + "不存在！";
			map.put("error", errMsg);
			return map;
		}
		//数据保存
		funcInfo.setRoles(roleList);
		funcInfo.setUpdateOpr(operator.getUserId());
		funcInfo.setStatus("A");
		Long ret = null;
		if(funcInfo.getId() > 0) {//修改
			ret = roleService.updateFunc(funcInfo);
		}else {
			funcInfo.setId(null);
			ret = roleService.addFunc(funcInfo);
		}
		if(ret < 0) {
			String errMsg = ErrorCodesPropUtil.getParam(ret.toString());
			map.put("error", errMsg);
			return map;
		}
		map.put("funcId", ret);
		map.put("result", "success");//操作结果：成功
		return map;
	}
	
	/**
	 * 逻辑删除功能信息
	 *【权限】
	 * 1、超级管理员
	 *【输入输出】
	 * @param funcId		待删除的功能ID
	 * @param operator
	 * @return {result,error}
	 */
	@RequestMapping("/deleteFunc")
	@ResponseBody
	public Object deleteFunc(Integer funcId,@ModelAttribute("operator")Operator operator) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", "failure");//操作结果：失败
		//功能检查
		FuncInfo old = roleService.getFuncByID(funcId);
		if(old == null) {
			String errMsg = "操作错误：该功能不存在！";
			map.put("error", errMsg);
			return map;
		}
		//数据保存
		old.setUpdateOpr(operator.getUserId());
		old.setStatus("D");
		old.setRoles(null);
		Long ret = roleService.updateFunc(old);
		if(ret < 0) {
			String errMsg = ErrorCodesPropUtil.getParam(ret.toString());
			map.put("error", errMsg);
			return map;
		}
		map.put("result", "success");//操作结果：成功
		return map;
	}
	
}
