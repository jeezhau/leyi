package me.jeekhan.leyi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.jeekhan.leyi.model.UserRole;

public interface UserRoleMapper {
	//根据ID获取用户角色
	UserRole selectByID(Long id);
	//根据用户与角色ID获取用户角色
	UserRole selectByUserRole(@Param("userId")Long userId,@Param("roleId")int roleId);
	//新增用户角色
	int insert(UserRole userRole);
	//逻辑删除
	int delete(Long id);
	//更新用户角色
	int update(UserRole userRole);
	//取用户拥有的所有角色信息
	List<UserRole> selectRoles4User(Long userId);
}
