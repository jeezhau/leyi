package me.jeekhan.leyi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

@ControllerAdvice
public class GolbalExceptionHandler {
	
    @ExceptionHandler(Exception.class)  
    @ResponseStatus(HttpStatus.BAD_REQUEST)  
    public String processException(NativeWebRequest request, Exception e) {  
        System.out.println("全局异常：" + e.getMessage());  
        return "exception"; //返回一个逻辑视图名  
    } 

}


