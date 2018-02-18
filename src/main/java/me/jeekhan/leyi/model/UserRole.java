package me.jeekhan.leyi.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 用户角色信息
 * @author jeekhan
 *
 */
public class UserRole {
	private Long id;
	
	@NotNull
	private Long userId;
	
	@NotNull
	private int roleId;
	
	private Long updateOpr;
	
	private Date updateTime;
	
	@Pattern(regexp="^[AD]$")
	private String status;	//状态
	
	private UserFullInfo user;
	
	private RoleInfo role;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public UserFullInfo getUser() {
		return user;
	}

	public void setUser(UserFullInfo user) {
		this.user = user;
	}

	public RoleInfo getRole() {
		return role;
	}

	public void setRole(RoleInfo role) {
		this.role = role;
	}

	
}
