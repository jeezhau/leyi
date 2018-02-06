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
  <title>乐学、乐享，快乐生活</title>
  <meta name="description" content="">
  <meta name="author" content="jeekhan">
  <link rel="shortcut icon" href="${contextPath}/images/leyi.ico" type="image/x-icon" />
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
  <jk:topSysMenuBar></jk:topSysMenuBar>
  <div class="row">
    <div class="col-md-3" ><!--左边明星简介  --> 
      <div style="background-color: #e6e6e6;" align="center">本期明星用户</div>
      <c:forEach items="${hotUsers}" var="user">
      <div class="thumbnail">
	    <p align="center"> ${user.username} </p>
	    <a target="_blank" href="${contextPath}/${user.username}/user_mgr/detail"><img src="${contextPath}/common/showPic/${user.username}/${user.picture }" alt="惹人靓照"></a>
	    <div class="caption">
		  <p>${user.introduce}</p><!-- 个人简介 -->
	    </div>
      </div>
      </c:forEach>
    </div>
    <div class="col-md-9 light-gray-bg">	<!--中间主要内容 --> 
	 <c:forEach items="${hotArticles}" var="article">
	  <div class="panel panel-info" style="margin-bottom:0px;border-radius:0;">
	    <div class="panel-heading"><h4 class="panel-title"><a target="_blank" href="${contextPath}/${userInfo.username}/article_mgr/detail/${article.id}">${article.name}</a></h4></div>
		<div class="panel-body">${article.brief}</div>
	  </div> <!-- 文章panel -->
	 </c:forEach>
	</div>
  </div><!-- end of row -->
  <jk:copyRight></jk:copyRight><!--页面底部相关说明 --> 
</div> <!-- end of container -->
 
</body>
</html> 
