package me.jeekhan.leyi.service;

import java.util.List;
import java.util.Map;

import me.jeekhan.leyi.model.FuncInfo;
import me.jeekhan.leyi.model.FuncRole;
import me.jeekhan.leyi.model.RoleInfo;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.model.UserRole;

public interface RoleService {
	//角色基本管理
	RoleInfo getRoleByID(Integer id);
	RoleInfo getRoleByName(String name);
	//添加删除修改角色，返回角色ID
	Long addRole(RoleInfo role);
	Long deleteRole(RoleInfo role);
	Long updateRole(RoleInfo role);
	
	//功能基本管理
	FuncInfo getFuncByID(Integer id);
	FuncInfo getFunByURL(String url);
	//添加删除修改功能，返回功能ID
	Long addFunc(FuncInfo func);
	Long deleteFunc(FuncInfo func);
	Long updateFunc(FuncInfo func);
	
	//用户角色基本管理
	UserRole getUserRole(Long id);
	UserRole getUserRole(Long userId,Integer roleId);
	//添加删除修改用户角色，返回ID
	Long addUserRole(UserRole userRole);
	Long deleteUserRole(UserRole userRole);
	Long updateUserRole(UserRole userRole);
	
	//功能角色基本管理
	FuncRole getFuncRole(Integer id);
	FuncRole getFuncRole(Integer funcId,Integer roleId);
	//添加删除修改角色，返回角色ID
	Long addFuncRole(FuncRole funcRole);
	Long deleteFuncRole(FuncRole funcRole);
	Long updateFuncRole(FuncRole funcRole);
	
	//获取所有的角色信息
	List<RoleInfo> getAllRoles();
	
	//获取所有的功能信息
	List<FuncInfo> getAllFuncs();
	
	//获取功能拥有的角色信息
	FuncInfo getRoles4Func(Integer funcId);
	
	//获取用户拥有的角色信息
	List<UserRole> getRoles4User(Long userId);
	
	/**
	 * 用户角色申请
	 * @param user
	 * @param userRoles
	 * @return
	 */
	Map<RoleInfo,Long> applyUserRole(UserFullInfo user,List<RoleInfo> userRoles);
}
