<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="me.jeekhan.leyi.model.*,java.util.*,me.jeekhan.leyi.common.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="jk" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">  
  <title>${operator.username}—文章资料管理</title>
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
           <li><a href="${contextPath}/${operator.username}/article_mgr/">ROOT</a></li>
	      <c:forEach items="${themeTreeUp}" var="item">
	       <c:if test="${currTheme.id==item.id}"> <li class="active">${item.name}</li> </c:if>
	       <c:if test="${currTheme.id!=item.id}"> 
	       <li><a class="go_link" href='{"begin":"0","themeId":"${item.id}"}'>${item.name}</a></li>
	       </c:if>
	      </c:forEach>
	      <%-- 
	      <c:if test="${not empty childrenThemes and themeTreeUp==null}"><span style="color:red">请选择待操作文章的主题！</span></c:if>
		  <c:if test="${empty childrenThemes and themeTreeUp==null}"><span style="color:red">您还没有主题，请先添加！</span></c:if>
	       --%>
	      </ol>
        </div>
	   	<ul class="list-group">
	     <c:forEach items="${childrenThemes}" var="item">
	       <li class="list-group-item"><a class="go_link" href='{"begin":"0","themeId":"${item.id}"}'>${item.name}</a></li>
	     </c:forEach>
	   	</ul>
  	  </div>
    </div><!-- end of 左面主题 -->
    <!-- 右面文章列表 -->
    <div class="col-xs-8" >
    	  <form id="searchForm" action="${contextPath}/${operator.username}/article_mgr/" style="display:none"><!-- 查询表单 -->
    	  	<input type="number" name="begin" value="0">
    	  	<input type="number" name="pageSize" value="20">
    	  	<input type="number" name="themeId" value="${currTheme.id}">
    	  	<input type="text" name="condParams" value="">
    	  </form>
      <div class="panel panel-info">
        <div class="panel-heading" style="margin:0">
	     	<a href="${contextPath}/${operator.username}/article_mgr/edit/0" onclick="if(${not empty currTheme.id}) { return true;} else { alert('请先选择主题！'); return false;}" target="_blank">新增文章</a>
        </div>
        <table class="table table-striped  table-bordered table-hover ">
          <thead>
   	        <tr><th width="3%"></th><th width="75%">文章标题</th><th width="25%">操作 </th></tr>
          </thead>
          <tbody> 
           <c:forEach items="${articles}" var="item" varStatus="sta">
            <tr>
              <td>${sta.count}</td>
              <td><a href="${contextPath}/${operator.username}/article_mgr/detail/${item.id }"  target="_blank">${item.name}</a></td>
              <td>
                [<a href="${contextPath}/${operator.username}/article_mgr/edit/${item.id }" target="_blank">编辑</a>]&nbsp;
                [<a href="${contextPath}/${operator.username}/article_mgr/delete/${item.id }" onclick="if(confirm('确定要删除该文章吗？')){ return true;} else { return false;}">删除</a>]&nbsp;
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
  
  <jk:copyRight></jk:copyRight> 
</div><!-- container -->
<script type="text/javascript">
	$('.go_link').click(function(){
		var url = '${contextPath}/${operator.username}/article_mgr/?condParams=' + encodeURIComponent($(this).attr('href'));
		$(this).attr('href',url);
		return true;
	});
</script>
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
           ${param.error}  ${error}
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
