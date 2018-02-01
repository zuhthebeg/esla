<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach items="${bigramArray}" var="bigramText">
	<input type="radio" name="bigram" onclick="$('#search_text').val(this.value);" value="${bigramText}"/>${bigramText} <br/>
</c:forEach>