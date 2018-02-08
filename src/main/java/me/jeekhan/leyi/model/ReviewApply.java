package me.jeekhan.leyi.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ReviewApply {
	private Long id;
	
	private String objName; //相关对象名称
	
	private Long keyId;	//相关表记录ID
	
	private Date applyTime;	//申请时间
	
	@NotNull(message="")
	@Pattern(regexp="^(A|R|CC|CR|CA|CT)$",
		message="类型：值只可为【A：通过，R：不通过，CC：复核中，CR：复核不通过，CA：复核通过，CT：复核终止 】！")
	private String status;

	public Long getId() {
		return id;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public Long getKeyId() {
		return keyId;
	}

	public void setKeyId(Long keyId) {
		this.keyId = keyId;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
