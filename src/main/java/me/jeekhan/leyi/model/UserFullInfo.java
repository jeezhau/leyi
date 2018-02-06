package me.jeekhan.leyi.model;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 用户详细记录
 * @author jeekhan
 *
 */

public class UserFullInfo {
    private Long id;
    
    @NotNull(message="不可为空！")
    @Size(min=3,max=50,message="长度为3-50个字符！")	
    private String username;
    
    @Size(max=100,message="最长100个字符！")
    @Email
    //@Pattern(regexp="^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$")
    private String email;
    
    @Pattern(regexp="\\d{4}-\\d{2}-\\d{2}",message="格式为：yyyy-MM-dd")
    private String birthday;
    
    @Pattern(regexp="^[NFM]$",message="值只可为【M-男，F-女，N-保密】！")
    private String sex;
    
    @NotNull(message="密码不可为空！")
    @Size(min=6,max=20,message="长度为6-20个字符！")
    private String passwd;
    
    @Size(max=50,message="最长为50个字符！")
    private String city;
    
    @Size(max=100,message="最长为100个字符！")
    private String favourite;
    
    @Size(max=100,message="最长为100个字符！")
    private String profession;
    
    @Size(max=600,message="最长为600个字符！")
    private String introduce;
    
    @Null
    private Date registTime;
    
    @Null
    private Date updateTime;
    
    @Null
    private String status;
    
    @NotNull
    @Size(max=50,message="最长为50个字符！")
    private String inviteCode;
    
    private String picture;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
  

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	//返回JSON字符串
    public String toString(){
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return super.toString();
		} 
    }
}