package me.jeekhan.leyi.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;


//import freemarker.template.Configuration;
//import freemarker.template.Template;
import me.jeekhan.leyi.component.WebContextBean;

public class HtmlGeneratorFromFtl {
//	private static  Configuration freemarker_cfg = null;
//	private static Configuration getConfig() throws Exception{
//		if(freemarker_cfg == null){
//			String ftlBaseDir = WebContextBean.getServletContext().getRealPath("WEB-INF/ftl");
//			File ftlDir = new File(ftlBaseDir);
//			if(!ftlDir.exists()){
//				throw new Exception("模板文件目录不存在！");
//			}
//			freemarker_cfg = new Configuration(Configuration.getVersion());
//			freemarker_cfg.setDirectoryForTemplateLoading(ftlDir);
//			freemarker_cfg.setDefaultEncoding("utf-8");
//		}
//		return freemarker_cfg;
//	}
//
	public static File genHtml(String ftlFileName,String htmlBaseDir,String htmlFileName,Map<String,Object> map) throws Exception{
//		
//		File htmlBase = new File(htmlBaseDir);
//		if(!htmlBase.exists()){
//			htmlBase.mkdirs();
//		}
//		
//		Configuration cfg = getConfig();
//		Template tpl = cfg.getTemplate(ftlFileName, "utf-8");
//		File htmlFile = new File( htmlBaseDir + htmlFileName);  
//		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));  
//		tpl.process(map, out);  
//		out.flush();  
//		out.close();  
//		return htmlFile;
		return null;
	}	
	
}

