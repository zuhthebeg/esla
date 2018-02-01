<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
	function updateKorDicWordNo(idx){
		
		var word_no = $('input:radio[name=choice]:checked').val();
		$('input:radio[name=choice]:checked').click();
		 $.ajax({
			   type:'post',
			   url:'/esla/tags/updateWordno',
			   data: ({word_no:word_no, idx:idx, tech_term_name:$('#tech_term_name'+idx).val(), word_class:$('#word_class'+idx).val()}),
			   success:function(data){
				  $('.mw_layer').removeClass('open');
			   }
		});
	}
	
	function setTechAndSpName(tech, sp, idx){
		if($.trim(tech) != "")
			$('#tech_term_name'+idx).val(tech);
		if($.trim(sp) != "")
			$('#word_class'+idx).val(sp);
	}
</script>
<table style="width: 100%;">							
	<caption>표준 국어 대사전 검색결과 : ${baseWord}</caption>	
	<tr>
		<th width="5%">선택</th> 
		<th width="10%">단어번호</th>
		<th width="10%">상위표제어</th>
		<th width="10%">전문어 분류</th>
		<th width="10%">품사</th>  
		<th width="55%">뜻풀이</th>
	</tr>
	<c:forEach items="${kdList}" var="article">
		<tr>
			<td><input type="radio" name="choice" onclick="setTechAndSpName('${article.tech_term_name}','${article.sp_name}','${idx}')" <c:if test="${article.word_no eq dic_word.word_no}">checked="checked"</c:if> value="${article.word_no}"/></td> 
			<td>${article.word_no}</td>
			<td>${article.upper_word}</td>
			<td>${article.tech_term_name}
			<c:if test="${empty article.tech_term_name}">없음</c:if>
			</td>
			<td>${article.sp_name}</td>  
			<td>${article.definition}</td>
		</tr>
	</c:forEach>
	<tr>
		<td><input type="radio" name="choice" <c:if test="${empty dic_word}">checked="checked"</c:if> value="" /></td> 
		<td colspan="100%">일치하는 단어 없음</td>
	</tr>
</table>
<br/>※ 단어번호가 동일하면 동일한 단어입니다.
<br/>
<span class="button black large" style="float: right;"><input type="button" value="확인" onclick="updateKorDicWordNo('${idx}');" /></span>
