<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="me.jeekhan.leyi.model.*,java.util.*,me.jeekhan.leyi.common.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
  <link rel="shortcut icon" href="${contextPath}/images${contextPath}.ico" type="image/x-icon" />
  <link rel="stylesheet" href="${contextPath}/bootstrap-3.3.5/css/bootstrap.min.css">  
  <script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
  <script src="${contextPath}/bootstrap-3.3.5/js/bootstrap.min.js"></script>

  <script src="${contextPath}/ckeditor/ckeditor.js"></script>
</head>
<body style="background-color: #efefef;">  
<div style="height:38px;background-color:#b3b3ff ;margin-bottom:5px;">

</div>
<div class="container">
  <jk:topSysMenuBar></jk:topSysMenuBar>
  <div class="row">
    <div class="col-sm-3 hidden-xs" >
       <jk:targetUserResume></jk:targetUserResume>
	</div>
    <!--======================中间主要内容  ===================--> 
    <div class="col-sm-9 light-gray-bg">
	  <div class="panel panel-info">
        <div class="panel-heading text-center"><h3 class="panel-title">个人信息</h3></div>
	    <div class="panel-body">
		  <div class="row">
		  	<div class="col-sm-6 "> 用户名：${targetUser.username} </div>
		    <div class="col-sm-6 "> 邮箱：${targetUser.email }</div>
		  </div> 
		  <div class="row">
		    <hr>
		    <div class="col-sm-6 "> 生日：${targetUser.birthday }</div>
		    <div class="col-sm-6 "> 性别： 
		     <c:choose>
		     	<c:when test="${targetUser.sex == 'M'}">男</c:when>
		     	<c:when test="${targetUser.sex == 'F'}">女</c:when>
		     	<c:when test="${targetUser.sex == 'N'}">保密</c:when>
		     </c:choose>   
		     </div>
		   </div>
		   <div class="row">
		     <hr>
		  	 <div class="col-sm-12 ">所在城市：${targetUser.city}</div>
		  </div>
		  <div class="row">
		    <hr>
		  	<div class="col-sm-12 "> 兴趣爱好：${targetUser.favourite} </div>
		  </div> 
		  <div class="row">
		    <hr>
		    <div class="col-sm-12 "> 职业：${targetUser.profession} </div>
		  </div>
		  <div class="row">
		    <hr>
		    <div class="col-sm-12 "> &nbsp; &nbsp; &nbsp; &nbsp;${targetUser.introduce} </div>
		  </div>
		  <c:if test="${mode == 'review'}"><%--审核模式 --%>
		  <div class="row">
		     <hr>
		     <form id="reviewForm" method="post" action="">
		     <input type="hidden" name="userId" value="${targetUser.id }">
		     <div class="form-group">
		        <label  class="col-sm-2 control-label">审核说明</label>
		        <div class="col-sm-10">
		          <textarea class="form-control" id="remark" name="remark" placeholder="请输入审核说明" rows="5" maxLength=600></textarea>
		        </div>
		      </div>
	         <div class="col-sm-offset-4 col-sm-10">
	           <button type="button" class="btn btn-info" id="accept" style="margin:20px">&nbsp;&nbsp;通过&nbsp;&nbsp;</button>
	           <button type="button" class="btn btn-warning" id="refuse" style="margin:20px">&nbsp;&nbsp;拒绝&nbsp;&nbsp; </button>
	         </div>
	         </form>
	         <script type="text/javascript">
	         $(function(){ 
		 		$("#accept").click(function(){
					$("#reviewForm").attr('action','${contextPath}/${operator.username}/user_mgr/accept');
					$("#reviewForm").submit();
				});
		 		$("#refuse").click(function(){
					$("#reviewForm").attr('action','${contextPath}/${operator.username}/user_mgr/refuse');
					$("#reviewForm").submit();
				});
	         });
	         </script>
     	  </div>
      	  </c:if>
	    </div>
	  </div> <!-- panel -->
    </div>
  </div><!-- end of row -->
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
<script>
	$("#tipModal").modal('show');
</script>
</c:if>

</body>
</html>