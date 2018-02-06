<%--系统错误页面 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,me.jeekhan.leyi.common.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
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
  <link rel="shortcut icon" href="/leyi/images/leyi.ico" type="image/x-icon" />
  <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css">  
  <script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
  <script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>

</head>
<body style="background-color: #efefef;">  
<div id="topBlankArea" style="height:38px;background-color:#b3b3ff;margin-bottom:5px; ">
<!-- 页面顶部留白 -->
</div>
<div class="container">
  
<c:if test="${not empty param.error}">
	<!-- 错误提示模态框（Modal） -->
	<div class="modal fade " id="errorModal" tabindex="-1" role="dialog" aria-labelledby="errorTitle" aria-hidden="false" data-backdrop="static">
   		<div class="modal-dialog">
      		<div class="modal-content">
         		<div class="modal-header">
            			<button type="button" class="close" data-dismiss="modal"  aria-hidden="true">× </button>
            			<h4 class="modal-title" id="errorTitle">系统错误</h4>
         		</div>
         		<div class="modal-body">
           			<p> ${param.error} </p><p/>
           			<p>如果您还没登录，请先登录！<a href="${contextPath}/login.jsp">登录</a></p>
         		</div>
         		<div class="modal-footer">
         			<div style="margin-left:50px">
             			<button type="button" class="btn btn-default" onclick="window.opener=null;window.open('','_self');window.close();" >关闭</button>
            			</div>
         		</div>
      		</div><!-- /.modal-content -->
   		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
<script>
$("#errorModal").modal('show');
</script>
</c:if>
</div>
</body>
</html>