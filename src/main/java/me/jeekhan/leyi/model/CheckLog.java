package me.jeekhan.leyi.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CheckLog {
	private Long id;
	
	private Long applyId; //申请ID
	
	@Size(max=600,message="复核意见：最大长度为600个字符！")
	private String checkInfo; //审批意见
	
	private Long checkOpr;	//审批人
	
	private Date checkTime;	//审批时间
	
	@NotNull(message="")
	@Pattern(regexp="^[AR]$",message="类型：值只可为【A-接受，R-拒绝】！")
	private String status;	//审批结果(A：通过，R：不通过)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public String getCheckInfo() {
		return checkInfo;
	}

	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}

	public Long getCheckOpr() {
		return checkOpr;
	}

	public void setCheckOpr(Long checkOpr) {
		this.checkOpr = checkOpr;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
