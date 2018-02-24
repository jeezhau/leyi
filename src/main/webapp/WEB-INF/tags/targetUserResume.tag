<!-- 目标用户的个人简介 -->
<%@ tag language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="thumbnail">
	<p align="center"> ${targetUser.username} </p>
	<a target="_blank" href="${contextPath}/${targetUser.username}/user_mgr/detail"><img src="${contextPath}/common/showPic/${targetUser.username}/${targetUser.picture }" alt="惹人靓照"></a>
	<div class="caption">
		<p>&nbsp;&nbsp;&nbsp;&nbsp;
			<c:if test="${not empty operator and targetUser.id == operator.userId}">
			<a href="${contextPath}/${operator.username}/user_mgr/edit">个人中心</a>&nbsp;&nbsp;&nbsp;&nbsp;
			</c:if>
		</p>
		<hr/>
		<div>&nbsp;&nbsp;&nbsp;&nbsp;${targetUser.introduce}</div><!-- 个人简介 -->
	</div>
</div>
