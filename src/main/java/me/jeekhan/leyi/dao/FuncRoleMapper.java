package me.jeekhan.leyi.dao;

import org.apache.ibatis.annotations.Param;

import me.jeekhan.leyi.model.FuncRole;

public interface FuncRoleMapper {
	//根据ID获取功能角色
	FuncRole selectByID(Integer id);
	//根据功能ID、角色ID获取信息，角色ID可为空
	FuncRole selectByFuncRole(@Param("funcId")Integer funcId,@Param("roleId")Integer roleId);
	//新增功能角色
	int insert(FuncRole funcRole);
	//删除功能角色
	int delete(Integer id);
	//更新功能角色
	int update(FuncRole funcRole);

}
