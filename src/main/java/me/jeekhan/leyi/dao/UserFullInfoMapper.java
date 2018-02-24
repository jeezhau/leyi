package me.jeekhan.leyi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.model.UserFullInfo;

/**
 * 用户详细数据管理接口
 * @author jeekhan
 *
 */
public interface UserFullInfoMapper {
	//更新用户状态
    Long updateStatus(@Param("id")Long id,@Param("status") String status);
    
    //新插入用户记录
    int insert(UserFullInfo user);
    
    //更新密码
    int updatePwd(@Param("userId")Long userId,@Param("newPwd")String newPwd);
    
    //根据主键获取用户详细信息
    UserFullInfo selectByPrimaryKey(Long id);
    
    	//根据用户名或邮箱获取用户信息
    UserFullInfo selectByName(String username);
    
    //根据主键更新用户详细信息
    Long updateByPrimaryKey(UserFullInfo record);
    
    	//获取用于显示于系统主页的推荐用户
    List<UserFullInfo> selectSysIndexShowUser();
    
    //获取待审核的用户记录
    List<UserFullInfo> selectUsers4Review(PageCond pageCond);
    
    //统计待审核的用户数量
    int countUsers4Review();
    
}