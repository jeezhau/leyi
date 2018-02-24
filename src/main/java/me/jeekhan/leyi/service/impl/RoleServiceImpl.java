package me.jeekhan.leyi.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.jeekhan.leyi.common.ErrorCodes;
import me.jeekhan.leyi.common.SysPropUtil;
import me.jeekhan.leyi.dao.FuncInfoMapper;
import me.jeekhan.leyi.dao.FuncRoleMapper;
import me.jeekhan.leyi.dao.RoleInfoMapper;
import me.jeekhan.leyi.dao.UserRoleMapper;
import me.jeekhan.leyi.model.FuncInfo;
import me.jeekhan.leyi.model.FuncRole;
import me.jeekhan.leyi.model.ReviewApply;
import me.jeekhan.leyi.model.RoleInfo;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.model.UserRole;
import me.jeekhan.leyi.service.ReviewCheck;
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
	@Autowired
	private ReviewCheck reviewCheck;

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
		func.setStatus("A");
		func.setUpdateTime(new Date());
		int cnt = funcInfoMapper.insert(func);
		if(cnt < 1) {
			return ErrorCodes.DB_CRUD_ERR;
		}
		//保存功能角色
		if(func.getRoles() != null && !func.getRoles().isEmpty()) {
			for(RoleInfo role:func.getRoles()) {
				FuncRole funcRole = new FuncRole();
				funcRole.setFuncId(func.getId());
				funcRole.setRoleId(role.getId());
				funcRole.setUpdateOpr(func.getUpdateOpr());
				funcRole.setUpdateTime(new Date());
				funcRole.setStatus("A");
				this.addFuncRole(funcRole);
			}
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
		FuncInfo old = funcInfoMapper.selectByURL(func.getUrl());
		if(old != null && old.getId() != func.getId()) {
			return ErrorCodes.ROLE_EXISTS_SAME_FUNC;
		}
		old = funcInfoMapper.selectFuncAndRoles(func.getId());
		//保存功能角色
		if(func.getRoles() != null && !func.getRoles().isEmpty()) {
			Set<RoleInfo> newRoles = new HashSet<RoleInfo>(func.getRoles());//新提交的角色
			Set<RoleInfo> oldRoles = new HashSet<RoleInfo>(old.getRoles()); //原来的角色
			Set<RoleInfo> addRoles = new HashSet<RoleInfo>(newRoles);
			addRoles.removeAll(oldRoles);
			Set<RoleInfo> delRoles = new HashSet<RoleInfo>(oldRoles);
			delRoles.removeAll(newRoles);
			for(RoleInfo role:addRoles) {//新增功能角色
				FuncRole funcRole = new FuncRole();
				funcRole.setFuncId(func.getId());
				funcRole.setRoleId(role.getId());
				funcRole.setUpdateOpr(func.getUpdateOpr());
				funcRole.setUpdateTime(new Date());
				funcRole.setStatus("A");
				this.addFuncRole(funcRole);
			}
			for(RoleInfo role:delRoles) {//删除功能角色
				FuncRole funcRole = this.getFuncRole(func.getId(), role.getId());
				funcRole.setUpdateOpr(func.getUpdateOpr());
				funcRole.setUpdateTime(new Date());
				funcRole.setStatus("D");
				this.deleteFuncRole(funcRole);
			}
		}
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
	
	/**
	 * 获取用户拥有的角色信息
	 * 
	 */
	public List<UserRole> getRoles4User(Long userId){
		return userRoleMapper.selectRoles4User(userId);
	}
	
	/**
	 * 保存用户角色申请
	 * 1、每类申请在连续6次审批被拒绝后系统不再接受申请；
	 * 2、待审批(包含复核中)、正常、拒绝的对象用户可修改再申请，如果复核中的对象被修改，则复核终止，重新进入审批流程。复核不通过的记录不可再次提交申请。
	 * 3、用户提交申请后在，系统在“申请审批表”中记录一条申请信息。一条记录作为一次审批流程。
	 * @param user	用户详细信息
	 * @param userRoles	申请的角色信息
	 * @return	{userRoleId:result}
	 */
	@Override 
	public Map<RoleInfo,Long> applyUserRole(UserFullInfo user,List<RoleInfo> userRoles){		
		Map<RoleInfo,Long> retMap = new HashMap<RoleInfo,Long>();
		String objName = SysPropUtil.getParam("USER_ROLE");
		for(RoleInfo role:userRoles) {
			UserRole userRole = this.getUserRole(user.getId(), role.getId());
			if(userRole == null) {//新增
				userRole = new UserRole();
				userRole.setRoleId(role.getId());
				userRole.setUserId(user.getId());
				userRole.setUpdateTime(new Date());
				userRole.setStatus("0");
				this.addUserRole(userRole);
				//添加申请
				ReviewApply apply = new ReviewApply();
				apply = new ReviewApply();
				apply.setObjName(objName);
				apply.setKeyId(userRole.getId());
				apply.setApplyTime(new Date());
				apply.setStatus("0"); //待审核
				reviewCheck.addReviewApply(apply);
				retMap.put(role, 1L);
			}else {
				boolean can = reviewCheck.canApply(objName, userRole.getId());
				if(!can) {//提交限制
					retMap.put(role, ErrorCodes.REVIEW_APPLY_LIMIT);
					continue;
				}
				ReviewApply apply = reviewCheck.getWait4Review(objName, userRole.getId());//取已有待审批记录
				if(apply != null) {
					if("CC".equals(apply.getStatus())) {//复核中
						apply.setStatus("CT");//复核终止
						apply = new ReviewApply();//重新申请
						apply = new ReviewApply();
						apply.setObjName(objName);
						apply.setKeyId(userRole.getId());
						apply.setApplyTime(new Date());
						apply.setStatus("0"); //待审核
						reviewCheck.addReviewApply(apply);
					}else {
						apply.setApplyTime(new Date());
						reviewCheck.updateReviewApply(apply);
					}
				}else {
					apply = new ReviewApply();//添加申请
					apply = new ReviewApply();
					apply.setObjName(objName);
					apply.setKeyId(userRole.getId());
					apply.setApplyTime(new Date());
					apply.setStatus("0"); //待审核
					reviewCheck.addReviewApply(apply);
				}
				//更新已有用户角色
				userRole.setUpdateTime(new Date());
				userRole.setStatus("0");
				this.updateUserRole(userRole);
				retMap.put(role, 1L);
			}
		}
		return retMap;
	}
}
