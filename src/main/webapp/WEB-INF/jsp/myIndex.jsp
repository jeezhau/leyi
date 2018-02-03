<%--用户的个人主页 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="me.jeekhan.leyi.model.*,java.util.*,me.jeekhan.leyi.common.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="jk"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">  
  <title>${userInfo.username}的主页</title>
  <meta name="description" content="">
  <meta name="author" content="jeekhan">
  <link rel="shortcut icon" href="${contextPath}/images${contextPath}.ico" type="image/x-icon" />
  <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,400italic,700' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css">  
<script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>

</head>
<body style="background-color: #efefef;">  
<div style="height:38px;background-color:#b3b3ff ;margin-bottom:5px;">

</div>
<div class="container">
  <jk:topSysMenuBar></jk:topSysMenuBar>
  <div class="row">
      <!--====================左边用户简介  ===================--> 
    <div class="col-xs-3" >
	  <div class="row light-gray-bg">
	      <jk:targetUserResume></jk:targetUserResume>
	      <nav class="navbar navbar-info "  role="navigation">          
	        <ul class="nav nav-tabs nav-stacked">
	         <c:forEach items="${topThemes}" var="theme">
	         
	         </c:forEach>
			</ul>
	      </nav> 
      </div>       
    </div>
    <!--======================中间主要内容  ===================--> 
    <div class="col-xs-9 light-gray-bg">
    <c:forEach items="${articles}" var="item" varStatus="sta">
    	  <div class="panel panel-info" style="margin-bottom:0px;border-radius:0;">
	    <div class="panel-heading"><h4 class="panel-title"><a target="_blank" href="${contextPath}/${targetUser.username}/article_mgr/detail/${item.id}"> ${item.name} </a></h4></div>
		<div class="panel-body">${item.brief } </div>
	  </div> <!-- 文章panel -->
    </c:forEach>
    </div>
  </div><!-- end of row -->
  
  <jk:copyRight></jk:copyRight>	<!--页面底部相关说明 --> 
   
</div> <!-- end of container -->
 
</body>
</html> 