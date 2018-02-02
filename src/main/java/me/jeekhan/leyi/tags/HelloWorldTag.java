package me.jeekhan.leyi.tags;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class HelloWorldTag extends SimpleTagSupport{
	
	@Override
	public void doTag() throws IOException{
		JspWriter out = this.getJspContext().getOut();
		out.write("hi,jee");
	}

}
