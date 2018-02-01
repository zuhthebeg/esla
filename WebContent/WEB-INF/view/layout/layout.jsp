<!doctype html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<meta http-equiv="Page-Enter" content="blendTrans(Duration=0.5)"/>
<meta http-equiv="Page-Exit" content="blendTrans(Duration=0.5)"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
	
<title><tiles:getAsString name="title" /></title>	
	<link rel="stylesheet" type="text/css"	href="<c:url value="/resource/style/table.css"/>" />
	<link rel="stylesheet" type="text/css"	href="<c:url value="/resource/style/main.css"/>" />
	<link rel="stylesheet" type="text/css"	href="<c:url value="/resource/style/button.css"/>" />

	<script type="text/javascript" src="<c:url value="/resource/script/jquery/jquery.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resource/script/jquery/jquery.form.js"/>"></script>
	
	<script src="<c:url value="/resource/script/jquery/jquery-ui.min.js"/>" type="text/javascript"></script>
	
	<script src="<c:url value="/resource/script/jquery/jquery.cookie.js"/>" type="text/javascript"></script>
	
	<link href="<c:url value="/resource/script/jquery/ui.dynatree.css"/>" rel="stylesheet" type="text/css">
	<script src="<c:url value="/resource/script/jquery/jquery.dynatree.js"/>" type="text/javascript"></script>
	
	<script src="http://code.highcharts.com/highcharts.js"></script>
	<script src="http://code.highcharts.com/modules/exporting.js"></script>
	<script>
		jQuery(function($) {
			var layerWindow = $('.mw_layer');
	
			$('span .close').click(function() {
				layerWindow.removeClass('open');
			});
	
			// Hide Window
			/*
			layerWindow.find('>.bg').mousedown(function(event) {
				layerWindow.removeClass('open');
				return false;
			});*/
		});
		function closeContent(){$('.mw_layer').removeClass('open');}
		
		function loadLoginForm(){
			$('.mw_layer').addClass('open');
			$('#layer').html($('#hiddenForm').html());
		}
		
		function loginAdmin(){
			var id = $('#admin_id').val();
			var pw = $('#admin_pw').val();
			
			console.log(id, pw);
			if($.trim(id) == "" || $.trim(pw) == "")
				return false;
			
			$.ajax({
				url : '/esla/login.json',
				method : 'post',
				data : {user_id : id, user_pw : pw},
				success : function(data){
					console.log(data);	
					if(data.status == 'success')
						$('.mw_layer').removeClass('open');
					else
						alert('아이디 또는 패스워드가 일치하지 않습니다.');
				}
			});
		}
		
		function registUser(){
			var id = $('#user_id').val();
			var pw = $('#user_pw').val();
			
			if($.trim(id) == "" || $.trim(pw) == "")
				return false;
			$.ajax({
				url : '/esla/member/join.json',
				method : 'post',
				data : {user_id : id, user_pw : pw},
				success : function(data){
					if(data.status == 'success'){
						alert('가입되었습니다.');
						$('#joinForm').hide();
					}else
						alert('가입실패. 관리자만 가능합니다.');
				}
			});
		}
		
		function changePW(){
			var id = $('#cuser_id').val();
			var pw = $('#cuser_pw').val();
			var new_pw = $('#new_pw').val();
			
			if($.trim(id) == "" || $.trim(pw) == "" || $.trim(new_pw) == "")
				return false;
			
			$.ajax({
				url : '/esla/member/changepw.json',
				method : 'post',
				data : {user_id : id, user_pw : pw, new_pw : new_pw},
				success : function(data){
					if(data.status == 'success'){
						alert('변경되었습니다.');
						$('#changeForm').hide();
					}else
						alert('변경실패. 관리자만 가능합니다.');
				}
			});
		}
	</script>
</head>

<body <c:if test="${empty sessionScope.user}">onload="loadLoginForm()" </c:if>> 
	<div id="wrapper">  
		<div id="header">
			<tiles:insertAttribute name="header" />
		</div>
		
		<div id="left">
			<tiles:insertAttribute name="left" />
		</div>
		
		<div id="content">
			<tiles:insertAttribute name="content" />
		</div>
		
		<div id="right">
			<tiles:insertAttribute name="right" />
		</div>
		
		<div id="footer">
			<tiles:insertAttribute name="footer" />
		</div>
	</div>
	<!-- light box -->
	<div class="mw_layer">
		<div class="bg"></div>
		<div id="layer"></div>
	</div>
	<div id="hiddenForm" style="display:none;" >
		<div id="loginForm" style="width:100%; height : 300px; text-align : center;margin-top:25%;">
			<c:if test="${empty sessionScope.user}">
				<div id="hideLogin">
					<h2>로그인</h2>
					id : <input type="text" id="admin_id" />
					pw : <input type="password" id="admin_pw" />
					<span class="button black large"><a href="#" onclick="loginAdmin();" >로그인</a></span> 
				</div>
			</c:if> 
				<span class="button black large"><a href="#" onclick="$('#joinForm').toggle();" >사용자등록</a></span> 
				<span class="button black large"><a href="#" onclick="$('#changeForm').toggle();" >pw변경</a></span>
				<span class="button black large"><a href="/esla/main/" >메인으로</a></span>
			<div id="joinForm" style="display:none;">
				<br/><br/><hr/><h2>사용자 등록</h2><br/>
				id <input type="text" id="user_id" /> pw <input type="password" id="user_pw" />
				<span class="button black large"><a href="#" onclick="registUser();" >등록</a></span> 
			</div>
			<div id="changeForm" style="display:none;">
				<br/><br/><hr/><h2>패스워드 변경</h2><br/>
				id <input type="text" id="cuser_id" /> 기존 pw <input type="password" id="cuser_pw" /><br/><br/> 변경할 패스워드 <input type="password" id="new_pw" />
				<br/><br/><span class="button black large"><a href="#" onclick="changePW();" >변경</a></span> 
			</div>
		</div>
	</div>
</body>
</html>