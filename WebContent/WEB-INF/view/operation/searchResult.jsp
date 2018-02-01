<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" type="text/css"	href="<c:url value="/resource/script/jquery/jquery-ui.css"/>" />

<style>
	#tabs-min {
		background: transparent;
		border: none;
	}
	#tabs-min .ui-widget-header {
		background: transparent;
		border: none;
		border-bottom: 1px solid #c0c0c0;
		-moz-border-radius: 0px;
		-webkit-border-radius: 0px;
		border-radius: 0px;
	}
	#tabs-min .ui-state-default {
		background: transparent;
		border: none;
	}
	#tabs-min .ui-state-active {
		border: none;
	}
	#tabs-min .ui-state-default a {
		color: #c0c0c0;
	}
	#tabs-min .ui-state-active a {
		color: #459e00;
	}
	
	#tabs-min div ul{
		overflow:scroll;
		max-height: 150px;
	}
</style>
<div id="tabs-min">
	<ul>
		<li><a href="#tabs-min-1">물리(${fn:length( phySearchList )})</a></li>
		<li><a href="#tabs-min-2">화학(${fn:length( cheSearchList )})</a></li>
		<li><a href="#tabs-min-3">생물(${fn:length( bioSearchList )})</a></li>
		<li><a href="#tabs-min-4">지학(${fn:length( earSearchList )})</a></li>
	</ul>
	<div id="tabs-min-1">
		<ul>
			<c:forEach items="${phySearchList}" var="word">
				<li>${word}</li> 
			</c:forEach>
		</ul>
	</div>
	<div id="tabs-min-2">
		<ul>
			<c:forEach items="${cheSearchList}" var="word">
				<li>${word}</li> 
			</c:forEach>
		</ul>
	</div>
	<div id="tabs-min-3">
		<ul>
			<c:forEach items="${bioSearchList}" var="word">
				<li>${word}</li> 
			</c:forEach>
		</ul>
	</div>
	<div id="tabs-min-4">
		<ul>
			<c:forEach items="${earSearchList}" var="word">
				<li>${word}</li> 
			</c:forEach>
		</ul>	
	</div>
</div>
