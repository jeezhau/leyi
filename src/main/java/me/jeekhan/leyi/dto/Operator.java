package me.jeekhan.leyi.dto;

import java.util.List;

import me.jeekhan.leyi.model.ThemeClass;
import me.jeekhan.leyi.model.UserRole;
/**
 * 系统内存中操作人员信息
 * @author jeekhan
 *
 */
public class Operator {
	//用户名
	private String username;
	//用户ID
	private Long userId;
	//用户的角色
	private List<UserRole> userRoles;
	//用户的一级主题
	private List<ThemeClass> topThemes;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public List<ThemeClass> getTopThemes() {
		return topThemes;
	}

	public void setTopThemes(List<ThemeClass> topThemes) {
		this.topThemes = topThemes;
	}
	
}
