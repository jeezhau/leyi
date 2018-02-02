package me.jeekhan.leyi.interceptor;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LogInterceptor extends HandlerInterceptorAdapter{
	Logger log = LoggerFactory.getLogger(LogInterceptor.class);
	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
        String requestUri = request.getRequestURI();  
        String contextPath = request.getContextPath();  
        String url = requestUri.substring(contextPath.length());  
        
        log.info("requestUri:"+requestUri);    
//		Enumeration<String> attrs = request.getAttributeNames();
//		System.out.println("===========属性：==============");
//		while(attrs.hasMoreElements()){
//			String attr = attrs.nextElement();
//			System.out.println(attrs + ":" + request.getAttribute(attr));
//		}
//		System.out.println("===========参数：==============");
//		Map<String,String[]> params = request.getParameterMap();
//		for(Map.Entry<String, String[]> entry:params.entrySet()){
//			System.out.println(entry.getKey() + Arrays.toString(entry.getValue()));
//		}
	}

}
