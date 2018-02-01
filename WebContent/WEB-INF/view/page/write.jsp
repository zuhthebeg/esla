<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="<c:url value="/resource/script/jquery/jquery.form.js"/>" type="text/javascript"></script>
<script>
	function fileCheck(){
		if($('#textarea_write').val() == "") {alert("내용을 입력하세요.");$('#textarea_write').focus();return false;}
		if($('#file').val() == "") {
			$('#file').remove();
		}
	}
	$('#insertForm').ajaxForm({
        beforeSubmit: function(a,f,o) {
        	if($('#textarea_write').val() == "")return false;
        	$('#insertButton').hide();
        	$('#loding').show();
        	
        },
        success: function(data) {
			alert('입력되었습니다.');
			nodeManagerAndPageList('${doc_idx}',6);
			$('.mw_layer').removeClass('open');
        }
    });
</script>
	<form action="/esla/insert" method="post" enctype="multipart/form-data" name="insertForm" onsubmit="fileCheck();" id="insertForm">
		<table style="width: 100%;">							
			<caption>페이지입력</caption>						
			<tr>									
				<th>작성자</th>						
				<td colspan="100%"><input type="text" name="writer" size="20" value="테스트" /></td>
			</tr>
			<tr>
				<th>교육과정</th>
				<td><input type="text" name="process" size="20" 	value="${process}" /></td>

				<th>학년</th>
				<td><input type="text" name="grade" size="5" 			value="${grade}"/></td>
				
				<th>출판사</th>
				<td><input type="text" name="publisher" size="20" 	value="${publisher}" /></td>
			</tr>
			<tr>
				<th>과목</th>
				<td><input type="text" name="title" size="15" 	value="${title}" /></td>
				
				<th>단원</th>
				<td><input type="text" name="chapter" size="20" 	value="${chapter}"/></td>
					
				<th>페이지</th>
				<td><input type="text" name="page" size="5" value="${articleCount}"/>페이지</td>
			</tr>
			<tr>
				<th height="300px">내용</th> 
				<td colspan="100%">
					<font color="red">※ 개행문자시 문장번호 증가, 마침표(.) 물음표(?)시에도 문장번호 증가</font>
					<textarea rows="18" cols="95" name="content" id="textarea_write" onKeyUp="javascript:str_limit_check( this, 4000, 'checklength');" ></textarea>				<!--  -->
					<span id="checklength">(0/4000)</span>
				</td>
			</tr>
			<!-- <tr>
				<th>이미지파일</th>
				<td colspan="100%"><input type="file" name="file" id="file"></td>
			</tr> -->
		</table>
		<input type="hidden" name="document_idx" value="${doc_idx}" />
		<span class="button black large" style="float: right;">
			<a href="#" onclick="closeContent()">취소</a>
		</span> 
		<span class="button black large" style="float: right;">
			<input id="insertButton" type="submit" value="입력" />
		</span>
		
		<center>
			<div id="loding" class="loding"><h2>형태소 분석중입니다...</h2></div>
		</center>
	</form> 
