<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
  <link rel="shortcut icon" href="/leyi/images/leyi.ico" type="image/x-icon" />
  <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,400italic,700' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css">  
<script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>

</head>
<body style="background-color: #efefef;">  
<div style="height:38px;background-color:#b3b3ff ;margin-bottom:5px;">

</div>
<div class="container">
  <div class="row">
  <c:if test="${not empty operator.userId and operator.userId > 0}">
  	<ul class="nav nav-tabs pull-right" >
  	 <li><a href="/leyi/${userInfo.username}/user_mgr/edit">个人信息</a></li>
  	 <li><a href="/leyi/${operator.username }/theme_mgr/" >分类管理</a></li>
     <li><a href="/leyi/${operator.username }/article_mgr/" >文章管理</a></li>
     <li><a href="/leyi/${operator.username }/review" >信息审核</a></li>
     <li><a href="/leyi/logout">退出</a></li>
	</ul>
   </c:if>
   <c:if test="${empty operator.userId or operator.userId < 1}">
    <ul class="nav nav-tabs pull-right" >
     <li><a href="/leyi/login.jsp" target="_blank">登录</a></li>
	</ul>
	</c:if>
  </div>
  <div class="row">
    <!--======================中间主要内容  ===================--> 
    <div class="col-xs-9 light-gray-bg">
	  <jk:topThemeBar></jk:topThemeBar>	<!-- 顶部主题分类-->
	  <c:forEach items="${articleBriefs}" var="item">	 <!-- 文章panel -->	  
	  <div class="panel panel-info" style="margin-bottom:0px;border-radius:0;">
        <div class="panel-heading"><h4 class="panel-title"><a target="_blank" href="/leyi/${userInfo.username}/article/${item.id}">${item.name}</a></h4></div>
	    <div class="panel-body">${item.brief}</div>
	  </div>
	  </c:forEach>
    </div>
    <!--====================右边菜单链接  ===================--> 
    <div class="col-xs-3" >
	  <div class="row light-gray-bg">
	      <ol class="breadcrumb " style="margin:10px 0;">
		   <c:forEach items="${themeTreeUp}" var="item">
		    <c:if test="${currTheme.id==item.id}"> <li class="active">${item.name}</li> </c:if>
		    <c:if test="${currTheme.id!=item.id}"> <li><a href="${item.id}">${item.name}</a></li> </c:if>
		   </c:forEach>
		  </ol>
	      <nav class="navbar navbar-info "  role="navigation">          
	        <ul class="nav nav-tabs nav-stacked">
	         <c:forEach items="${children}" var="item">
	          <c:if test="${currTheme.id==item.id}"> <li class="active"><a href="/leyi/${userInfo.username}/theme/${item.id}"> > ${item.name}</a></li></c:if>
	          <c:if test="${currTheme.id!=item.id}"> <li><a href="/leyi/${userInfo.username}/theme/${item.id}"> > ${item.name}</a></li></c:if>
	         </c:forEach>
			</ul>
	      </nav> 
      </div>       
    </div>
  </div><!-- end of row -->
  
  <jk:copyRight></jk:copyRight>	<!--页面底部相关说明 --> 
   
</div> <!-- end of container -->
 
</body>
</html> 