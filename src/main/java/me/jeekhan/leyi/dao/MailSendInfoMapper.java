package me.jeekhan.leyi.dao;

import java.util.List;

import me.jeekhan.leyi.dto.MailSendInfo;

public interface MailSendInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(MailSendInfo mailSendInfo);

    int updateByPrimaryKey(MailSendInfo record);
    //获取100条邮件记录用于发送
    List<MailSendInfo> selectMails4Send();
    
}