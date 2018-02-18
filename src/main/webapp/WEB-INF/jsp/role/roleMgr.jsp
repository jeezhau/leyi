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
   <ul class="nav nav-pills nav-justified" style="margin-bottom:10px;">
	   <li class="active" onclick="$(this).addClass('active');$(this).siblings().removeClass('active');$('#div_RoleMgr').show();$('#div_FuncRole').hide()"><a id="btn_RoleMgr">角色创建与注销</a></li>
	   <li onclick="$(this).addClass('active');$(this).siblings().removeClass('active');$('#div_RoleMgr').hide();$('#div_FuncRole').show()"><a id="btn_FuncRole">功能角色赋予 </a></li>
	</ul>
  </div>
  <div id = "div_RoleMgr">
    <div class="col-xs-4" ><!-- 左面角色列表 -->
      <div class="panel panel-info"> 
 	    <div class="panel-heading" >
 	      <p class="text-center">系统当前角色信息</p>
 	    </div>
 	    <div class="panel-body" style="overflow:auto;height:525px">
   	      <ul class="list-group" id="roleList">
          <c:forEach items="${allRoles}" var="item">
            <li class="list-group-item role-info" id="roleID_${item.id}" style="cursor:pointer">
            <p class= "link_role">${item.name}</p>
            <p style="display:none">${item.desc}</p>
            </li>
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
	            <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	          </div>
	        </div>
	      </div>  
      	  <div class="form-group">
	        <label for="keywords" class="col-sm-2 control-label">角色描述 </label>
	        <div class="col-sm-10">
	          <textarea class="form-control" name="desc" id="roleDesc"  rows=15 maxLength=600 readonly placeholder="请输入角色描述，使用逗号分隔（最多600个字符）..."></textarea>
	          <div class="alert alert-warning alert-dismissable" style="display:none">
	            <p id="valid_role_desc"> </p>
	            <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
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
   	      <ul class="list-group">
          <c:forEach items="${allFuncs}" var="item">
            <li class="list-group-item link_func" style="cursor:pointer" id="funcID_${item.id}">${item.name}</li>
          </c:forEach>
  	      </ul>	
        </div>
	  </div>
    </div><!-- end of 左面角色列表 -->
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
	        <label for="keywords" class="col-sm-2 control-label">功能名称</label>
	        <div class="col-sm-10"><input class="form-control" id="funcName" type="text" value="" maxlength=25 ></div>
	      </div>
          <div class="form-group">
	        <label for="keywords" class="col-sm-2 control-label">功能URL</label>
	        <div class="col-sm-10"><input class="form-control" id="funcUrl" type="text" value="" maxlength=25 ></div>
	      </div>	         	
      	  <div class="form-group">
	        <label for="keywords" class="col-sm-2 control-label">功能描述</label>
	        <div class="col-sm-10"><textarea class="form-control" id="funcDesc" maxLength=600></textarea></div>
	      </div>
	      <table class="table table-striped  table-bordered table-hover ">
            <thead>
   	          <tr><th width="25%">角色名称</th><th >角色描述</th><th width="10%">操作 </th></tr>
            </thead>
            <tbody id="funcRoles"> 
            
            </tbody>
          </table>
	      
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
	currRole.id = $(this).parent().attr('id').substring(7);
	currRole.name = $(this).text();
	currRole.desc = $(this).siblings().first().text();
	$("#roleName").attr("readOnly",true);
	$("#roleDesc").attr("readOnly",true);
	$("#roleName").val(currRole.name);
	$("#roleDesc").val(currRole.desc);
	$(this).parent().siblings().removeClass("active");
	$(this).parent().addClass("active");
	
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
	if(!currRole){
		alert('请先选择角色！'); 
		return false;
	}
	$("#roleName").attr("readOnly",false);
	$("#roleDesc").attr("readOnly",false);
	$("#btn_SaveRole").show();
	$("#btn_ResetRole").show();
	$("#btn_DeleteRole").hide();
	$(this).siblings().removeClass('active');
	$(this).addClass('active');
}
//点击注销角色按钮
function delRole(){
	if(!currRole){
		alert('请先选择角色！'); 
		return false;
	}
	$("#roleName").attr("readOnly",true);
	$("#roleDesc").attr("readOnly",true);
	$("#btn_SaveRole").hide();
	$("#btn_ResetRole").hide();
	$("#btn_DeleteRole").show();
	$(this).siblings().removeClass('active');
	$(this).addClass('active');
}

//页面初始化
$("#btn_SaveRole").hide();
$("#btn_ResetRole").hide();
$("#btn_DeleteRole").hide();
$(".link_role").click(clickRoleLink);
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
	$.ajax({
		url: '${contextPath}/${operator.username}/role_mgr/saveRole',
		data: {'id':currRole?currRole.id:0,
				"name":$('#roleName').val(),
				"desc":$("#roleDesc").val()
		},
		success: function(retObj,status,xhr){
			if(retObj){
				if("success" == retObj.result){
					//更新角色信息
					$("#roleID_" + retObj.roleId ).remove();//删除旧的角色信息
					var html = '<li class="list-group-item role-info" id="roleID_'+ retObj.roleId +'" style="cursor:pointer">'
    						+ '<p class= "link_role">' + $('#roleName').val() + '</p>'
    						+ '<p style="display:none">'+ $("#roleDesc").val() +'</p>'
    						+ '</li>'
					$('#roleList').html(html + $('#roleList').html());
					$(".link_role").click(clickRoleLink);
					$("#btn_AddRole").removeClass('active');
					$("#btn_AddRole").siblings().removeClass('active');
					alert("角色【"+ $('#roleName').val() +"】保存成功！！");
					$("#roleID_" + retObj.roleId + " p.link_role").click();
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

<script type="text/javascript">
//===================================================
//功能管理
//===================================================
var currFunc = null;
//点击功能链接
$('.link_func').click(function(){
	currFunc = {};
	currFunc.name = $(this).text();
	currFunc.id = $(this).attr('id').substring(7);
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
						html_roles += '<tr><td>'+ func.roles[i].name +'</td><td> '+ func.roles[i].desc +'</td><td> '
						+ '[<a href="${contextPath}/${operator.username}/role_mgr/delete" onclick="">删除</a>]'
						+'</td></tr>';
					}
					var html_add_role = '<tr><td>' 
					+ '<select class="selectpicker" id="sel4Roles"><option value="1">广东省</option></select>'
					+ '</td><td></td><td> ' 
					+ '[<a href="${contextPath}/${operator.username}/role_mgr/delete" onclick="">添加</a>]' 
					+'</td></tr>';
					$('#funcRoles').html(html_roles + html_add_role);
					$('#sel4Roles').prop('disabled', false);
					$('#sel4Roles').selectpicker('refresh');
				}
			}else{
				alert('获取功能的角色信息失败！')
			}
		},
		dataType: 'json'
	});
	
	$(this).siblings().removeClass("active");
	$(this).addClass("active");
});

</script>
</body>
</html>

