package me.jeekhan.leyi.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ThemeClass {
	private List<ThemeClass> children;
	
    private Long id;
    
    	//@NotNull(message="上级主题ID序列：不可为空！")
    @Size(max=80,message="上级主题ID序列：最大长度为80个字符！")
    private String parentSeq;
    
    @NotNull(message="名称：不可为空！")
    @Size(max=7,message="名称：最大长度为7个字符！")
    private String name;
    
    @Null
    private Long ownerId;

    @NotNull(message="关键词：不可为空！")
    @Size(max=255,message="关键词：最大长度为255个字符！")
    private String keywords;
    
    @NotNull(message="描述：不可为空！")
    @Size(max=600,message="描述：最大长度为600个字符！")
    private String content;

    @Null
    private Date updateTime;
    
    @Null
    private String status;
    
    public  ThemeClass(){}
    
    public  ThemeClass(String name){
    		this.name = name;
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

	public List<ThemeClass> getChildren() {
		return children;
	}

	public void setChildren(List<ThemeClass> children) {
		this.children = children;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParentSeq() {
		return parentSeq;
	}

	public void setParentSeq(String parentSeq) {
		this.parentSeq = parentSeq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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