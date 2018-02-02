package me.jeekhan.leyi.dto;

import java.util.Date;

public class MailSendInfo {
    private Integer id;

    private String subject;
    
    private String content;
    
    private String toAddr;
    
    private String ccAddr;
    
    private String bccAddr;
    
    private String attachfileNames;

    private Date crtTime;

    private Date sendTime;

    private Integer sendNum;

    private String failMsg;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    
    public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getToAddr() {
		return toAddr;
	}

	public void setToAddr(String toAddr) {
		this.toAddr = toAddr;
	}

	public String getCcAddr() {
		return ccAddr;
	}

	public void setCcAddr(String ccAddr) {
		this.ccAddr = ccAddr;
	}

	public String getBccAddr() {
		return bccAddr;
	}

	public void setBccAddr(String bccAddr) {
		this.bccAddr = bccAddr;
	}

	public String getAttachfileNames() {
		return attachfileNames;
	}

	public void setAttachfileNames(String attachfileNames) {
		this.attachfileNames = attachfileNames;
	}

	public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getSendNum() {
        return sendNum;
    }

    public void setSendNum(Integer sendNum) {
        this.sendNum = sendNum;
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}