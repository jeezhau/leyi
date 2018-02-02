<!-- 用户的个人简介 -->
<%@ tag language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = "";
    String desc = "";
	if(request.getServletPath().contains("userShow")){
		path = "";
		desc = " 的主页";
	}else{
		path = "/detail";
		desc = " 的个人信息";
	}
%>

<div class="thumbnail">
   <img src="/leyi/common/showPic/${userInfo.username}/${userInfo.picture }" alt="惹人靓照">
   <div class="caption">
   <h3><a href="/leyi/${userInfo.username}<%=path%>"><b>${userInfo.username}<%=desc%></b></a></h3>
   <p>${userInfo.introduce}</p>
   </div>
</div>
