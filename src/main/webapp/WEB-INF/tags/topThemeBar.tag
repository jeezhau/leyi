<!-- 顶级主题显示菜单栏 -->
<%@ tag language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <ul class="nav nav-pills nav-justified" style="background-color:#66ccff;margin-bottom:10px;">
  <c:forEach items="${topThemes}" var="item">
    <li>
      <a href="/${serverName}/${userInfo.username}/theme/${item.id}">${item.name}</a>
    </li>
  </c:forEach>
 </ul> 
