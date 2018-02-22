package me.jeekhan.leyi.dao;

import java.util.List;

import me.jeekhan.leyi.model.RoleInfo;

/**
 * 角色信息数据处理接口
 * @author jeekhan
 *
 */
public interface RoleInfoMapper {
	
	//新增角色
	int insert(RoleInfo roleInfo);
	
	//获取角色信息
	RoleInfo selectByID(int id);
	RoleInfo selectByName(String name);
	
	//更新角色信息
	int update(RoleInfo roleInfo);
	
	//删除角色
	int delete(int roleId);
	
	//获取系统中所有的角色信息
	List<RoleInfo> selectAllRoles();

}
