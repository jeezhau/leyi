package me.jeekhan.leyi.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import me.jeekhan.leyi.dto.Operator;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	Logger log = LoggerFactory.getLogger(LoginInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws ServletException, IOException{
		
        //String requestUri = request.getRequestURI();  
        String contextPath = request.getContextPath();  
        //String url = requestUri.substring(contextPath.length());   
          
       Operator operator =  (Operator)request.getSession().getAttribute("operator");   
        if(operator == null || operator.getUserId() == 0){  
            log.info("Interceptor：跳转到login页面！");  
            response.sendRedirect(contextPath + "/login.jsp");
            return false;  
        }else {
            return true; 
        }
	}
}
