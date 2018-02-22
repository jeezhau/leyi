<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="me.jeekhan.leyi.model.*,java.util.*,me.jeekhan.leyi.common.*"%>
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
  <title>角色管理</title>
  <meta name="description" content="">
  <meta name="author" content="jeekhan">
  <link rel="shortcut icon" href="${contextPath}/images/leyi.ico" type="image/x-icon" />
  <link rel="stylesheet" href="${contextPath}/css/leyi.css">  
  <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css">  
  <script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
  <script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/css/bootstrap-select.min.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/i18n/defaults-zh_CN.min.js"></script>
</head>
<body style="background-color: #efefef;">  
<div style="height:38px;background-color:#b3b3ff;margin-bottom:5px; ">

</div>
<div class="container">
  <jk:topSysMenuBar></jk:topSysMenuBar>
  <div class="btn-toolbar btn-toolbar-info" role="toolbar">
   <ul class="nav nav-pills nav-justified" style="margin-bottom:10px;cursor:pointer">
	   <li class="active" onclick="$(this).addClass('active');$(this).siblings().removeClass('active');$('#div_RoleMgr').show();$('#div_FuncRole').hide()"><a id="btn_RoleMgr">角色管理</a></li>
	   <li onclick="$(this).addClass('active');$(this).siblings().removeClass('active');$('#div_RoleMgr').hide();$('#div_FuncRole').show()"><a id="btn_FuncRole">功能角色管理 </a></li>
	</ul>
  </div>
  <div id = "div_RoleMgr"><!-- 角色管理布局页面 -->
    <div class="col-xs-4" ><!-- 左面角色列表 -->
      <div class="panel panel-info"> 
 	    <div class="panel-heading" >
 	      <p class="text-center">系统当前角色信息</p>
 	    </div>
 	    <div class="panel-body" style="overflow:auto;height:525px">
   	      <ul class="list-group"  id="roleList">
          <c:forEach items="${allRoles}" var="item">
            <li class="list-group-item role-info" id="roleID_${item.id}" style="cursor:pointer" title="${item.desc}">${item.name}</li>
          </c:forEach>
  	      </ul>	  	
        </div>
	  </div>
    </div><!-- end of 左面角色列表 -->
    
    <!-- 右面当前角色信息 -->
    <div class="col-xs-8" >
      <div class="panel panel-info">
        <div class="panel-heading" style="margin:0">
          <button type="button" class="btn btn-default" id = "btn_AddRole">新增角色</button>
	      <button type="button" class="btn btn-default" id = "btn_UpdRole" >修改角色</button>
          <button type="button" class="btn btn-default" id = "btn_DelRole" >注销角色</button>
        </div>
        <div class="panel-body" style="overflow:auto;height:520px">
        <p/><p/>
        <form class="form-horizontal" id="roleForm" action="" method ="post" role="form" enctype="multipart/form-data" role="form">
          <div class="form-group">
	        <label for="keywords" class="col-sm-2 control-label">角色名称<span style="color:red">*</span></label>
	        <div class="col-sm-10">
	          <input class="form-control" name="name" id="roleName" type="text" value="" maxlength=30 required readonly placeholder="请输入角色名称（3-30）个字符..." >
	          <div class="alert alert-warning alert-dismissable" style="display:none">
	            <p id="valid_role_name"> </p>
	            <button type="button" class="valid" onclick="$(this).parent().hide()"> &times;</button>
	          </div>
	        </div>
	      </div>  
      	  <div class="form-group">
	        <label for="keywords" class="col-sm-2 control-label">角色描述 </label>
	        <div class="col-sm-10">
	          <textarea class="form-control" name="desc" id="roleDesc"  rows=15 maxLength=600 readonly placeholder="请输入角色描述（最多600个字符）..."></textarea>
	          <div class="alert alert-warning alert-dismissable" style="display:none">
	            <p id="valid_role_desc"> </p>
	            <button type="button" class="valid" onclick="$(this).parent().hide()"> &times;</button>
	          </div>
	        </div>
	      </div>
	      <div class="form-group">
            <div class="col-sm-offset-4 col-sm-10">
             <button type="button" class="btn btn-info" id="btn_SaveRole" style="margin:20px">&nbsp;&nbsp;提 交&nbsp;&nbsp;</button>
             <button type="button" class="btn btn-warning" id="btn_ResetRole" style="margin:20px">&nbsp;&nbsp;重 置&nbsp;&nbsp; </button>
             <button type="button" class="btn btn-danger" id="btn_DeleteRole" style="margin:20px">&nbsp;&nbsp;注 销&nbsp;&nbsp;</button>
            </div>
          </div>
        </form>
        </div>
      </div><!-- end of 右面当前角色信息 -->
    </div> 
  </div>
  <!-- ===================================功能管理==================================== -->
  <div id="div_FuncRole" style="display:none"> 
    <div class="col-xs-4" > <!-- 左面功能列表 -->
      <div class="panel panel-info">
 	    <div class="panel-heading">
 	      <p class="text-center">系统当前功能信息</p>
 	    </div>
 	    <div class="panel-body" style="overflow:auto;height:525px">
   	      <ul class="list-group" id="funcList">
          <c:forEach items="${allFuncs}" var="item">
            <li class="list-group-item func-info" style="cursor:pointer" id="funcID_${item.id}">${item.name}</li>
          </c:forEach>
  	      </ul>	
        </div>
	  </div>
    </div><!-- end of 左面列表 -->
    <div class="col-xs-8" ><!-- 右面当前功能信息 -->
      <div class="panel panel-info">
        <div class="panel-heading">
          <button type="button" class="btn btn-default" id = "btn_AddFunc">新增功能</button>
	      <button type="button" class="btn btn-default" id = "btn_UpdFunc" >修改功能</button>
          <button type="button" class="btn btn-default" id = "btn_DelFunc" >注销功能</button>
        </div>
        <div class="panel-body" style="overflow:auto;height:520px">
        	  <p/><p/>
          <form class="form-horizontal" id="roleForm" action="" method ="post" role="form" enctype="multipart/form-data" role="form">
          <div class="form-group">
	        <label for="keywords" class="col-sm-2 control-label">功能名称<span style="color:red ">*</span></label>
	        <div class="col-sm-10">
	          <input class="form-control" id="funcName" type="text" value="" maxlength=30 required readonly placeholder="请输入功能名称（3-30）个字符..." >
	          <div class="alert alert-warning alert-dismissable" style="display:none">
	            <p id="valid_func_name"> </p>
	           <button type="button" class="valid" onclick="$(this).parent().hide()"> &times;</button>
	          </div>
	        </div>
	      </div>
          <div class="form-group">
	        <label for="keywords" class="col-sm-2 control-label">功能URL<span style="color:red ">*</span></label>
	        <div class="col-sm-10">
	          <input class="form-control" id="funcUrl" type="text" value="" maxlength=100 required readonly placeholder="请输入功能URL（3-100）个字符..." >
	          <div class="alert alert-warning alert-dismissable" style="display:none">
	            <p id="valid_func_url"> </p>
	            <button type="button" class="valid" onclick="$(this).parent().hide()"> &times;</button>
	          </div>	          
	        </div>
	      </div>	         	
      	  <div class="form-group">
	        <label for="keywords" class="col-sm-2 control-label">功能描述</label>
	        <div class="col-sm-10">
	          <textarea class="form-control" id="funcDesc" maxLength=600 readonly placeholder="请输入功能描述（最多600个字符）..."></textarea>
	          <div class="alert alert-warning alert-dismissable" style="display:none">
	            <p id="valid_desc_name"> </p>
	            <button type="button" class="valid" onclick="$(this).parent().hide()"> &times;</button>
	          </div>	        
	        </div>
	      </div>
	      <div class="form-group">
	        <label for="keywords" class="col-sm-2 control-label">需要角色</label>
	        <div class="col-sm-10">
	          <table class="table table-striped  table-bordered table-hover ">
                <thead>
   	              <tr><th >角色名称</th><th width="10%">操作 </th></tr>
                </thead>
                <tbody id="funcRoles"> 
            
                </tbody>
              </table>
	        </div>
	      </div>
	      <div class="form-group">
            <div class="col-sm-offset-4 col-sm-10">
             <button type="button" class="btn btn-info" id="btn_SaveFunc" style="margin:20px">&nbsp;&nbsp;提 交&nbsp;&nbsp;</button>
             <button type="button" class="btn btn-warning" id="btn_ResetFunc" style="margin:20px">&nbsp;&nbsp;重 置&nbsp;&nbsp; </button>
             <button type="button" class="btn btn-danger" id="btn_DeleteFunc" style="margin:20px">&nbsp;&nbsp;注 销&nbsp;&nbsp;</button>
            </div>
          </div>	
        </form>
        </div>
      </div><!-- end of 右面当前功能信息 -->
    </div>       
  </div> 
</div><!-- end of container -->

<script>
//===================================================
//	角色管理
//===================================================
var currRole = null;	//当前选择的角色
//点击角色链接
function clickRoleLink(){
	currRole = {};
	currRole.id = $(this).attr('id').substring(7);
	currRole.name = $(this).text();
	currRole.desc = $(this).attr('title');
	$("#roleName").attr("readOnly",true);
	$("#roleDesc").attr("readOnly",true);
	$("#roleName").val(currRole.name);
	$("#roleDesc").val(currRole.desc);
	$(this).siblings().removeClass("active");
	$(this).addClass("active");
	
	$("#btn_SaveRole").hide();
	$("#btn_ResetRole").hide();
	$("#btn_DeleteRole").hide();
	$("#btn_AddRole").removeClass('active');
	$("#btn_AddRole").siblings().removeClass('active');
}
//点击新增角色按钮
function addRole(){
	currRole = null;
	$("#roleName").attr("readOnly",false);
	$("#roleName").val('');
	$("#roleDesc").attr("readOnly",false);
	$("#roleDesc").val('');
	$(".role-info").removeClass("active");
	$("#btn_SaveRole").show();
	$("#btn_ResetRole").show();
	$("#btn_DeleteRole").hide();
	$(this).siblings().removeClass('active');
	$(this).addClass('active');
}
//点击修改角色按钮
function updRole(){
	$(this).siblings().removeClass('active');
	if(!currRole){
		alert('请先选择角色！'); 
		return false;
	}
	$("#roleName").attr("readOnly",false);
	$("#roleDesc").attr("readOnly",false);
	$("#btn_SaveRole").show();
	$("#btn_ResetRole").show();
	$("#btn_DeleteRole").hide();
	$(this).addClass('active');
}
//点击注销角色按钮
function delRole(){
	$(this).siblings().removeClass('active');
	if(!currRole){
		alert('请先选择角色！'); 
		return false;
	}
	$("#roleName").attr("readOnly",true);
	$("#roleDesc").attr("readOnly",true);
	$("#btn_SaveRole").hide();
	$("#btn_ResetRole").hide();
	$("#btn_DeleteRole").show();
	$(this).addClass('active');
}

//页面初始化
$("#btn_SaveRole").hide();
$("#btn_ResetRole").hide();
$("#btn_DeleteRole").hide();
$(".role-info").click(clickRoleLink);
$("#btn_AddRole").click(addRole);
$("#btn_UpdRole").click(updRole);
$("#btn_DelRole").click(delRole);

//功能按钮处理
$("#btn_DeleteRole").click(function(){
	if(confirm("您确定要注销角色【"+ currRole.name +"】吗？")){
		$.ajax({
			url: '${contextPath}/${operator.username}/role_mgr/deleteRole',
			data: {'roleId':currRole.id},
			success: function(retObj,status,xhr){
				if(retObj){
					if("success" == retObj.result){
						//更新角色信息
						$("#roleID_" + currRole.id).remove();
						$("#roleDesc").val('');
						$("#roleName").val('');
						$("#btn_DeleteRole").hide();
						$("#btn_AddRole").removeClass('active');
						$("#btn_AddRole").siblings().removeClass('active');
						alert("角色【" + currRole.name + "】已成功注销！！");
						currRole = null;
					}else{//出现逻辑错误
						alert(retObj.error);
					}
				}else{
					alert('注销角色失败！')
				}
			},
			dataType: 'json'
		});
	}
	return false;
});
$("#btn_SaveRole").click(function(){
	var valid = true;
	var name = $('#roleName').val();
	if(!name){
		$('#valid_role_name').text('角色名称：不可为空');
		$('#valid_role_name').parent().show();
		valid = false;
	}else{
		name = name.trim();
		if(name.length<3 || name.length>30){
			$('#valid_role_name').text('角色名称：长度为3-30字符');
			$('#valid_role_name').parent().show();
			valid = false;
		}
	}
	var desc = $("#roleDesc").val();
	if(desc && desc.length>600){
		desc = desc.trim();
		$('#valid_role_desc').text("角色描述：最长600字符");
		$('#valid_role_desc').parent().show()
		valid = false;
	}
	if(!valid){
		return false;
	}
	$.ajax({
		url: '${contextPath}/${operator.username}/role_mgr/saveRole',
		data: {'id':currRole?currRole.id:0,
				"name":name,
				"desc":desc
		},
		success: function(retObj,status,xhr){
			if(retObj){
				if("success" == retObj.result){
					//更新角色信息
					$("#roleID_" + retObj.roleId ).remove();//删除旧的角色信息
					var html = '<li class="list-group-item role-info" id="roleID_'+ retObj.roleId +'" style="cursor:pointer" title="'+ desc +'">'
    						 + name + '</li>'
					$('#roleList').html(html + $('#roleList').html());
					$(".role-info").click(clickRoleLink);
					$("#btn_AddRole").removeClass('active');
					$("#btn_AddRole").siblings().removeClass('active');
					alert("角色【"+ $('#roleName').val() +"】保存成功！！");
					$("#roleID_" + retObj.roleId).click();
				}else{
					if(retObj.error){//出现逻辑错误
						alert(retObj.error)
					}else if(retObj.valid){//字段验证出错
						var valid = retObj.valid
						for(attr in valid){
							if(attr =='name'){
								$('#valid_role_name').text(valid[attr]);
								$('#valid_role_name').parent().show();
							}else if(attr =='desc'){
								$('#valid_role_desc').text(valid[attr]);
								$('#valid_role_desc').parent().show()
							}
						}
					}
				}
			}else{
				alert('保存角色信息失败！')
			}
		},
		dataType: 'json'
	});
});
$("#btn_ResetRole").click(function(){
	if(currRole){	//修改
		$("#roleName").attr("readOnly",false);
		$("#roleDesc").attr("readOnly",false);
		$("#roleName").val(currRole.name);
		$("#roleDesc").val(currRole.desc);
	}else{
		$("#roleName").attr("readOnly",false);
		$("#roleName").val('');
		$("#roleDesc").attr("readOnly",false);
		$("#roleDesc").val('');
	}
});
</script>

<script>
//===================================================
//功能管理
//===================================================
var currFunc = null;
var selHtml4Rols = "";
//生成添加角色表列
function genAddFunRoleHtml(){
	if(selHtml4Rols){
		return selHtml4Rols;
	}
	var html_add_role = '<tr id="add_func_role">' 
		+ '<td><select class="selectpicker" id="sel4Roles">';
	$("#roleList li").each(function(){
		var id = $(this).attr('id').substring(7);
		var name = $(this).text();
		html_add_role += "<option value='" + id + "'>" + name + "</option>"
	});
	html_add_role += '</select></td>'
	+ '<td><a onclick="addFuncRole()" style="cursor:pointer">[添加]</a></td>' 
	+ '</tr>';
	selHtml4Rols = html_add_role;
	return selHtml4Rols;
}
//生成已有角色表列
function genFuncRoleHtml(roleId,roleName,roleDesc){
	var html_role = '<tr id="funcRoleID_'+ roleId +'">'
	+ '<td title="'+ roleDesc +'">'+ roleName +'</td>'
	+ '<td class="func-role-opr"><a style="display:none;cursor:pointer">[删除]</a></td>'
	+ '</tr>';
	return html_role;
}
//点击功能链接
function clickFuncLink(){
	currFunc = {};
	currFunc.name = $(this).text();
	currFunc.id = $(this).attr('id').substring(7);
	currFunc.initRolesId = []; //功能原有的角色
	$.ajax({
		url: '${contextPath}/${operator.username}/role_mgr/getRoles4Func',
		data: {'funcId':currFunc.id},
		success: function(func,status,xhr){
			if(func){
				$("#funcName").val(func.name);
				$("#funcUrl").val(func.url);
				$("#funcDesc").val(func.desc);
				if(func.roles){
					var html_roles = '';
					for(var i=0;i<func.roles.length;i++){
						html_roles += genFuncRoleHtml(func.roles[i].id,func.roles[i].name,func.roles[i].desc);
						currFunc.initRolesId.push(func.roles[i].id);
					}
					$('#funcRoles').html(html_roles);
				}
			}else{
				alert('获取功能的角色信息失败！')
			}
		},
		dataType: 'json'
	});
	
	$(this).siblings().removeClass("active");
	$(this).addClass("active");
	$("#funcName").attr("readOnly",true);
	$("#funcUrl").attr("readOnly",true);
	$("#funcDesc").attr("readOnly",true);
	$("#btn_SaveFunc").hide();
	$("#btn_ResetFunc").hide();
	$("#btn_DeleteFunc").hide();
	$("#btn_AddFunc").removeClass('active');
	$("#btn_AddFunc").siblings().removeClass('active');
}

//点击新增功能按钮
function addFunc(){
	$(this).siblings().removeClass('active');
	$(".func-info").removeClass("active");
	currFunc = null;
	$("#funcName").attr("readOnly",false);
	$("#funcUrl").attr("readOnly",false);
	$("#funcDesc").attr("readOnly",false);
	$("#funcName").val('');
	$("#funcUrl").val('');
	$("#funcDesc").val('');
	$('#funcRoles').html(genAddFunRoleHtml());
	$('#sel4Roles').prop('disabled', false);
	$('#sel4Roles').selectpicker('refresh');
	$('#sel4Roles').selectpicker('val','');
	$("#btn_SaveFunc").show();
	$("#btn_ResetFunc").show();
	$("#btn_DeleteFunc").hide();
	$(this).addClass('active');
}
//点击修改角色按钮
function updFunc(){
	$(this).siblings().removeClass('active');
	if(!currFunc){
		alert('请先选择功能！'); 
		return false;
	}
	$("#funcName").attr("readOnly",false);
	$("#funcUrl").attr("readOnly",false);
	$("#funcDesc").attr("readOnly",false);
	if($("#add_func_role").length<1){
		resetFuncRole();
		$('#funcRoles').append(genAddFunRoleHtml());
		$('#sel4Roles').prop('disabled', false);
		$('#sel4Roles').selectpicker('refresh');
		$('#sel4Roles').selectpicker('val','');
	}
	$(".func-role-opr a").show();
	$(".func-role-opr a").click(delFuncRole);
	$("#btn_SaveFunc").show();
	$("#btn_ResetFunc").show();
	$("#btn_DeleteFunc").hide();
	$(this).addClass('active');
}
//点击注销角色按钮
function delFunc(){
	$(this).siblings().removeClass('active');
	if(!currFunc){
		alert('请先选择功能！'); 
		return false;
	}
	$("#funcName").attr("readOnly",true);
	$("#funcUrl").attr("readOnly",true);
	$("#funcDesc").attr("readOnly",true);
	resetFuncRole();
	if($("#add_func_role").length>0){
		$("#add_func_role").remove();
	}
	$(".func-role-opr a").hide();
	$("#btn_SaveFunc").hide();
	$("#btn_ResetFunc").hide();
	$("#btn_DeleteFunc").show();
	$(this).addClass('active');
}
//添加功能角色至页面列表
function addFuncRole(){
	var roleId = $('#sel4Roles').val();//当前选择的角色
	if(!roleId){
		alert("请选择要添加的角色！");
		return;
	}
	if($("#funcRoleID_"+ roleId).length>0){
		alert("已经拥有该角色！");
		return;
	}
	var html_role = genFuncRoleHtml(roleId,$('#roleID_' + roleId).text(),$('#roleID_' + roleId).attr('title'));
	$("#add_func_role").remove();
	$('#funcRoles').html($('#funcRoles').html() + html_role + genAddFunRoleHtml());
	$(".func-role-opr a").show();
	$(".func-role-opr a").click(delFuncRole);
	$('#sel4Roles').prop('disabled', false);
	$('#sel4Roles').selectpicker('refresh');
	$('#sel4Roles').selectpicker('val','');
}
//从页面删除功能角色
function delFuncRole(){
	$(this).parent().parent().remove();
}
//重置功能角色
function resetFuncRole(){
	var html_func_roles = "";
	for(var i=0;i<currFunc.initRolesId.length;i++){
		var roleId = currFunc.initRolesId[i];
		html_func_roles += genFuncRoleHtml(roleId,$('#roleID_' + roleId).text(),$('#roleID_' + roleId).attr('title'));
	}
	$('#funcRoles').html(html_func_roles);
}
//页面初始化
$("#btn_SaveFunc").hide();
$("#btn_ResetFunc").hide();
$("#btn_DeleteFunc").hide();
$(".func-info").click(clickFuncLink);
$("#btn_AddFunc").click(addFunc);
$("#btn_UpdFunc").click(updFunc);
$("#btn_DelFunc").click(delFunc);
$("#btn_ResetFunc").click(resetFuncRole);
$("#btn_SaveFunc").click(function(){
	var valid = true;
	var name = $('#funcName').val();
	if(!name){
		$('#valid_func_name').text('功能名称：不可为空');
		$('#valid_func_name').parent().show();
		valid = false;
	}else{
		name = name.trim();
		if(name.length<3 || name.length>30){
			$('#valid_func_name').text('功能名称：长度为3-30字符');
			$('#valid_func_name').parent().show();
			valid = false;
		}
	}
	var url = $('#funcUrl').val();
	if(!url){
		$('#valid_func_url').text("功能URL：不可为空");
		$('#valid_func_url').parent().show();
		valid = false;
	}else{
		url = url.trim();
		if(url.length<3 || url.length>100){
			$('#valid_func_url').text("功能URL：长度为3-100字符");
			$('#valid_func_url').parent().show();
			valid = false;
		}
	}
	var desc = $("#funcDesc").val();
	if(desc && desc.length>600){
		desc = desc.trim();
		$('#valid_func_desc').text("功能描述：最长600字符");
		$('#valid_func_desc').parent().show()
		valid = false;
	}
	if(!valid){
		return false;
	}
	var rolesId = [];
	$('tr[id^=funcRoleID]').each(function(){
		rolesId.push($(this).attr('id').substring(11));
	});
	$.ajax({
		url:'${contextPath}/${operator.username}/role_mgr/saveFunc',
		data: {'id':currFunc?currFunc.id:0,
				"name":name,
				"url":url,
				"desc":desc,
				'rolesId':rolesId.join(',')
		},
		success: function(retObj,status,xhr){
			if(retObj){
				if("success" == retObj.result){
					//更新功能信息
					$("#funcID_" + retObj.funcId ).remove();//删除旧的功能信息
					var new_func_html = '<li class="list-group-item func-info" id="funcID_'+ retObj.funcId +'" style="cursor:pointer" title="'+ desc +'">' + name + '</li>'
					$('#funcList').html(new_func_html + $('#funcList').html());  					
					$(".func-info").click(clickFuncLink);
					$("#btn_AddFunc").removeClass('active');
					$("#btn_AddFunc").siblings().removeClass('active');
					alert("功能【"+ $('#funcName').val() +"】保存成功！！");
					$("#funcID_" + retObj.funcId).click();
				}else{
					if(retObj.error){//出现逻辑错误
						alert(retObj.error)
					}else if(retObj.valid){//字段验证出错
						var valid = retObj.valid
						for(attr in valid){
							if(attr =='name'){
								$('#valid_func_name').text(valid[attr]);
								$('#valid_func_name').parent().show();
							}else if(attr =='url'){
								$('#valid_func_url').text(valid[attr]);
								$('#valid_func_url').parent().show();
							}else if(attr =='desc'){
								$('#valid_func_desc').text(valid[attr]);
								$('#valid_func_desc').parent().show()
							}
						}
					}
				}
			}else{
				alert('保存功能信息失败！')
			}
		},
		dataType:'json'
	});
});
$("#btn_DeleteFunc").click(function(){
	if(confirm("您确定要注销功能【"+ currFunc.name +"】吗？")){
		$.ajax({
			url: '${contextPath}/${operator.username}/role_mgr/deleteFunc',
			data: {'funcId':currFunc.id},
			success: function(retObj,status,xhr){
				if(retObj){
					if("success" == retObj.result){
						//更新信息
						$("#funcID_" + currFunc.id).remove();
						$("#funcDesc").val('');
						$("#funcName").val('');
						$("#funcUrl").val('');
						$('#funcRoles').html('');
						$("#btn_DeleteFunc").hide();
						$("#btn_AddFunc").removeClass('active');
						$("#btn_AddFunc").siblings().removeClass('active');
						alert("功能【" + currFunc.name + "】已成功注销！！");
						currFunc = null;
					}else{//出现逻辑错误
						alert(retObj.error);
					}
				}else{
					alert('注销角色失败！')
				}
			},
			dataType: 'json'
		});
	}
	return false;	
});
</script>
</body>
</html>

