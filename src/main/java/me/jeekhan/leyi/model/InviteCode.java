package me.jeekhan.leyi.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 邀请码信息
 * @author jeekhan
 *
 */
public class InviteCode {
	private BigDecimal code ;
	
	private Long createOpr;
	
	private Date createTime;
	
	private int usedCnt;	//已使用次数

	public BigDecimal getCode() {
		return code;
	}

	public void setCode(BigDecimal code) {
		this.code = code;
	}

	public Long getCreateOpr() {
		return createOpr;
	}

	public void setCreateOpr(Long createOpr) {
		this.createOpr = createOpr;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getUsedCnt() {
		return usedCnt;
	}

	public void setUsedCnt(int usedCnt) {
		this.usedCnt = usedCnt;
	}
	
	

}
