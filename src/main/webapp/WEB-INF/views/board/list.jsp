<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@ page session="false"%>
<html>
<head>
	<title>게시물 목록</title>
	<%@include file="../layout/header.jsp" %>
	<link href="${path}/resources/css/list.css" rel="stylesheet" type="text/css">
	<style>
		ul.pagination { 
			list-style: none; 
			padding: 0; 
			justify-content: center;
			flex-wrap: wrap; 
			gap: 6px; /* 페이지 버튼 사이의 간격을 3px로 설정합니다. */
		}
		ul.pagination li.paginate_button a { 
			display: block; 
			border: 1px solid #dee2e6; 
			text-decoration: none; 
			color: #333; 
		}
		ul.pagination li.paginate_button.active a {
        	background-color: red; /* active 상태일 때 버튼 색을 빨간색으로 변경합니다. */
        	color: white; /* active 상태일 때 버튼 글자 색을 흰색으로 변경합니다. */
        	border: #dee2e6;
    	}
	</style>
</head>
<body style="margin: 150px">
		<div class="container text-center" style="width: 870px;">
            	<h1>공지사항</h1>
				<br>
				<h4>롯데관광의 공지사항 및 새로운 소식을 알려드립니다.</h4>
				<br>
            <!-- 검색기능 -->
      		<div class="search-container">
            <form id="searchForm" action="/" method="get" style="width: 830px;">
                <select name="type" style="width: 110px; height: 35px;">
                    <option value="T" <c:out value='${pageMaker.cri.type eq "T"?"selected": "" }'/>>제목</option>
                    <option value="C" <c:out value='${pageMaker.cri.type eq "C"?"selected": "" }'/>>내용</option>
                    <option value="W" <c:out value='${pageMaker.cri.type eq "W"?"selected": "" }'/>>작성자</option>
                </select>
                <input type="text" name="keyword" value="<c:out value='${pageMaker.cri.keyword}'/>" style="width: 310.52px; height: 34px;">
                <button style="width: 33px; height: 34px; background-color: #3c64d6; padding:0px;"><img src="/resources/img/search.png" style="width: 25px; height: 25px;"></button>
            </form>
            </div>
               <!-- 검색기능 -->
			<table class="table table-horizontal table-bordered">
				<thead class="thead-strong">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>작성일</th>
						<th>조회</th>
					</tr>
				</thead>
				<tbody id="tbody">
					<c:forEach items="${boards}" var="board">
						<c:set var="i" value="${i+1}" />
						<tr>
							<td>${i+(pageMaker.cri.pageNum-1)*pageMaker.cri.amount}</td>
							<td>
								<c:url value="/board/detail/${board.id}" var="url" />
								<a href="${url}">
									<c:out value="${board.title}" escapeXml="false" />
								</a>
							</td>
							<td>
								<fmt:formatDate pattern="yyyy-MM-dd" value="${board.regDate}" />
							</td>
							<td>
								<c:out value="${board.viewCount}" />
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br>

			<!-- Paging -->
			<nav class="page navigation">
				<ul class="pagination">
					<c:if test="${pageMaker.prev}">
						<li class="paginate_button previous"><a class="page-link" href="${pageMaker.startPage - 1}">Prev</a></li>
					</c:if>
					<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
						<li class="paginate_button ${pageMaker.cri.pageNum == num ? "active" : "" } ">
							<a class="page-link" href="${num}">${num}</a>
						</li>
					</c:forEach>
					<c:if test="${pageMaker.next}">
						<li class="paginate_button next"><a class="page-link" href="${pageMaker.endPage + 1}">Next</a></li>
					</c:if>
				</ul>
			</nav>
			<!-- /.page -->
			<c:if test="${not empty boards}">
				<div>
					<ul	id="boardPagination" class="pagination">
						<c:forEach var="num" begin="1" end="${pageMaker.endPage}" step="1">
							<li class="paginate_button<c:if test="${pageMaker.cri.pageNum == num}"> active</c:if>">
								<a class="page-link" data-pageno="${page}" href="${num}">${num}</a>
							</li>
						</c:forEach>
					</ul>
				</div>
			</c:if>

			<form id='actionForm' action="/" method="get">
				<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
				<input type="hidden" name="amount" value="${pageMaker.cri.amount}">
				<input type="hidden" name="type" value="<c:out value='${pageMaker.cri.type}'/>">
				<input type="hidden" name="keyword" value="<c:out value='${pageMaker.cri.keyword}'/>">
			</form>

			<br>
			<div class="text-center">
				<a href="/board" class="btn btn-outline-dark">게시글 등록</a>
			</div>
		</div>

	<script src="${path}/resources/js/list.js"></script>
</body>
</html>
