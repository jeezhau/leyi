<!-- 根据用户是否登录生成顶部菜单栏 -->
<%@ tag language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <div class="row">  
	<%--已登录用户的页面顶级功能菜单 --%>
	<c:if test="${not empty operator.userId and operator.userId > 0}">
	<ul class="nav nav-tabs pull-right" >
		<li><a href="/${serverName}/${operator.username }/theme_mgr/" >主题管理</a></li>
		<li><a href="/${serverName}/${operator.username }/article_mgr/" >文章管理</a></li>
		<li><a href="/${serverName}/${operator.username }/review" >信息审核</a></li>
		<li><a href="/${serverName}/${operator.username }">我的主页</a></li>
		<li><a href="/${serverName}/logout">退出</a></li>
	</ul>
	</c:if>
	<%--未登录用户的页面顶级功能菜单 --%>
	<c:if test="${empty operator.userId or operator.userId < 1}">
	<ul class="nav nav-tabs pull-right" >
		<li><a href="/leyi/login.jsp" target="_blank">登录</a></li>
		<li><a href="/leyi/register"  target="_blank">注册</a></li>
	</ul>
	</c:if>
  </div>
