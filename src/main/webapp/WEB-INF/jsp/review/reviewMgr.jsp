<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="me.jeekhan.leyi.model.ThemeClass,java.util.*,me.jeekhan.leyi.common.*"%>
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
  <title>信息审核</title>
  <meta name="description" content="">
  <meta name="author" content="jeekhan">
  <link rel="shortcut icon" href="/leyi/images/leyi.ico" type="image/x-icon" />
  <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css">  
  <script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
  <script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>

</head>
<body style="background-color: #efefef;">  
<div style="height:38px;background-color:#b3b3ff;margin-bottom:5px; ">

</div>
<div class="container">
  <jk:loginMenuBar></jk:loginMenuBar>
  <div class="row">
  <c:if test="${fn:length(users)<=0 }">
   <div class="col-md-12" >
  	<div class="alert alert-info alert-dismissable">无需要审核的用户！！！
        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
     </div>
   </div>
  </c:if>
  <c:if test="${fn:length(users)>0 }">
    <!-- 用户列表 -->
    <div class="col-md-12" >
      <div class="panel panel-info" >
      <h3 class="text-center"><b>用户审核</b></h3>
        <table class="table table-striped table-bordered table-hover ">
          <thead>
   	        <tr><th width="20%">用户名</th><th width="20%">邮箱 </th><th width="8%">性别</th><th>职业 </th><th width="13%">更新时间 </th><th width="8%">操作 </th></tr>
          </thead>
          <tbody> 
           <c:forEach items="${users}" var="item">
            <tr>
              <td>${item.username}</td>
              <td>${item.email }</td>
              <td>
              <c:choose>
			     	<c:when test="${item.sex == 0}">男</c:when>
			     	<c:when test="${item.sex == 1}">女</c:when>
			     	<c:when test="${item.sex == 2}">其他</c:when>
			     </c:choose>   
              </td>
              <td>${item.profession }</td>
              <td><fmt:formatDate value="${item.updateTime }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
              <td><a href="/leyi/${operator.username }/user_mgr/review/${item.id}" >审核</a></td>
            </tr>
           </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    </c:if>
  </div><!-- 用户审核 结束-->
  <div class="row">
  <c:if test="${fn:length(articles)<=0 }">
   <div class="col-md-12" >
  	<div class="alert alert-info alert-dismissable">无需要审核的文章！！！
        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
     </div>
   </div>
  </c:if>
  <c:if test="${fn:length(articles)>0 }">
    <!-- 文章列表 -->
    <div class="col-md-12" >
      <div class="panel panel-info" >
      <h3 class="text-center"><b>文章审核</b></h3>
        <table class="table table-striped table-bordered table-hover ">
          <thead>
   	        <tr><th width="25%">文章标题</th><th>关键词 </th><th width="8%">来源</th><th width="8%">类型 </th><th width="13%">更新时间 </th><th width="8%">操作 </th></tr>
          </thead>
          <tbody> 
           <c:forEach items="${articles}" var="item">
            <tr>
              <td>${item.name}</td>
              <td>${item.keywords }</td>
              <td>
              <c:choose>
			     	<c:when test="${item.source == 0}">自创</c:when>
			     	<c:when test="${item.source == 1}">转摘</c:when>
			     	<c:when test="${item.source == 2}">其他</c:when>
			     </c:choose>   
              </td>
              <td>
              <c:choose>
			     	<c:when test="${item.type == 0}">文本</c:when>
			     	<c:when test="${item.type == 1}">图册</c:when>
			     	<c:when test="${item.type == 2}">视频</c:when>
			     	<c:when test="${item.type == 3}">语音</c:when>
			     	<c:when test="${item.type == 4}">混合</c:when>
			     </c:choose>
              </td>
              <td><fmt:formatDate value="${item.updateTime }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
              <td><a href="/leyi/${operator.username }/article_mgr/review/${item.id}" >审核</a></td>
            </tr>
           </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    </c:if>
  </div><!-- 文章审核 结束-->
  
  <div class="row"><!-- 主题审核列表 -->
  <c:if test="${fn:length(themes)<=0 }">
   <div class="col-md-12" >
  	<div class="alert alert-info alert-dismissable">无需要审核的主题！！！
        <button type="button" class="close" data-dismiss="alert"  aria-hidden="true"> &times;</button>
     </div>
   </div>
  </c:if>
  <c:if test="${fn:length(themes)>0 }">
    <div class="col-md-12" >
      <div class="panel panel-info">
        <h3 class="text-center"><b>主题审核</b></h3>
        <table class="table table-striped table-bordered table-hover ">
          <thead>
   	        <tr><th width="25%">主题名称</th><th>关键词 </th><th width="13%">更新时间 </th><th width="8%">操作 </th></tr>
          </thead>
          <tbody> 
           <c:forEach items="${themes}" var="item">
            <tr id="theme_${item.id}">
              <td id="name_${item.id}">${item.name}</td>
              <td id="keywords_${item.id}">${item.keywords }</td>
              <td><fmt:formatDate value="${item.updateTime }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
              <td id="descInfo_${item.id}" style="display:none">${item.descInfo }</td>
              <td><a href="#" onclick=" $('#themeId').val('${item.id}');$('#themeName').val($('#name_${item.id}').text()); $('#keywords').text($('#keywords_${item.id}').text());$('#themeDesc').text($('#descInfo_${item.id}').text());$('#themeModal').modal('show'); ">审核</a></td>
            </tr>
           </c:forEach>
          </tbody>
        </table>
      </div>
    </div><!-- end of 文章列表 -->
    </c:if>
  </div><!-- 主题审核列表结束 -->  
  <jk:copyRight></jk:copyRight>
</div>

<!-- 主题分类模态框（Modal） -->
<div class="modal fade " id="themeModal" tabindex="-1" role="dialog" aria-labelledby="themeModalLabel" aria-hidden="true" data-backdrop="static">
  <div class="modal-dialog">
     <div class="modal-content">
        <div class="modal-header">
           <button type="button" class="close" data-dismiss="modal"  aria-hidden="true">× </button>
           <h4 class="modal-title" id="themeModalLabel">主题审核</h4>
        </div>
        <div class="modal-body">
          <form class="form-horizontal">
		   <div class="form-group">
		      <label class="col-sm-2 control-label">主题名称</label>
		      <div class="col-sm-10">
		        <input class="form-control" id="themeName" type="text" value="" readonly>
		      </div>
		   </div>
		   <div class="form-group">
		      <label for="keywords" class="col-sm-2 control-label">关键词</label>
		      <div class="col-sm-10">
		         <textarea class="form-control" id="keywords" readonly ></textarea>
		      </div>
		   </div>
		   <div class="form-group ">
		      <label class="col-sm-2 control-label" for="themeDesc">主题描述</label>
		      <div class="col-sm-10">
		         <textarea  class="form-control" id="themeDesc" rows="8" readonly></textarea>
		      </div>
		   </div>
		</form>
        </div>
        <div class="modal-footer">
          <form id="reviewForm" method="post" action="">
	     <input type="hidden" id="themeId" name="themeId" value="">
	     <div class="form-group">
	        <label  class="col-sm-2 control-label">审核说明</label>
	        <div class="col-sm-10">
	          <textarea class="form-control" id="remark" name="remark" placeholder="请输入审核说明" rows="5" maxLength=600></textarea>
	        </div>
	      </div>
         <div class=" col-sm-10">
           <button type="button" class="btn btn-info" id="accept" style="margin:10px">&nbsp;&nbsp;通过&nbsp;&nbsp;</button>
           <button type="button" class="btn btn-warning" id="refuse" style="margin:10px">&nbsp;&nbsp;拒绝&nbsp;&nbsp; </button>
         </div>
	    </form>
        </div>
        <script>
        	$(function(){ 
	 		$("#accept").click(function(){
				$("#reviewForm").attr('action','/leyi/${operator.username}/theme_mgr/accept');
				$("#reviewForm").submit();
			});
	 		$("#refuse").click(function(){
				$("#reviewForm").attr('action','/leyi/${operator.username}/theme_mgr/refuse');
				$("#reviewForm").submit();
			});
        });
        </script>
     </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
 </div><!-- /.modal -->  
  
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
<script>
$("#tipModal").modal('show');
</script>
</c:if>

</body>
</html>