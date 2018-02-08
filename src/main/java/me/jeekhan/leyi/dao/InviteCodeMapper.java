package me.jeekhan.leyi.dao;

import java.math.BigDecimal;

import me.jeekhan.leyi.model.InviteCode;

/**
 * 邀请码信息数据处理接口
 * @author jeekhan
 *
 */
public interface InviteCodeMapper {
	
	//新增邀请码
	int insert(InviteCode inviteCode);
	
	//取用户当前可用的邀请码
	InviteCode selectAvailableByUser(Long userId);
	
	//更新邀请码的使用次数
	int updateUsedCnt(BigDecimal code);
	
	//判断邀请码是否可用
	int isAvailableCode(BigDecimal code);

}
