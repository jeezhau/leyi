package me.jeekhan.leyi.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class FuncRole {
	private int id;
	
	@NotNull
	private int funcId;
	
	@NotNull
	private int roleId;
	
	private Long updateOpr;
	
	private Date updateTime;
	
	@Pattern(regexp="^[AD]$")
	private String status;	//状态
	
	private FuncInfo func;
	
	private RoleInfo role;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFuncId() {
		return funcId;
	}

	public void setFuncId(int funcId) {
		this.funcId = funcId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
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

	public FuncInfo getFunc() {
		return func;
	}

	public void setFunc(FuncInfo func) {
		this.func = func;
	}

	public RoleInfo getRole() {
		return role;
	}

	public void setRole(RoleInfo role) {
		this.role = role;
	}
	
	

}
