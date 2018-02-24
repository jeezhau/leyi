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
  <title>个人信息中心</title>
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
  
  <script src="${contextPath}/js/common.js"></script>
  
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/css/bootstrap-select.min.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/i18n/defaults-zh_CN.min.js"></script>
</head>
<body  style="background-color: #efefef;">
<div style="height:38px;background-color:#b3b3ff ;margin:0 0 10px 0">

</div>
<div class="container" > 
<jk:topSysMenuBar></jk:topSysMenuBar>
<div class="row">
<h3 align="center">个人信息中心</h3>
  <div class="col-sm-3">
    <div class="panel panel-info">
 	    <div class="panel-body" >
		    <ul class="nav nav-pills nav-stacked">
			  <li id="ln_editBasic" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id^=edit]').hide();$('#editBasic').show(); "><a href="#">用户基本信息变更</a></li>
			  <li id="ln_editPwd" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id^=edit]').hide();$('#editPwd').show(); "><a href="#">密码变更</a></li>
			  <li id="ln_editPic" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id^=edit]').hide();$('#editPic').show(); "><a href="#">个人照片变更</a></li>
			  <li id="ln_editCode" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id^=edit]').hide();$('#editCode').show(); "><a href="#">邀请码信息</a></li>
		      <li id="ln_editRole" onclick="$(this).addClass('active'); $(this).siblings().removeClass('active');$('[id^=edit]').hide();$('#editRole').show(); "><a href="#">角色申请</a></li>
		    </ul>
		</div>
    </div>
  </div>
  <div class="col-sm-9 panel panel-info panel-body">
   <!-- 用户基本信息修改 -->
   <div class="row" id="editBasic" style="display:none;padding:0 5px">
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
        <label  for="sex" class="col-xs-2 control-label" >性别<span style="color:red">*</span></label>
        <div class="col-xs-3">
          <select class="form-control" id="sex" name="sex" required>
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
	      
	    <label for="age" class="col-xs-2 control-label">生日<span style="color:red">*</span></label>
        <div class="col-xs-3">
          <input class="form-control" type="date" id="birthday" name="birthday" required placeholder="请输入年龄" value="${targetUser.birthday }">
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
  <div class="row" style="display:none;padding:0 5px" id="editPwd">
    <h3 style="text-align:center;margin:20px 0 ">密码变更</h3>
	<form class="form-horizontal" id="pwdForm" action="updatePwd" method ="post" autocomplete="on" role="form" >
	  <div class="form-group">
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
           <button type="submit" class="btn btn-info" style="margin:20px" onclick="return checkPwdSame()">&nbsp;&nbsp;提 交&nbsp;&nbsp;</button>
           <button type="button" class="btn btn-warning" style="margin:20px" onclick="$('#pwdForm')[0].reset()">&nbsp;&nbsp;重 置&nbsp;&nbsp; </button>
         </div>
      </div>
	</form>
  </div>
  <!-- 用户头像修改 -->
  <div class="row" style="display:none;padding:0 5px" id="editPic">
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
  <!-- 用户邀请码信息展示 -->
  <div class="row" style="display:none;padding:0 5px" id="editCode">
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
          <input class="form-control" type="text" id="linkInviteCode" value="http://www.jeekhan.me/leyi/register.jsp?inviteCode=${availCode}" readonly>
        </div>
      </div>
	</form>
  </div>
  <!-- 用户角色申请 -->
  <div class="row" style="display:none;padding:0 5px" id="editRole">
    <ul class="list-group"  id="roleList" style="display:none;padding:0 5px">
     <c:forEach items="${allRoles}" var="item">
     <li class="list-group-item role-info" id="roleID_${item.id}" style="cursor:pointer" title="${item.desc}">${item.name}</li>
     </c:forEach>
    </ul>	
    <h3 style="text-align:center;">角色管理</h3>
    <div>
    		1、待审批(包含复核中)、正常、拒绝的角色可再次申请。<br>
    		2、复核不通过的记录不可再次提交申请。<br>
    		3、申请在连续6次(可配置)审批被拒绝后系统不再接受申请。
    </div>
    <table class="table table-striped  table-bordered table-hover ">
      <thead>
         <tr><th width="20%">角色名称</th><th>角色描述</th><th width="10%">角色状态</th><th width="10%">操作 </th></tr>
      </thead>
      <tbody id="userRoles"> 
         <c:forEach items="${operator.userRoles}" var="item">
     	 <tr id="userRoleID_${item.role.id}">
			<td> ${item.role.name} </td>
			<td> ${item.role.desc} </td>
			<td> <script>document.write(getReviewStatus('${item.status}'))</script></td>
			<td> <a style="cursor:pointer">[审批记录]</a></td>
		 </tr>
		 </c:forEach>
       </tbody>
    </table>
    <div class="form-group">
     <div class="col-sm-offset-4 col-sm-10">
      <button type="button" class="btn btn-info" id="btn_SaveUserRole" style="margin:20px">&nbsp;&nbsp;提 交&nbsp;&nbsp;</button>
      <button type="button" class="btn btn-warning" id="btn_ResetUserRole" style="margin:20px" onclick="$('tr[id^=newUserRoleID]').remove()">&nbsp;&nbsp;重 置&nbsp;&nbsp; </button>
     </div>
    </div>	
  </div>
 </div>
 <jk:copyRight></jk:copyRight>	<!--页面底部相关说明 -->
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
    		  success: function(retObj,status,xhr){
    			  if("success" == retObj.result){
    				  $('#txtInviteCode').val(retObj.code);
    				  $('#linkInviteCode').val('http://www.jeekhan.me/${contextPath}/register.jsp?inviteCode=' + retObj.code);
    				  $('#btnCreateCode').attr('disabled',true);
    			  }else{
    				  alert(retObj.error)
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

var selHtml4Rols = "";
//生成添加角色表列
function genAddUserRoleHtml(){
	if(selHtml4Rols){
		return selHtml4Rols;
	}
	var html_add_role = '<tr id="add_user_role">' 
		+ '<td><select class="selectpicker" id="sel4Roles">';
	$("#roleList li").each(function(){
		var id = $(this).attr('id').substring(7);
		var name = $(this).text();
		html_add_role += "<option value='" + id + "'>" + name + "</option>"
	});
	html_add_role += '</select></td>'
	+ '<td></td>'
	+ '<td></td>'
	+ '<td><a onclick="addUserRole()" style="cursor:pointer">[添加]</a></td>' 
	+ '</tr>';
	selHtml4Rols = html_add_role;
	return selHtml4Rols;
}
//生成已有角色表列
function genUserRoleHtml(roleId,roleName,roleDesc,status){
	var html_role = '<tr id="' + ((status =='Z') ?'new':'') + 'UserRoleID_'+ roleId +'">'
	+ '<td>' + roleName +'</td>'
	+ '<td>' + roleDesc +'</td>'
	+ '<td>' + getReviewStatus(status) + '</td>'
	+ '<td class="func-role-opr">'
	+ ((status =='Z') ?'<a style="cursor:pointer" onclick="$(this).parent().parent().remove()">[删除]</a>':'<a style="cursor:pointer">[审批记录]</a>')
	+ '</td>'
	+ '</tr>';
	return html_role;
}
//添加用户角色至页面列表
function addUserRole(){
	var roleId = $('#sel4Roles').val();//当前选择的角色
	if(!roleId){
		alert("请选择要添加的角色！");
		return;
	}
	if($("#newUserRoleID_"+ roleId).length>0){
		alert("已经拥有该角色！");
		return;
	}
	var html_role = genUserRoleHtml(roleId,$('#roleID_' + roleId).text(),$('#roleID_' + roleId).attr('title'),"Z");
	$("#add_user_role").remove();
	$('#userRoles').append(html_role);
	$('#userRoles').append(genAddUserRoleHtml());
	$('#sel4Roles').prop('disabled', false);
	$('#sel4Roles').selectpicker('refresh');
	$('#sel4Roles').selectpicker('val','');
}
//初始化
$('#userRoles').append(genAddUserRoleHtml());
$('#sel4Roles').prop('disabled', false);
$('#sel4Roles').selectpicker('refresh');
$('#sel4Roles').selectpicker('val','');
$('#btn_SaveUserRole').click(function(){
	var rolesId = [];
	$('tr[id^=newUserRoleID_]').each(function(){
		rolesId.push($(this).attr('id').substring(14));
	});
	if(rolesId.length<1){
		alert("请添加申请角色信息！");
		return false;
	}
	$.ajax({
		url:'${contextPath}/${operator.username}/user_mgr/applyRole',
		data: {'rolesId':rolesId.join(',')},
		success: function(retObj,status,xhr){
			$("#add_user_role").remove();
			$('tr[id^=newUserRoleID]').remove();
			if(retObj.succRoles && retObj.succRoles.length>0){
				for(var i=0;i<retObj.succRoles.length;i++){
					var roleId = retObj.succRoles[i];
					$('#userRoleID_' + roleId).remove();
					$('#userRoles').append(genUserRoleHtml(roleId,$('#roleID_' + roleId).text(),$('#roleID_' + roleId).attr('title'),"0"));
				}
			}
			if(retObj.result == "success"){
				alert('角色申请提交成功！');
			}else{
				alert(retObj.error)
			}
			$('#userRoles').append(genAddUserRoleHtml());
			$('#sel4Roles').prop('disabled', false);
			$('#sel4Roles').selectpicker('refresh');
			$('#sel4Roles').selectpicker('val','');
		},
		dataType:'json'
	});
});
</script>

</body>
</html>