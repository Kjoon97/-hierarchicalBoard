<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@ page session="false"%>
<html>
<head>
<title>게시물 상세보기</title>
<%@include file="../layout/header.jsp" %>
<link href="${path}/resources/css/detail.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="modal-delete-bg">
		<div class="white-bg">
			<h4>비밀번호 입력하세요</h4>
			<form>
				<div class="my-3">
					<input type="password" class="form-control" id="passwd" placeholder="게시글 비밀번호를 입력하세요">
                	<div id="pw-text1">비밀번호를 입력해주세요.</div>
                	<div id="pw-text2">8자리 ~ 12자리 이내로 입력해주세요.</div>
					<div id="pw-text3">비밀번호는 공백 없이 입력해주세요.</div>
					<div id="pw-text4">영문,숫자,특수문자를 혼합하여 입력해주세요.</div>
				</div>
			</form>
			<div style="text-align: right;">
				<button type="submit" class="btn btn-outline-dark" id="modal-delete-submit">확인</button>
				<button type="button" class="btn btn-outline-dark" id="modal-delete-close">취소</button>
			</div>
		</div>
	</div>

	<div class="container text-center" style="width: 870px; padding: 150px">
		<h1>공지사항</h1>
		<br>
		<h4>롯데관광의 공지사항 및 새로운 소식을 알려드립니다.</h4>
		<hr>
		<input type="text" class="form-control" id="id" value=${board.id } style="display: none">
		<p style="height: 12px">
			<c:out value="${board.title}" />
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
		<!-- 오른쪽 정렬 스타일을 적용한 부분 -->
		<div class="right-align">
			<a href="/" class="btn btn-outline-dark">목록</a>
			<!--<button id="update-btn" type="button" class="btn btn-outline-dark">수정</button>-->
			<a href="/board/${board.id}" class="btn btn-outline-dark">수정</a>
			<button id="delete-btn" type="button" class="btn btn-outline-dark">삭제</button>
		</div>
	</div>
	<script src="${path}/resources/js/board.js"></script>
</body>
</html>