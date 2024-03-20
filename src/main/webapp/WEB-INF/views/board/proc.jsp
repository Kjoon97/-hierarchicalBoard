<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@ page session="false"%>
<html>
<head>
<title>게시물 상세보기</title>
<%@include file="../layout/header.jsp" %>
<link href="${path}/resources/css/proc.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="container text-center" style="width: 870px; padding: 150px">
		<h3>게시글 조회 (프로시저 활용 버전)</h3>
		<hr>
		<input type="text" class="form-control" id="id" value=${board.id } style="display: none">
		<p style="height: 12px">
			제목: <c:out value="${board.title}" />
		</p>
		<p style="height: 12px">
			게시글 ID: <c:out value="${board.id}" />
		</p>
		<div style="display: flex; justify-content: space-between; height: 25px;">
			<div>
				<p id="date" style="display: inline-block; margin-right: 5px">
					작성일:
					<fmt:formatDate pattern="yyyy-MM-dd hh:mm" value="${board.regDate}" />
				</p>
				<p id="date" style="display: inline-block;">
					수정일:
					<fmt:formatDate pattern="yyyy-MM-dd hh:mm" value="${board.updateDate}" />
				</p>
				<p id="viewCount" style="display: inline-block; margin-left: 15px;">
					조회:
					<c:out value="${board.viewCount}" />
				</p>
			</div>
		</div>
			<p id="date">			
				삭제 예정일:
			<fmt:formatDate pattern="yyyy-MM-dd" value="${board.deleteDate}" />
			</p>
		<hr>
		<br>
		<div class="container text-center">
			<pre>
				<c:out value="${board.content}" escapeXml="false" />
			</pre>
		</div>
		<br>
		<hr>
		<br>
	</div>
</body>
</html>