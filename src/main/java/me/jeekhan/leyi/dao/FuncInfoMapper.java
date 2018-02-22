package me.jeekhan.leyi.dao;

import java.util.List;

import me.jeekhan.leyi.model.FuncInfo;

/**
 * 功能信息数据处理接口
 * @author jeekhan
 *
 */
public interface FuncInfoMapper {
	
	//新增功能
	int insert(FuncInfo funcInfo);
	
	//获取功能信息
	FuncInfo selectByID(int id);
	FuncInfo selectByURL(String url);
	
	//更新功能信息
	int update(FuncInfo funcInfo);
	
	//删除功能
	int delete(int funcInfo);
	
	List<FuncInfo> selectAllFuncs();
	
	//获取功能信息及其拥有的角色信息
	FuncInfo selectFuncAndRoles(Integer funcId);

}
