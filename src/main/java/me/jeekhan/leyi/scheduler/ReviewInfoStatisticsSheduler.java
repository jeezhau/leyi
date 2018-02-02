package me.jeekhan.leyi.scheduler;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import me.jeekhan.leyi.service.MailSendService;

//@Component
public class ReviewInfoStatisticsSheduler {
	private String prop_file = "props/mail_tpl";
	private ResourceBundle BUNDLE = ResourceBundle.getBundle(prop_file);
    
	//@Autowired
	private MailSendService mailSendService;
	
	/**
	 * 添加待审核信息提醒邮件记录
	 */
	//@Scheduled(cron="0 1 8-22/2 * * ? ")
	public void get4ReviewInfo(){
		System.out.println("添加待审核信息提醒邮件记录");
		mailSendService.add4ReviewWarn();
	}
	
	
	/**
	 * 根据指定key获取对应的值
	 * @param key
	 * @param params
	 * @return
	 */
	public String getForamtValue(String key,Object[] params) {
		String keyValue = null;
		String msg = null;
		try{
			keyValue = BUNDLE.getString(key);
			MessageFormat mf = new MessageFormat(keyValue); 
			msg = mf.format(params);  
		}catch(Exception e){
			return null;
		}
		return msg;
	}

}
