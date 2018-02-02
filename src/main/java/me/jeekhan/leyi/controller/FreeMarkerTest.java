package me.jeekhan.leyi.controller;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import me.jeekhan.leyi.common.HtmlGeneratorFromFtl;
import me.jeekhan.leyi.common.SysPropUtil;

@Controller
@RequestMapping(value="/ftl")
public class FreeMarkerTest {
	@RequestMapping(value="test")
	public String test(Map<String,Object> map) throws Exception{
		map.put("username", "赵，jee");
		String htmldir = SysPropUtil.getParam("DIR_TEMPFILE");
		File file = HtmlGeneratorFromFtl.genHtml("test.ftl",htmldir,UUID.randomUUID().toString()+ ".html",map);
		return "test";
	}
	
}
