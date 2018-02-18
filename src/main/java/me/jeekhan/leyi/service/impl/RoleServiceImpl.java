package me.jeekhan.leyi.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.jeekhan.leyi.common.ErrorCodes;
import me.jeekhan.leyi.dao.FuncInfoMapper;
import me.jeekhan.leyi.dao.FuncRoleMapper;
import me.jeekhan.leyi.dao.RoleInfoMapper;
import me.jeekhan.leyi.dao.UserRoleMapper;
import me.jeekhan.leyi.model.FuncInfo;
import me.jeekhan.leyi.model.FuncRole;
import me.jeekhan.leyi.model.RoleInfo;
import me.jeekhan.leyi.model.UserRole;
import me.jeekhan.leyi.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleInfoMapper roleInfoMapper;
	@Autowired
	private FuncInfoMapper funcInfoMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private FuncRoleMapper funcRoleMapper;
	
	//角色信息基本操作：CRUD
	@Override
	public RoleInfo getRoleByID(Integer id) {
		return roleInfoMapper.selectByID(id);
	}

	@Override
	public RoleInfo getRoleByName(String name) {
		return roleInfoMapper.selectByName(name);
	}

	@Override
	public Long addRole(RoleInfo role) {
		RoleInfo old = roleInfoMapper.selectByName(role.getName());
		if(old != null) {
			return ErrorCodes.ROLE_EXISTS_SAME_ROLE;
		}
		role.setStatus("A");
		role.setUpdateTime(new Date());
		int cnt = roleInfoMapper.insert(role);
		if(cnt<1) {
			return ErrorCodes.DB_CRUD_ERR;
		}
		return new Long(role.getId());	//返回角色ID
	}

	@Override
	public Long deleteRole(RoleInfo role) {
		int cnt = roleInfoMapper.delete(role.getId());
		if(cnt<1) {
			return ErrorCodes.DB_CRUD_ERR;
		}
		return new Long(role.getId());	//返回角色ID
	}

	@Override
	public Long updateRole(RoleInfo role) {
		RoleInfo old = roleInfoMapper.selectByName(role.getName());
		if(old != null && role.getId() != old.getId()) {
			return ErrorCodes.ROLE_EXISTS_SAME_ROLE;
		}
		role.setStatus("A");
		role.setUpdateTime(new Date());
		int cnt = roleInfoMapper.update(role);
		if(cnt<1) {
			return ErrorCodes.DB_CRUD_ERR;
		}
		return new Long(role.getId());	//返回角色ID
	}

	//功能信息基本操作：CRUD
	@Override
	public FuncInfo getFuncByID(Integer id) {
		return funcInfoMapper.selectByID(id);
	}

	@Override
	public FuncInfo getFunByURL(String url) {
		return funcInfoMapper.selectByURL(url);
	}

	@Override
	public Long addFunc(FuncInfo func) {
		FuncInfo old = funcInfoMapper.selectByURL(func.getUrl());
		if(old != null) {
			return ErrorCodes.ROLE_EXISTS_SAME_FUNC;
		}
		func.setUpdateTime(new Date());
		int cnt = funcInfoMapper.insert(func);
		if(cnt < 1) {
			return ErrorCodes.DB_CRUD_ERR;
		}
		return new Long(func.getId());	//返回功能ID
	}

	@Override
	public Long deleteFunc(FuncInfo func) {
		int cnt = funcInfoMapper.delete(func.getId());
		if(cnt < 1) {
			return ErrorCodes.DB_CRUD_ERR;
		}
		return new Long(func.getId());	//返回功能ID
	}

	@Override
	public Long updateFunc(FuncInfo func) {
		func.setUpdateTime(new Date());
		int cnt = funcInfoMapper.update(func);
		if(cnt < 1) {
			return ErrorCodes.DB_CRUD_ERR;
		}
		return new Long(func.getId());	//返回功能ID
	}

	//用户角色基本操作：CRUD
	@Override
	public UserRole getUserRole(Long id) {
		return userRoleMapper.selectByID(id);
	}

	@Override
	public UserRole getUserRole(Long userId, Integer roleId) {
		return userRoleMapper.selectByUserRole(userId, roleId);
	}

	@Override
	public Long addUserRole(UserRole userRole) {
		userRole.setUpdateTime(new Date());
		int cnt = userRoleMapper.insert(userRole);
		if(cnt < 1) {
			return ErrorCodes.DB_CRUD_ERR;
		}
		return userRole.getId();	//返回用户角色ID
	}

	@Override
	public Long deleteUserRole(UserRole userRole) {
		int cnt = userRoleMapper.delete(userRole.getId());
		if(cnt < 1) {
			return ErrorCodes.DB_CRUD_ERR;
		}
		return userRole.getId();	//返回用户角色ID
	}

	@Override
	public Long updateUserRole(UserRole userRole) {
		userRole.setUpdateTime(new Date());
		int cnt = userRoleMapper.update(userRole);
		if(cnt < 1) {
			return ErrorCodes.DB_CRUD_ERR;
		}
		return userRole.getId();	//返回用户角色ID 
	}

	//功能角色基本操作：CRUD
	@Override
	public FuncRole getFuncRole(Integer id) {
		return funcRoleMapper.selectByID(id);
	}

	/**
	 * 获取功能对应的角色信息
	 * @param funcId 功能ID，不可为空
	 * @param roleId 角色ID，可为空
	 * @return
	 */
	@Override
	public FuncRole getFuncRole(Integer funcId, Integer roleId) {
		return funcRoleMapper.selectByFuncRole(funcId, roleId);
	}

	@Override
	public Long addFuncRole(FuncRole funcRole) {
		funcRole.setUpdateTime(new Date());
		int cnt = funcRoleMapper.insert(funcRole);
		if(cnt < 1) {
			return ErrorCodes.DB_CRUD_ERR;
		}
		return new Long(funcRole.getId());	//返回功能角色ID
	}

	@Override
	public Long deleteFuncRole(FuncRole funcRole) {
		int cnt = funcRoleMapper.delete(funcRole.getId());
		if(cnt < 1) {
			return ErrorCodes.DB_CRUD_ERR;
		}
		return new Long(funcRole.getId());	//返回功能角色ID
	}

	@Override
	public Long updateFuncRole(FuncRole funcRole) {
		int cnt = funcRoleMapper.update(funcRole);
		if(cnt < 1) {
			return ErrorCodes.DB_CRUD_ERR;
		}
		return new Long(funcRole.getId());	//返回功能角色ID
	}
	
	/**
	 * 获取系统中所有的角色信息
	 */
	@Override
	public List<RoleInfo> getAllRoles(){
		return roleInfoMapper.selectAllRoles();
	}
	
	/**
	 * 获取系统中所有的功能信息
	 */
	@Override
	public List<FuncInfo> getAllFuncs(){
		return funcInfoMapper.selectAllFuncs();
	}
	
	/**
	 * 获取功能所拥有的角色信息
	 */
	@Override
	public FuncInfo getRoles4Func(Integer funcId) {
		return funcInfoMapper.selectFuncAndRoles(funcId);
	}
	
}
