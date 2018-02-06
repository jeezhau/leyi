<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="me.jeekhan.leyi.model.*,java.util.*,me.jeekhan.leyi.common.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="jk"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">  
  <title>${operator.username}——主题分类管理</title>
  <meta name="description" content="">
  <meta name="author" content="jeekhan">
  <link rel="shortcut icon" href="${contextPath}/images${contextPath}.ico" type="image/x-icon" />
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
	<!-- 左面主题 -->
    <div class="col-xs-4" >
      <div class="panel panel-info">
   	    <div class="panel-heading" style="margin:0">
          <ol class="breadcrumb" style="margin:0;">
           <li class="active"><a href="${contextPath}/${operator.username}/theme_mgr/">ROOT</a></li>
	      <c:forEach items="${themeTreeUp}" var="item">
	       <c:if test="${currTheme.id==item.id}"> <li class="active">${item.name}</li> </c:if>
	       <c:if test="${currTheme.id!=item.id}"> <li><a href="${contextPath}/${operator.username}/theme_mgr/theme/${item.id}">${item.name}</a></li> </c:if>
	      </c:forEach>
	      <%-- 
	      <c:if test="${not empty childrenThemes and themeTreeUp==null}"><span style="color:red">请选择待操作的主题！</span></c:if>
		  <c:if test="${empty childrenThemes and themeTreeUp==null}"><span style="color:red">您还没有主题，请添加！</span></c:if>
	       --%>
	      </ol>
        </div>
	   	<ul class="list-group">
	     <c:forEach items="${childrenThemes}" var="item">
	       <li class="list-group-item"><a href="${contextPath}/${operator.username}/theme_mgr/theme/${item.id}">${item.name}</a></li>
	     </c:forEach>
	   	</ul>
  	  </div>
    </div><!-- end of 左面主题 -->
    <!-- 右面当前主题信息 -->
    <div class="col-xs-8" >
      <div class="panel panel-info">
        <div class="panel-heading" style="margin:0">
          <button type="button" class="btn btn-default" id = "startNew">新增下级</button>
	      <button type="button" class="btn btn-default" id = "updCurr" >修改当前</button>
          <button type="button" class="btn btn-default" id = "delCurr" >删除当前</button>
        </div>
      <form class="form-horizontal" id="themeForm" action="" method ="post" role="form" enctype="multipart/form-data" role="form">
        <div class="form-group">
           <input type="hidden" id="themeId" name="id" value="${currTheme.id}">
           <input type="hidden" id="parentSeq" name="parentSeq" value="${currTheme.parentSeq}/${currTheme.id}">
        </div>
       	<div class="form-group">
	      <label for="keywords" class="col-sm-2 control-label">主题名称<span style="color:red">*</span></label>
	      <div class="col-sm-10">
	        <input class="form-control" name="name" id="themeName" type="text" value="${currTheme.name }" maxlength=25 required readonly placeholder="请输入主题名称..." >
	        <c:if test="${not empty valid.name}">
	        <div class="alert alert-warning alert-dismissable">${valid.name}
	          <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	        </div>
		    </c:if>
	      </div>
	    </div>     	
      	<div class="form-group">
	      <label for="keywords" class="col-sm-2 control-label">关键词<span style="color:red">*</span></label>
	      <div class="col-sm-10">
	        <textarea class="form-control" id="keywords" name="keywords" maxLength=255 required readonly placeholder="请输入关键词，使用逗号分隔">${currTheme.keywords}</textarea>
	        <c:if test="${not empty valid.keywords}">
	        <div class="alert alert-warning alert-dismissable">${valid.keywords}
	          <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	        </div>
		    </c:if>
	      </div>
	    </div>
      	<div class="form-group">
	      <label for="keywords" class="col-sm-2 control-label">主题描述</label>
	      <div class="col-sm-10">
	        <textarea  class="form-control" name="content" id="themeContent" rows="8" maxlength=600  readonly placeholder="请输入主题描述..." >${currTheme.content}</textarea>
	        <c:if test="${not empty valid.content}">
	        <div class="alert alert-warning alert-dismissable">${valid.content}
	          <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
	        </div>
		    </c:if>
	      </div>
	    </div>
	    <div class="form-group">
         <div class="col-sm-offset-5 col-sm-10">
           <button type="submit" class="btn btn-info" id="save" style="margin:20px">&nbsp;&nbsp;提 交&nbsp;&nbsp;</button>
           <button type="button" class="btn btn-warning" id="reset" style="margin:20px">&nbsp;&nbsp;重 置&nbsp;&nbsp; </button>
           <button type="submit" class="btn btn-danger" id="delete" style="margin:20px">&nbsp;&nbsp;删 除&nbsp;&nbsp;</button>
         </div>
        </div>	         
      </form>
    </div><!-- end of 右面当前主题信息 -->
  </div>
  </div>   
</div>
  
<script>
var themeId = '${currTheme.id}';//当前主题的ID
var themeSeq = '${currTheme.parentSeq}/${currTheme.id}';//当前主题的ID序列
if(themeSeq.indexOf("//")>=0){
	themeSeq = themeSeq.substring(1);
}
var noConfirm = false;//是否不需要进行一级主题创建提示
function startNew(){
	if(!noConfirm && (themeId == null || themeId == "")){
		if(confirm("您没有还有选中主题，确定是要添加一级主题吗？如果不是，请先选择主题！")){
			themeSeq = "/";
		}else{
			return false;
		}
	}
	$("#themeId").val('');
	$("#parentSeq").val(themeSeq);
	$("#themeName").attr("readOnly",false);
	$("#themeName").val('');
	$("#keywords").attr("readOnly",false);
	$("#keywords").val('');
	$("#themeContent").attr("readOnly",false);
	$("#themeContent").val('');
	
	$("#save").show();
	$("#reset").show();
	$("#delete").hide();
}

function updCurr(){
	if( '${fn:length(themeTreeUp)}'<1) { 
		alert('请先选择主题！'); 
		return false;
	}
	$("#themeId").val(themeId);
	$("#parentSeq").val('');
	$("#themeName").attr("readOnly",false);
	$("#themeName").val($('#hiddenCurrName').val());
	$("#keywords").attr("readOnly",false);
	$("#keywords").val($('#hiddenCurrKeywords').val());
	$("#themeContent").attr("readOnly",false);
	$("#themeContent").val($('#hiddenCurrContent').val());	
	$("#save").show();
	$("#reset").show();
	$("#delete").hide();
}
//页面初始化
$("#save").hide();
$("#reset").hide();
$("#delete").hide();
$("#startNew").click(startNew);

$("#updCurr").click(updCurr);
$("#delCurr").click(function(){
	if( '${fn:length(themeTreeUp)}'<1) { 
		alert('请先选择主题！'); 
		return false;
	}
	$("#themeId").val(themeId);
	$("#parentSeq").val('');
	$("#themeName").attr("readOnly",true);
	$("#themeName").val($('#hiddenCurrName').val());
	$("#keywords").attr("readOnly",true);
	$("#keywords").val($('#hiddenCurrKeywords').val());
	$("#themeContent").attr("readOnly",true);
	$("#themeContent").val($('#hiddenCurrContent').val());
	$("#delete").show();
	$("#save").hide();
	$("#reset").hide();
});
$("#delete").click(function(){
	if(confirm("您确定要删除主题【${currTheme.name}】吗？")){
		$("#themeForm").attr('action','${contextPath}/${operator.username}/theme_mgr/delete');
		return true
	}
	return false
});
$("#save").click(function(){
	if($("#themeId").val()){
		$("#themeForm").attr('action','${contextPath}/${operator.username}/theme_mgr/update');
	}else{
		$("#themeForm").attr('action','${contextPath}/${operator.username}/theme_mgr/add');
	}
	return true;
});
$("#reset").click(function(){
	if($("#themeId").val()){
		$("#themeName").attr("readOnly",false);
		$("#themeName").val($("#hiddenCurrName").val());
		$("#keywords").attr("readOnly",false);
		$("#keywords").val($("#hiddenCurrKeywords").val());
		$("#themeContent").attr("readOnly",false);
		$("#themeContent").val($("#hiddenCurrContent").val());
	}else{
		$("#themeName").attr("readOnly",false);
		$("#themeName").val('');
		$("#keywords").attr("readOnly",false);
		$("#keywords").val('');
		$("#themeContent").attr("readOnly",false);
		$("#themeContent").val('');
	}
});
	

</script>
<%--缓存当前主题 --%>
<c:if test="${currTheme.id != null}">
<div style="visibility:hidden">
	<input type ="hidden" id="hiddenCurrName" value="${currTheme.name}">
	<input type ="hidden" id="hiddenCurrKeywords" value="${currTheme.keywords}">
	<input type ="hidden" id="hiddenCurrContent" value="${currTheme.content}">
</div>
</c:if>

<div>
</div>
 <c:if test="${not empty param.error or not empty error}">
 <!-- 错误提示模态框（Modal） -->
 <div class="modal fade " id="tipModal" tabindex="-1" role="dialog" aria-labelledby="tipTitle" aria-hidden="false" data-backdrop="static">
  <div class="modal-dialog">
     <div class="modal-content">
        <div class="modal-header">
           <button type="button" class="close" data-dismiss="modal"  aria-hidden="true">× </button>
           <h4 class="modal-title" id="tipTitle">提示信息</h4>
        </div>
        <div class="modal-body">
          ${param.error} ${error}
        </div>
        <div class="modal-footer">
        	<div style="margin-left:50px">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
           </div>
        </div>
     </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<%--缓存刚提交的数据 --%>
<c:if test="${currTheme.id != null}">
<div style="visibility:hidden">
	<input type ="hidden" id="hiddenErrName" value="${theme.name}">
	<input type ="hidden" id="hiddenErrKeywords" value="${theme.keywords}">
	<input type ="hidden" id="hiddenErrContent" value="${theme.content}">
</div>
</c:if>
<script>
$("#tipModal").modal('show');
if('${mode}' == "add"){
	if(!themeSeq){
		themeSeq = "/";
	}
	noConfirm = true;
	startNew();
	$("#themeId").val('');
	$("#parentSeq").val(themeSeq);
	$("#themeName").val($("#hiddenErrName").val());
	$("#keywords").val($("#hiddenErrKeywords").val());
	$("#themeContent").val($("#hiddenErrContnet").val());
	$("#save").show();
	$("#reset").show();
}
if("${mode}" == "update"){
	updCurr();
	$("#themeId").val(themeId);
	$("#parentSeq").val('');
	$("#themeName").val($("#hiddenErrName").val());
	$("#keywords").val($("#hiddenErrKeywords").val());
	$("#themeContent").val($("#hiddenErrContnet").val());
	$("#save").show();
	$("#reset").show();
}
</script>
</c:if>

<jk:copyRight></jk:copyRight>

</body>
</html>
