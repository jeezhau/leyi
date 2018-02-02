package me.jeekhan.leyi.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ReviewInfo {
    private Long id;

    @NotNull(message="审批对象：不可为空！")
    private String objName;
    
    @NotNull(message="审批对象ID：不可为空！")
    private Long keyId;

    private String reviewInfo;

    private Long reviewOpr;

    private Date reviewTime;
    
    @NotNull(message="审批结果：不可为空！")
    @Pattern(regexp="^[AR]$",message="审批结果：取值只可为【A-接收，R-拒绝】")
    private String result;

    private String originalInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getOriginalInfo() {
        return originalInfo;
    }

    public void setOriginalInfo(String originalInfo) {
        this.originalInfo = originalInfo;
    }
}