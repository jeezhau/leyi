<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">  
    <title>欢迎 - 登录</title>
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="css/font-awesome.min.css" rel="stylesheet">
    <link href="css/templatemo-style.css" rel="stylesheet">
    <link rel="shortcut icon" href="/leyi/images/leyi.ico" type="image/x-icon" />
    <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css">  
    <script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</head>
<body class="light-gray-bg">
	<div class="templatemo-content-widget templatemo-login-widget white-bg">
		<header class="text-center">
          <div class="square"></div>
          <h1>欢迎</h1>
        </header>
        <form action="login" class="templatemo-login-form" method="post">
        	<div class="form-group">
        		<div class="input-group">
	        		<div class="input-group-addon"><i class="fa fa-user fa-fw"></i></div>	        		
	            <input class="form-control" type="text" name="username" required placeholder="用户名/邮箱">           
	        </div>	
        	</div>
        	<div class="form-group">
        		<div class="input-group">
	        		<div class="input-group-addon"><i class="fa fa-key fa-fw"></i></div>	        		
	            <input class="form-control" type="password" name="password"  required placeholder="******">           
	        </div>	
        	</div>
			<div class="form-group">
				<button type="submit" class="templatemo-blue-button width-100">登录</button>
			</div>
        </form>
	</div>
	<div class="templatemo-content-widget templatemo-login-widget templatemo-register-widget white-bg">
		<p>还未有账号? 请向好友索取邀请码并注册</p>
	</div>
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
         		</div>
      		</div><!-- /.modal-content -->
   		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
<script>
$("#errorModal").modal('show');
</script>
</c:if>
</body>
</html>