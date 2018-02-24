package me.jeekhan.leyi.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import me.jeekhan.leyi.dto.Operator;
/**
 * 信息审核
 * @author Jee Khan
 *
 */
@Controller
@SessionAttributes({"operator"})
@RequestMapping(value="/{username}")
public class ReviewAction {

	/**
	 * 审核管理页面初始化
	 * 【权限】
	 * 	1、仅登录的管理员可执行该操作；
	 *  
	 * @param operator
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/review",method=RequestMethod.GET)
	public String review(@ModelAttribute("operator")Operator operator,Map<String,Object> map){
		//权限控制
		//if(operator.getUserId()>0 && operator.getLevel() >= 9){
			return "reviewMgr";
//		}else{
//			return "redirect:/";	//跳转至系统主页
//		}
	}

}


