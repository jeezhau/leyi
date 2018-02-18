package me.jeekhan.leyi.tags;

import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import me.jeekhan.leyi.component.WebContextBean;
import me.jeekhan.leyi.service.RoleService;

/**
 * 根据角色判断当前用户是否有该功能的使用权限
 * @author jeekhan
 *
 */
public class Jurisdiction extends SimpleTagSupport{
	
	@Override
	public void doTag() {
		ApplicationContext applicationContext = WebContextBean.getApplicationContext();
		
		
	}
	

}
