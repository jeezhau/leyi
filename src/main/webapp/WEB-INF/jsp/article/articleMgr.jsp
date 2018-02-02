<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="me.jeekhan.leyi.model.ThemeClass,java.util.*,me.jeekhan.leyi.common.*"%>
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
  <title>文章记录管理</title>
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
	<ul class="nav nav-pills nav-justified nav-xs" style="background-color:#66ccff;margin-bottom:10px;">
  	 <c:forEach items="${topThemes}" var="item">
      <c:if test="${currTheme.id==item.id}"> <li class="active"><a href="/leyi/${userInfo.username}/article_mgr/theme/${item.id}/1">${item.name}</a></li></c:if>
      <c:if test="${currTheme.id!=item.id}"> <li><a href="/leyi/${userInfo.username}/article_mgr/theme/${item.id}/1">${item.name}</a></li></c:if>
     </c:forEach>
 	</ul>
  </div>
  
  <div class="row">
	<!-- 左面主题 -->
    <div class="col-xs-4" >
      <div class="panel panel-info">
   	    <div class="panel-heading" style="margin:0">
          <ol class="breadcrumb" style="margin:0;">
	      <c:forEach items="${themeTreeUp}" var="item">
	       <c:if test="${currTheme.id==item.id}"> <li class="active">${item.name}</li> </c:if>
	       <c:if test="${currTheme.id!=item.id}"> <li><a href="/leyi/${operator.username}/article_mgr/theme/${item.id}/1">${item.name}</a></li> </c:if>
	      </c:forEach>
	      <c:if test="${fn:length(themeTreeUp)<=0 }">
		   还没有主题，请添加！
		 </c:if>
	      </ol>
        </div>
  		<div class="panel-body">
    	  <p id="themeDesc-show">&nbsp;&nbsp;&nbsp;&nbsp;${currTheme.descInfo}</p>
    	  <p id="keywords-show"><c:if test="${not empty currTheme.id}"> 关键词：${currTheme.keywords}</c:if></p> 
   		</div>
	   	<ul class="list-group">
	     <c:forEach items="${children}" var="item">
	       <li class="list-group-item"><a href="/leyi/${operator.username}/article_mgr/theme/${item.id}/1">${item.name}</a></li>
	     </c:forEach>
	   	</ul>
  	  </div>
    </div><!-- end of 左面主题 -->
    <!-- 右面文章列表 -->
    <div class="col-xs-8" >
      <div class="panel panel-info">
        <div class="panel-heading" style="margin:0">
	     	<a href="/leyi/${operator.username}/article_mgr/editing" onclick="if('${currTheme.id}') { return true;} else { alert('请先选择主题！'); return false;}" target="_blank">新增文章</a>
        </div>
        <table class="table table-striped  table-bordered table-hover ">
          <thead>
   	        <tr><th width="3%"></th><th width="25%">文章标题</th><th>关键词 </th><th width="20%">操作 </th></tr>
          </thead>
          <tbody> 
           <c:forEach items="${currArticles}" var="item" varStatus="sta">
            <tr>
              <td>${sta.count}</td>
              <td>${item.name}</td>
              <td>${item.keywords }</td>
              <td>
                [<a href="/leyi/${operator.username}/article_mgr/editing?articleId=${item.id }" target="_blank">编辑</a>]&nbsp;
                [<a href="/leyi/${operator.username}/article_mgr/delete?articleId=${item.id }">删除</a>]&nbsp;
                [<a href="/leyi/${operator.username}/article_mgr/detail/${item.id }"  target="_blank">显示</a>]
            </tr>
           </c:forEach>
           <tr >
           	<td colspan="3">
				<jk:pager></jk:pager>
           	</td>
           </tr>
          </tbody>
        </table>
      </div>
    </div><!-- end of 右面文章列表 -->
  </div>   
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
</c:if>

  <jk:copyRight></jk:copyRight>
  
<script>
 $('#go').click(function(){
	 var pagSize = ${pageCond.pageSize};
	 var pageNo = $('#pageNo').val();
	 if(!pageNo){
		 return false;
	 }
	 if(pageNo<1){
		 alert('小于最小页数（1）！');
		 pageNo = 1;
	 }
	 var pageCnt = $('#pageCnt').text();
	 if(pageNo > pageCnt){
		 alert('超过最大页数（'+pageCnt+'）！');
		 return false;
	 }
	 window.location.href = pagSize*(pageNo-1)+1;
 });

</script>

</body>
</html>
