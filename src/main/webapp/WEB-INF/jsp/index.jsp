<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.jeekhan.me/leyi/" prefix="leyi" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="jk"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">  
  <title>一叶知秋</title>
  <meta name="description" content="">
  <meta name="author" content="jeekhan">
  <link rel="shortcut icon" href="/leyi/images/leyi.ico" type="image/x-icon" />
  <link href="css/font-awesome.min.css" rel="stylesheet">
  <link href="css/templatemo-style.css" rel="stylesheet">
  <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css">  
  <script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
  <script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>

</head>
<body style="background-color: #efefef;">  
<div style="height:38px;background-color:#b3b3ff;margin-bottom:5px; ">

</div>
<div class="container">
	<jk:loginMenuBar></jk:loginMenuBar>
	
  <div class="row">
    <div class="col-md-3" ><jk:individualResume></jk:individualResume></div>	<!--左边个人简介  --> 
    <div class="col-md-9 light-gray-bg">	<!--中间主要内容 --> 
	 <c:forEach items="${hotnew}" var="item">
	  <div class="panel panel-info" style="margin-bottom:0px;border-radius:0;">
	    <div class="panel-heading"><h4 class="panel-title"><a target="_blank" href="/leyi/${userInfo.username}/article/${item.id}">${item.name}</a></h4></div>
		<div class="panel-body">${item.brief}</div>
	  </div> <!-- 文章panel -->
	 </c:forEach>
	</div>
  </div><!-- end of row -->
  <jk:copyRight></jk:copyRight><!--页面底部相关说明 --> 
</div> <!-- end of container -->
 
</body>
</html> 
