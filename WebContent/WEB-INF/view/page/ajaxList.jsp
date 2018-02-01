<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach items="${articleList}" var="article">
	<tr>
		<td>${article.page}</td> 
		<td><a href="#layer" onclick="openContent('${article.idx}')">${article.title} </a></td>
		<td>${article.writer}</td>
		<td>${article.grade}</td>
		<td>${article.publisher}</td>  
		<td>${article.regdate}</td>
	</tr>
</c:forEach>
