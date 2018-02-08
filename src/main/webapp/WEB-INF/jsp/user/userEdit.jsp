<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="me.jeekhan.leyi.model.ThemeClass,java.util.*,me.jeekhan.leyi.common.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="jk"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">  
  <title>用户信息编辑</title>
  <meta name="description" content="">
  <meta name="author" content="jeekhan">
  <link rel="shortcut icon" href="${contextPath}/images/leyi.ico" type="image/x-icon" />
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
<jk:topSysMenuBar></jk:topSysMenuBar>
<div class="row">
  <div class="col-sm-3">
  <ul class="nav nav-pills nav-stacked">
	  <li id="ln_editBasic" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id^=edit]').hide();$('#editBasic').show(); "><a href="#">用户基本信息变更</a></li>
	  <li id="ln_editPwd" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id^=edit]').hide();$('#editPwd').show(); "><a href="#">密码变更</a></li>
	  <li id="ln_editPic" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id^=edit]').hide();$('#editPic').show(); "><a href="#">个人照片变更</a></li>
	  <li id="ln_editCode" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id^=edit]').hide();$('#editCode').show(); "><a href="#">邀请码信息</a></li>
  </ul>
  </div>
  <div class="col-sm-9">
   <!-- 用户基本信息修改 -->
   <div class="row" id="editBasic" style="display:none">
    <h3 style="text-align:center;margin:20px 0 ">用户基本信息</h3>
	<form class="form-horizontal" id="basicForm" action="updateBasic" method ="post" autocomplete="on" role="form" >
	  <div class="form-group">
	    <label for="username" class="col-xs-2 control-label">用户名<span style="color:red">*</span></label>
	    <div class="col-xs-5">
	      <input type="hidden" name="id" value="${targetUser.id }">
	      <input type="hidden" name="passwd" value="111111">
	      <input type="hidden" name="inviteCode" value="111111">
	      <input type="text" class="form-control" id="username" name="username" pattern="\w{3,20}" title="3-20个字符组成" maxLength=20 value="${targetUser.username}" required placeholder="请输入用户名（3-50个字符）">
	      <c:if test="${not empty valid.username}">
	      <div class="alert alert-warning alert-dismissable">${valid.username}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
	      </c:if>
	    </div>
	  </div>
	  <div class="form-group">
	    <label for=email class="col-xs-2 control-label">邮箱<span style="color:red">*</span></label>
	    <div class="col-xs-5">
	      <input class="form-control" type="email" id="email" name="email" value="${targetUser.email}" required maxLength=100 placeholder="请输入邮箱（最长100个字符）">
	      <c:if test="${not empty valid.email}">
	      <div class="alert alert-warning alert-dismissable">${valid.email}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
	      </c:if>
	    </div>
	  </div>	  	  
 	  <div class="form-group">
        <label  for="sex" class="col-xs-2 control-label">性别</label>
        <div class="col-xs-3">
          <select class="form-control" id="sex" name="sex">
           <option value="M">男</option>
           <option value="F">女</option>
           <option value="N">保密</option>
          </select>
        </div>
         <c:if test="${not empty valid.sex}">
	      <div class="alert alert-warning alert-dismissable">${valid.sex}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
	      </c:if>
	      
	    <label for="age" class="col-xs-2 control-label">生日</label>
        <div class="col-xs-3">
          <input class="form-control" type="date" id="birthday" name="birthday" placeholder="请输入年龄" value="${targetUser.birthday }">
          <c:if test="${not empty valid.birthday}">
	      <div class="alert alert-warning alert-dismissable">${valid.birthday}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
	      </c:if>
        </div>
      </div>    
      <div class="form-group">
        <label for="city" class="col-xs-2 control-label">所在城市</label>
        <div class="col-xs-3">
          <input class="form-control" id="city" name="city" maxLength=50 placeholder="请输入城市" value="${targetUser.city }">
        </div>
        <c:if test="${not empty valid.city}">
	      <div class="alert alert-warning alert-dismissable">${valid.city}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
	      </c:if>
	      
	    <label for="profession" class="col-xs-2 control-label">职业</label>
        <div class="col-xs-3">
          <input class="form-control" id="profession" name="profession"  maxLength=100  placeholder="请输入职业"  value="${targetUser.profession }">
        </div>
        <c:if test="${not empty valid.profession}">
	    <div class="alert alert-warning alert-dismissable">${valid.profession}
	       <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	    </div>
	    </c:if>
      </div> 
      <div class="form-group">
        <label for="favourite" class="col-xs-2 control-label">兴趣爱好</label>
        <div class="col-xs-5">
          <input class="form-control" id="favourite" name="favourite"  maxLength=100  placeholder="请输入兴趣爱好" value="${targetUser.favourite }">
        </div>
        <c:if test="${not empty valid.favourite}">
	      <div class="alert alert-warning alert-dismissable">${valid.favourite}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
	      </c:if>
      </div>       
      <div class="form-group">
        <label for="introduce" class="col-xs-2 control-label">个人简介<span style="color:red">*</span></label>
        <div class="col-xs-8">
          <textarea class="form-control" id="introduce" name="introduce"  maxLength=600 rows=5 required > ${targetUser.introduce} </textarea>
        </div>
      </div>
      <c:if test="${not empty valid.introduce}">
      <div class="alert alert-warning alert-dismissable">${valid.introduce}
        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
      </div>
      </c:if>
      <div class="form-group">
         <div class="col-sm-offset-4 col-sm-10">
           <button type="submit" class="btn btn-info" id="save" style="margin:20px">&nbsp;&nbsp;提 交&nbsp;&nbsp;</button>
           <button type="button" class="btn btn-warning" id="reset" style="margin:20px">&nbsp;&nbsp;重 置&nbsp;&nbsp; </button>
         </div>
      </div>
	</form>
  </div>
  
  <!-- 用户密码修改 -->
  <div class="row" style="display:none" id="editPwd">
    <h3 style="text-align:center;margin:20px 0 ">密码变更</h3>
	<form class="form-horizontal" id="pwdForm" action="updatePwd" method ="post" autocomplete="on" role="form" >
	  <div class="form-group">
	    <input type="hidden" name="userId" value="${targetUser.id }">
	    <label for="old_password" class="col-xs-2 control-label">原密码<span style="color:red">*</span></label>
	    <div class="col-xs-3">
	      <input class="form-control" type="password" id="old_password" name="old_passwd" title="6-20个字符，最好包含大小字符，数字和符号" pattern="\w{6,20}" maxLength=20 required autocomplete="off" placeholder="请输入密码">
	      <c:if test="${not empty valid.passwd}">
	      <div class="alert alert-warning alert-dismissable">${valid.passwd}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
	      </c:if>
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="password" class="col-xs-2 control-label">新密码<span style="color:red">*</span></label>
	    <div class="col-xs-3">
	      <input class="form-control" type="password" id="password" name="new_passwd" title="6-20个字符，最好包含大小字符，数字和符号" pattern="\w{6,20}" maxLength=20 required autocomplete="off" placeholder="请输入密码">
	      <c:if test="${not empty valid.passwd}">
	      <div class="alert alert-warning alert-dismissable">${valid.passwd}
	        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	      </div>
	      </c:if>
	    </div>
	    <label for="re-password" class="col-xs-2 control-label">确认密码<span style="color:red">*</span></label>
	    <div class="col-xs-3">
	      <input class="form-control" type="password" id="re-password" pattern="\w{6,20}" maxLength=20 required autocomplete="off" placeholder="请输入确认密码">
	    </div>
	  </div>
      <div class="form-group">
         <div class="col-sm-offset-4 col-sm-10">
           <button type="submit" class="btn btn-info" id="save" style="margin:20px" onclick="return checkPwdSame()">&nbsp;&nbsp;提 交&nbsp;&nbsp;</button>
           <button type="button" class="btn btn-warning" id="reset" style="margin:20px">&nbsp;&nbsp;重 置&nbsp;&nbsp; </button>
         </div>
      </div>
	</form>
  </div>
  <!-- 用户头像修改 -->
  <div class="row" style="display:none" id="editPic">
    <h3 style="text-align:center;margin:20px 0 ">个人照片</h3>
	<form class="form-horizontal" id="picForm" action="changePic" method ="post" autocomplete="on" enctype="multipart/form-data" role="form" >
		<div class="thumbnail">
		   <img src="${contextPath}/common/showPic/${targetUser.username}/${targetUser.picture }" alt="惹人靓照">
		</div>
	  <div class="form-group">
	    <input type="hidden" name="userId" value="${targetUser.id }">
        <label for="picFile" class="col-xs-2 control-label">照片</label>
        <div class="col-xs-5">
          <input id="picFile"  type="file" name="picFile" type="file" accept="image/*" class="file-loading">
        </div>
      </div>
      <div class="form-group">
         <div class="col-sm-offset-4 col-sm-10">
           <button type="submit" class="btn btn-info" id="save" style="margin:20px">&nbsp;&nbsp;提 交&nbsp;&nbsp;</button>
           <button type="button" class="btn btn-warning" id="reset" style="margin:20px">&nbsp;&nbsp;重 置&nbsp;&nbsp; </button>
         </div>
      </div>
	</form>
  </div>
  <!-- 用户其他信息展示 -->
  <div class="row" style="display:none" id="editCode">
    <h3 style="text-align:center;margin:20px 0 ">邀请码信息</h3>
	<form class="form-horizontal" id="codeForm" action="" method ="post" autocomplete="on" role="form" >
	  <div class="form-group">
        <label for="introduce" class="col-xs-2 control-label">邀请码</label>
        <div class="col-xs-6">
          <input class="form-control" type="text" id="txtInviteCode" value="${availCode}" readonly>
        </div>
        <div class="col-xs-2">
          <c:if test="${not empty availCode}"><button type="button" class="btn btn-primary"id="btnCreateCode" >生成</button></c:if>
          <c:if test="${empty availCode}"><button type="button" class="btn btn-primary" id="btnCreateCode" >生成</button></c:if>
        </div>
      </div>
	  <div class="form-group">
        <label for="introduce" class="col-xs-2 control-label">注册地址</label>
        <div class="col-xs-8">
          <input class="form-control" type="text" id="linkInviteCode" value="http://www.jeekhan.me/${contextPath}/register.jsp?inviteCode=${availCode}" readonly>
        </div>
      </div>
	</form>
  </div>
  <jk:copyRight></jk:copyRight>	<!--页面底部相关说明 -->
 </div> 
</div>
</div>

<c:if test="${not empty error}">
<!-- 错误提示模态框（Modal） -->
<div class="modal fade " id="tipModal" tabindex="-1" role="dialog" aria-labelledby="tipTitle" aria-hidden="true" data-backdrop="static">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"  aria-hidden="true">× </button>
            <h4 class="modal-title" id="tipTitle">提示信息</h4>
         </div>
         <div class="modal-body">
           ${error}
         </div>
         <div class="modal-footer">
         	<div style="margin-left:50px">
             <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
         </div>
      </div><!-- /.modal-content -->
   </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script>
	$('#tipModal').modal('show')
</script>
</c:if>
<script>

$(document).on('ready', function() {
	//页面初始化
	var mode = "${mode}";
	if(mode == ''){
		mode = 'editBasic';
	}
	$('#ln_'+mode).click();
	$('#sex').val('${targetUser.sex}');		
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
    
    $('#btnCreateCode').click(function(){
    		$.ajax({
    		  url: '${contextPath}/${targetUser.username}/user_mgr/createAvailCode',
    		  data: null,
    		  success: function(data,status,xhr){
    			  if(data && data.code){
    				  $('#txtInviteCode').val(data.code);
    				  $('#linkInviteCode').val('http://www.jeekhan.me/${contextPath}/register.jsp?inviteCode=' + data.code);
    				  $('#btnCreateCode').attr('disabled',true);
    			  }else{
    				  alert('生成邀请码失败！')
    			  }
    		  },
    		  dataType: 'json'
    		});
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