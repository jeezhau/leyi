<!-- 目标用户的个人简介 -->
<%@ tag language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="thumbnail">
	<p align="center"> ${targetUser.username} </p>
	<a target="_blank" href="${contextPath}/${targetUser.username}"><img src="${contextPath}/common/showPic/${targetUser.username}/${targetUser.picture }" alt="惹人靓照"></a>
	<div class="caption">
		<p>${targetUser.introduce}</p><!-- 个人简介 -->
	</div>
</div>
