<!-- 页面脚部相关声明 -->
<%@ tag language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="leyi" uri="http://www.jeekhan.me/leyi/"%>

<ul class="pager" style="margin:0"> 
<c:if test="${pageCond.begin>1 }"><li class="active"><a href="${pageCond.begin-pageCond.pageSize }">上一页</a></li></c:if>
<c:if test="${pageCond.begin==1 }"><li class="disabled"><a href="#">上一页</a></li></c:if>
 <li>
                 共有<span id="pageCnt">${leyi:cellFloat(pageCond.count/pageCond.pageSize)}</span>页 
 	<input type="number" id="pageNo" maxLength=3 min=1 style='width:25px' value='${leyi:cellFloat(pageCond.begin/pageCond.pageSize)}'>
 	<button id="go">GO</button>
 </li>
 <c:if test="${pageCond.begin+pageCond.pageSize<=pageCond.count }"><li class="active"><a href="${pageCond.begin+pageCond.pageSize }">下一页</a></li></c:if>
 <c:if test="${pageCond.begin+pageCond.pageSize>pageCond.count }"><li class="disabled"><a href="#">下一页</a></li></c:if>
</ul>
