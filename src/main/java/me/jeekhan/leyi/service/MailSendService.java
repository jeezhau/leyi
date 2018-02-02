package me.jeekhan.leyi.service;

import java.util.List;

import me.jeekhan.leyi.dto.MailSendInfo;

public interface MailSendService {
	/**
	 * 保存邮件发送信息
	 * @param mailSendInfo
	 * @return
	 */
	public int saveMailInfo(MailSendInfo mailSendInfo);
	
	/**
	 * 保存邮件发送信息
	 * @param mailSendInfo
	 * @return
	 */
	public int updateMailInfo(MailSendInfo mailSendInfo);
	/**
	 * 待审核信息提现邮件添加
	 * @return
	 */
	public int add4ReviewWarn();
	
	/**
	 * 获取待发送邮件（100条）
	 * @return
	 */
	List<MailSendInfo> getMails4Send();

}
