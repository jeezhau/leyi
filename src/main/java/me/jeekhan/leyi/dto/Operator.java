package me.jeekhan.leyi.dto;

import java.util.List;
import java.util.Map;

import me.jeekhan.leyi.model.ThemeInfo;
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
	//用户的级别
	private int level;
	//用户的一级主题
	private List<ThemeInfo> topThemes;
	
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public List<ThemeInfo> getTopThemes() {
		return topThemes;
	}

	public void setTopThemes(List<ThemeInfo> topThemes) {
		this.topThemes = topThemes;
	}
	
}
