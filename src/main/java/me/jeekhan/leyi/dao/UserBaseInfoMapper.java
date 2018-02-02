package me.jeekhan.leyi.dao;

import me.jeekhan.leyi.model.UserBaseInfo;

/**
 * 用户信息数据管理接口
 * @author jeekhan
 *
 */
public interface UserBaseInfoMapper {
	//根据主键删除用户记录
    Long deleteByPrimaryKey(Long userId);
    //新插入用户记录
    Long insert(UserBaseInfo record);
    //根据主键获取用户记录
    UserBaseInfo selectByPrimaryKey(Long userId);
    //根据主键更新用户记录
    Long updateByPrimaryKey(UserBaseInfo record);
    //根据用户名获取用户记录
    UserBaseInfo selectByName(String username);
}