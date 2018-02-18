package me.jeekhan.leyi.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RoleInfo {
	
	private Integer id;
	
	@NotNull(message="角色名称：不可为空")
	@Size(min=3,max=30,message="角色名称：长度为3-30字符")
	private String name;
	
	@Size(max=600,message="角色描述：最长600字符")
	private String desc;
	
	private Long updateOpr;
	
	private Date updateTime;
	
	@Pattern(regexp="^[AD]$")
	private String status;	//角色状态

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Long getUpdateOpr() {
		return updateOpr;
	}

	public void setUpdateOpr(Long updateOpr) {
		this.updateOpr = updateOpr;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
