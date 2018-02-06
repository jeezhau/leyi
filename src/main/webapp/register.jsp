<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,me.jeekhan.leyi.common.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="jk"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">  
  <title>欢迎-注册</title>
  <meta name="description" content="">
  <meta name="author" content="jeekhan">
  <link rel="shortcut icon" href="${contextPath}/images${contextPath}.ico" type="image/x-icon" />
  <link rel="stylesheet" href="${contextPath}/bootstrap-3.3.5/css/bootstrap.min.css">  
  <link rel="stylesheet" href="${contextPath}/bootstrap-3.3.5/css/fileinput.min.css">  
  <script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>

  <script src="${contextPath}/bootstrap-3.3.5/js/fileinput.min.js"></script>
  <script src="${contextPath}/bootstrap-3.3.5/js/zh.js"></script>
  
  <script src="${contextPath}/bootstrap-3.3.5/js/bootstrap.min.js"></script>

  <script src="${contextPath}/ckeditor/ckeditor.js"></script>
</head>
<body>
<div style="height:38px;background-color:#b3b3ff ;margin:0 0 10px 0">

</div>
<div class="container" > 
  <h3 style="text-align:center;margin:20px 0 ">用户注册</h3>
  <div class="row">
	<form class="form-horizontal" id="registerForm" action="createUser" method ="post" autocomplete="on" enctype="multipart/form-data" role="form" >
	  <div class="form-group">
	    <label for="inviteCode" class="col-xs-2 control-label">邀请码<span style="color:red">*</span></label>
	    <div class="col-xs-5">
	      <input type="text" class="form-control" id="inviteCode" name="inviteCode" title="邀请码" value="${param.inviteCode}" required>
	      <c:if test="${not empty inviteCode}">
	      <div class="alert alert-warning alert-dismissable">${inviteCode}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
	      </c:if>
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="username" class="col-xs-2 control-label">用户名<span style="color:red">*</span></label>
	    <div class="col-xs-5">
	      <input type="hidden" name="inviteCode" >
	      <input type="text" class="form-control" id="username" name="username" pattern="\w{3,20}" title="3-20个字符组成" maxLength=20 value="${param.username}" required placeholder="请输入用户名（3-50个字符）">
	      <c:if test="${not empty username}">
	      <div class="alert alert-warning alert-dismissable">${username}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
	      </c:if>
	    </div>
	  </div>
	  <div class="form-group">
	    <label for=email class="col-xs-2 control-label">邮箱<span style="color:red">*</span></label>
	    <div class="col-xs-5">
	      <input class="form-control" type="email" id="email" name="email" value="${param.email}" required maxLength=100 placeholder="请输入邮箱（最长100个字符）">
	      <c:if test="${not empty email}">
	      <div class="alert alert-warning alert-dismissable">${email}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
	      </c:if>
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="password" class="col-xs-2 control-label">密码<span style="color:red">*</span></label>
	    <div class="col-xs-5">
	      <input class="form-control" type="password" id="password" name="passwd" title="6-20个字符，最好包含大小字符，数字和符号" pattern="\w{6,20}" maxLength=20 required autocomplete="off" placeholder="请输入密码">
	      <c:if test="${not empty passwd}">
	      <div class="alert alert-warning alert-dismissable">${passwd}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
	      </c:if>
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="re-password" class="col-xs-2 control-label">确认密码<span style="color:red">*</span></label>
	    <div class="col-xs-5">
	      <input class="form-control" type="password" id="re-password" pattern="\w{6,20}" maxLength=20 required autocomplete="off" placeholder="请输入确认密码">
	    </div>
	  </div>	  	  
	  <div class="form-group">
        <label for="age" class="col-xs-2 control-label">生日</label>
        <div class="col-xs-3">
          <input class="form-control" type="date" id="birthday" name="birthday" placeholder="请输入生日（2010-09-09）" value="${param.birthday }">
          <c:if test="${not empty birthday}">
	      <div class="alert alert-warning alert-dismissable">${birthday}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
	      </c:if>
        </div>
      </div>
 	  <div class="form-group">
        <label  for="sex" class="col-xs-2 control-label">性别</label>
        <div class="col-xs-3">
          <select class="form-control" id="sex" name="sex" >
           <option value="M">男</option>
           <option value="F">女</option>
           <option value="N">保密</option>
          </select>
        </div>
         <c:if test="${not empty sex}">
	      <div class="alert alert-warning alert-dismissable">${sex}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
	      </c:if>
      </div>    
      <div class="form-group">
        <label for="city" class="col-xs-2 control-label">所在城市</label>
        <div class="col-xs-3">
          <input class="form-control" id="city" name="city" maxLength=50 placeholder="请输入城市" value="${param.city }">
        </div>
        <c:if test="${not empty city}">
	      <div class="alert alert-warning alert-dismissable">${city}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
	      </c:if>
      </div> 
      <div class="form-group">
        <label for="favourite" class="col-xs-2 control-label">兴趣爱好</label>
        <div class="col-xs-5">
          <input class="form-control" id="favourite" name="favourite"  maxLength=100  placeholder="请输入兴趣爱好" value="${param.favourite }">
        </div>
        <c:if test="${not empty favourite}">
	      <div class="alert alert-warning alert-dismissable">${favourite}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
	      </c:if>
      </div> 
      <div class="form-group">
        <label for="profession" class="col-xs-2 control-label">职业</label>
        <div class="col-xs-5">
          <input class="form-control" id="profession" name="profession"  maxLength=100   placeholder="请输入职业" value="${param.profession }">
        </div>
        <c:if test="${not empty profession}">
	    <div class="alert alert-warning alert-dismissable">${profession}
	       <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	    </div>
	    </c:if>
      </div>       
      <div class="form-group">
        <label for="introduce" class="col-xs-2 control-label">个人简介<span style="color:red">*</span></label>
        <div class="col-xs-8">
          <textarea class="form-control" id="introduce" name="introduce"  maxLength=600 rows=5 required >${param.introduce }</textarea>
        </div>
      </div>
      <c:if test="${not empty introduce}">
      <div class="alert alert-warning alert-dismissable">${introduce}
        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
      </div>
      </c:if>
      <div class="form-group">
        <label for="introduce" class="col-xs-2 control-label">照片</label>
        <div class="col-xs-5">
          <input id="picFile"  type="file" name="picFile" type="file" accept="image/*" class="file-loading">
        </div>
      </div>
      <div class="form-group">
         <div class="col-sm-offset-5 col-sm-10">
           <button type="submit" class="btn btn-info" id="save" style="margin:20px" onclick="return checkPwdSame()">&nbsp;&nbsp;提 交&nbsp;&nbsp;</button>
           <button type="button" class="btn btn-warning" id="reset" style="margin:20px">&nbsp;&nbsp;重 置&nbsp;&nbsp; </button>
         </div>
      </div>
      
	</form>
  </div>
  <jk:copyRight></jk:copyRight>
</div>
<c:if test="${not empty param.error}">
<!-- 错误提示模态框（Modal） -->
<div class="modal fade " id="tipModal" tabindex="-1" role="dialog" aria-labelledby="tipTitle" aria-hidden="false" data-backdrop="static">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"  aria-hidden="true">× </button>
            <h4 class="modal-title" id="tipTitle">提示信息</h4>
         </div>
         <div class="modal-body">
           ${param.error}
         </div>
         <div class="modal-footer">
         	<div style="margin-left:50px">
             <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
         </div>
      </div><!-- /.modal-content -->
   </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script type="text/javascript">
$("#tipModal").modal('show');
</script>
</c:if>
<script>
$(document).on('ready', function() {
	$('#sex').val('${param.sex}');
    $("#picFile").fileinput({
    	language: 'zh', //设置语言
        uploadUrl: '', //上传的地址
        showUpload: false, //是否显示上传按钮
        previewFileType: "image",
        browseClass: "btn btn-success",
        browseLabel: "Pick Image",
        browseIcon: "<i class=\"glyphicon glyphicon-picture\"></i> ",
        removeClass: "btn btn-danger",
        removeLabel: "Delete",
        removeIcon: "<i class=\"glyphicon glyphicon-trash\"></i> ",
        uploadClass: "btn btn-info",
        uploadLabel: "Upload",
        uploadIcon: "<i class=\"glyphicon glyphicon-upload\"></i> "
    });
});

function checkPwdSame(){
	if($('#password').val() === $('#re-password').val()){
		return true;
	}else{
		alert('确认密码与密码不一致！');
		return false;
	}
}

</script>

</body>
</html>