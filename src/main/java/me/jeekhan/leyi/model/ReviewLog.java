package me.jeekhan.leyi.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ReviewLog {
	private Long id;
	
	@NotNull
	private Long applyId; //申请ID
	
	private String originalInfo;	//原始待审批信息
	
	@Size(max=600,message="审批意见：最大长度为600个字符！")
	private String reviewInfo; //审批意见
	
	private Long reviewOpr;	//审批人
	
	private Date reviewTime;	//审批时间
	
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

	public String getOriginalInfo() {
		return originalInfo;
	}

	public void setOriginalInfo(String originalInfo) {
		this.originalInfo = originalInfo;
	}

	public String getReviewInfo() {
		return reviewInfo;
	}

	public void setReviewInfo(String reviewInfo) {
		this.reviewInfo = reviewInfo;
	}

	public Long getReviewOpr() {
		return reviewOpr;
	}

	public void setReviewOpr(Long reviewOpr) {
		this.reviewOpr = reviewOpr;
	}

	public Date getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
