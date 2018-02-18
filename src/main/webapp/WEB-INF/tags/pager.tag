
<%@ tag language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="leyi" uri="http://www.jeekhan.me/leyi/"%>
<%--
分页处理
1、页面需要设置查询表单(id=searchForm)，表单中设置查询条件，条件命名与MappedStatement中的字段名一致；并且表单的action需设置为目标URL；
2、表单中设置掩藏字段:begin；如果需要个性化控制每页记录数，则可添加掩藏字段：pageSize;
3、表单中设置掩藏字段：condParams，该字段为所有查询参数的 json 字符串表示，包括分页信息；
 --%>
<ul class="pager" style="margin:0"> 
<c:if test="${pageCond.begin>0 }"><li id="upPage" class="active"><a href="###">上一页</a></li></c:if>
<c:if test="${pageCond.begin<=0 }"><li id="upPage" class="disabled"><a href="###">上一页</a></li></c:if>
 <li>
                 共有<span id="pageCnt">${leyi:cellFloat(pageCond.count/pageCond.pageSize)}</span>页 
 	<input type="number" id="pageNo" maxLength=15 min=1 style='width:55px' value='${leyi:cellFloat((pageCond.begin+1)/pageCond.pageSize)}'>
 	<button id="go">GO</button>
 </li>
 <c:if test="${pageCond.begin+pageCond.pageSize<pageCond.count }"><li id="downPage" class="active"><a href="#">下一页</a></li></c:if>
 <c:if test="${pageCond.begin+pageCond.pageSize>=pageCond.count }"><li id="downPage" class="disabled"><a href="#">下一页</a></li></c:if>
</ul>
<script>
 var pageSize = ${pageCond.pageSize};
 var currBegin = ${pageCond.begin};
 var count = ${pageCond.count};
 var searchForm = $('#searchForm');
 var beginObj = $('#searchForm input[name="begin"]');
 var condParamsObj = $('#searchForm input[name="condParams"]');
 $('#go').click(function(){
	 
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
	 var go_begin = pageSize*(pageNo-1);
	 beginObj.val(go_begin);
	 condParamsObj.val(getCondParams());
	 searchForm.submit();
	 //window.location.href = pagSize*(pageNo-1);
 });
 $('#upPage a').click(function(){
	 var begin = currBegin-pageSize;
	 beginObj.val(begin);
	 condParamsObj.val(getCondParams());
	 searchForm.submit();
 });
 $('#downPage a').click(function(){
	 var begin = currBegin+pageSize;
	 beginObj.val(begin);
	 condParamsObj.val(getCondParams());
	 searchForm.submit();
 });
 //组装查询条件参数为JSON
 function getCondParams(){
	 var paramsObj = {};
	 $('#searchForm input').each(function(){
		 var name = $(this).attr('name');
		 if(!$(this).attr('disabled') && name != 'condParams'){
		 	paramsObj[name] = $(this).val();
		 }
	 });
	 return JSON.stringify(paramsObj);
 }
 
</script>
