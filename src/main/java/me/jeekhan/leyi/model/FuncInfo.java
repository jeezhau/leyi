package me.jeekhan.leyi.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class FuncInfo {
	private int id;
	
	@NotNull(message="功能名称：不可为空")
	@Size(min=3,max=30,message="功能名称：长度为3-30字符")
	private String name;
	
	@NotNull(message="功能名称：不可为空")
	@Size(min=3,max=100,message="功能名称：长度为3-100字符")
	private String url;
	
	@Size(max=600,message="功能描述：最长600字符")
	private String desc;
	
	private Long updateOpr;
	
	private Date updateTime;
	
	@Pattern(regexp="^[AD]$")
	private String status;	//功能状态
	
	private List<RoleInfo> roles;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public List<RoleInfo> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleInfo> roles) {
		this.roles = roles;
	}
	

}
