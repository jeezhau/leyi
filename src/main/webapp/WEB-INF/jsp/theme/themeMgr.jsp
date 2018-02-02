<%--
主题管理页面
1、功能菜单：新增主题

 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
  <title>主题分类管理</title>
  <meta name="description" content="">
  <meta name="author" content="jeekhan">
  <link rel="shortcut icon" href="/leyi/images/leyi.ico" type="image/x-icon" />
  <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css">  
  <script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
  <script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>

</head>
<body style="background-color: #efefef;">  
<div style="height:38px;background-color:#b3b3ff ">
<%--页面顶部留白 --%>
</div>
<div class="container">
<jk:topMenuBar></jk:topMenuBar> <%--显示用户的顶层功能菜单 --%>
<p class="text-danger h4">系统支持3级分类，1级可有3个分类（分类名称不可超过10个字符），2、3级可有5个分类（分类名称不可超过10个字符）。</p>
<div class="row"><!-- 一级菜单容器 -->
  <c:forEach items="${topThemes}" var="item">
  <%--页面分为三列，每一列一个顶级主题 --%>
  <div class="col-xs-12 col-md-4" style="padding:1px;border-radius:0;">
  <div class="panel panel-default" style="border-radius:0;">
    <div class="panel-heading">
      <div class="row">
	  <div class="col-xs-10">${item.name }</div>
	  <div class="col-xs-2 text-right"><a class=" glyphicon glyphicon-pencil text-right" style="text-decoration:none;" onclick='showClassEditor(1,"${item.name}",${item})'></a></div>
	  </div>
	</div>
    <div class="panel-body">
      <ul class="list-group"><!-- 二级主题 -->
       <c:if test="${!empty item.children}"><!-- 有二级主题 -->
       <c:forEach items="${item.children}" var="item1" varStatus="sta">
	    <li class="list-group-item"><!-- 二级菜单项 -->
	    <div class="row">
	      <span class="col-xs-9">${item1.name }</span>
	      <div class="col-xs-1">
		      <a class="dropdown-toggle" type="button" id="${item1.logicId}" data-toggle="dropdown"><span class="glyphicon glyphicon-list-alt"></span></a>
			  <ul class="dropdown-menu dropdown-menu-right list-group" role="menu" aria-labelledby="${item1.logicId}"><!-- 三级菜单 -->
			    <c:if test="${!empty item1.children}"><!-- 有三级主题 -->
			    <c:forEach items="${item1.children}" var="item2" varStatus="sta">
			      <li class="list-group-item" role="presentation" ><!-- 三级菜单项 -->
			      <div class="row">
			        <span class="col-xs-10">${item2.name}</span>
	                <a class="glyphicon glyphicon-pencil" style="text-decoration:none;" onclick='showClassEditor(3,"${item2.logicId}",${item2})'></a>
	              </div>
			    </li><!-- 三级菜单项 -->
			    </c:forEach>
			    </c:if>
			    <c:if test="${fn:length(item1.children)<6}">
			    <li class="list-group-item" role="presentation" ><!-- 新增三级菜单项 -->
			      <div class="row">
			        <span class="col-xs-10">分类名称</span>
	                <a class="glyphicon glyphicon-pencil" style="text-decoration:none;" onclick="showClassEditor(3,'${item1.logicId}')"></a>
	              </div>
			    </li><!-- 三级菜单项 -->
			    </c:if>
			  </ul><!-- 三级菜单 -->
		  </div>
		  <a class=" col-xs-1 glyphicon glyphicon-pencil" style="text-decoration:none;" onclick='showClassEditor(2,"${item1.logicId}",${item1})'></a>
	     </div>
	    </li>  <!-- 二级菜单项 -->	
	    </c:forEach>
	    </c:if>
	    <c:if test="${!empty item.logicId and fn:length(item.children)<6 }">
	    <li class="list-group-item" role="presentation" ><!-- 新增二级菜单项-->
	      <div class="row">
	        <span class="col-xs-10">分类名称</span>
	        <span class="col-xs-1 "> </span>
            <a class=" col-xs-1 glyphicon glyphicon-pencil" style="text-decoration:none;" onclick="showClassEditor(2,'${item.logicId}')"></a>
          </div>
	    </li><!-- 新增二级菜单项 -->
	    </c:if>   	    
	  </ul> <!-- 二级菜单 -->
   </div>
  </div>
  </div>
  </c:forEach>
</div>
</div><!-- container -->
<!-- 主题分类模态框（Modal） -->
<div class="modal fade " id="themeModal" tabindex="-1" role="dialog" aria-labelledby="themeModalLabel" aria-hidden="true" data-backdrop="static">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"  aria-hidden="true">× </button>
            <h4 class="modal-title text-center" id="themeModalLabel">新增主题分类</h4>
         </div>
         <div class="modal-body">
            <form class="form-horizontal" id="themeForm" method ="post" action="add" role="form" >
               <div class="form-group">
                  <input type="hidden" id="themeId" name="id" value="">
                  <input type="hidden" id="classLvl" name="classLvl" value="">
               	  <input type="hidden" id="logicId" name="logicId" value="">
               </div>
			   <div class="form-group">
			      <label class="col-xs-2 control-label">主题名称</label>
			      <div class="col-xs-10">
			         <input class="form-control" name="name" id="themeName" type="text" value="" maxlength=7 required placeholder="请输入主题名称..." >
			      </div>
			   </div>
			   <div class="form-group">
			      <label for="keywords" class="col-xs-2 control-label">关键词</label>
			      <div class="col-xs-10">
			         <textarea class="form-control" name="keywords" id="keywords" maxlength=255 required placeholder="请输入关键词..." ></textarea>
			      </div>
			   </div>
			   <div class="form-group has-success">
			      <label class="col-xs-2 control-label" for="themeDesc">主题描述</label>
			      <div class="col-xs-10">
			         <textarea  class="form-control" name="descInfo" id="themeDesc" rows="8" maxlength=600 required placeholder="请输入主题描述..." ></textarea>
			      </div>
			   </div>
			</form>
         </div>
         <div class="modal-footer">
            <span class="col-xs-4"></span>
            <button type="submit" class="col-xs-2 btn btn-primary" id="submit">提交</button>
            <button type="button" class="col-xs-2 btn btn-danger" data-dismiss="modal">关闭</button>
            <span class="col-xs-4"></span>
         </div>
      </div><!-- /.modal-content -->
   </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script>
/**
 * param classLvl	分类级别信息
 */
function showClassEditor(classLvl,logicId,data){
	$("#themeForm")[0].reset();
	$("#themeModal").modal('show');	//打卡编辑对话框
	$("#classLvl").val(classLvl);
	$("#logicId").val(logicId);
	if(data){//编辑模式，设置初值
		$('#themeId').val(data.id);
		$("#logicId").val(data.logicId);
		$("#themeName").val(data.name);
		$("#keywords").val(data.keywords);
		$("#themeDesc").val(data.descInfo);
	}
}
$(function(){ 
	$("#submit").click(function(){
		$("#themeForm").submit();
		$("#themeModal").modal('hide');
	});
});
</script>

</body>

</html>