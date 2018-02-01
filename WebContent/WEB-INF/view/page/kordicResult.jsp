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
	<caption>ǥ�� ���� ����� �˻���� : ${baseWord}</caption>	
	<tr>
		<th width="5%">����</th> 
		<th width="10%">�ܾ��ȣ</th>
		<th width="10%">����ǥ����</th>
		<th width="10%">������ �з�</th>
		<th width="10%">ǰ��</th>  
		<th width="55%">��Ǯ��</th>
	</tr>
	<c:forEach items="${kdList}" var="article">
		<tr>
			<td><input type="radio" name="choice" onclick="setTechAndSpName('${article.tech_term_name}','${article.sp_name}','${idx}')" <c:if test="${article.word_no eq dic_word.word_no}">checked="checked"</c:if> value="${article.word_no}"/></td> 
			<td>${article.word_no}</td>
			<td>${article.upper_word}</td>
			<td>${article.tech_term_name}
			<c:if test="${empty article.tech_term_name}">����</c:if>
			</td>
			<td>${article.sp_name}</td>  
			<td>${article.definition}</td>
		</tr>
	</c:forEach>
	<tr>
		<td><input type="radio" name="choice" <c:if test="${empty dic_word}">checked="checked"</c:if> value="" /></td> 
		<td colspan="100%">��ġ�ϴ� �ܾ� ����</td>
	</tr>
</table>
<br/>�� �ܾ��ȣ�� �����ϸ� ������ �ܾ��Դϴ�.
<br/>
<span class="button black large" style="float: right;"><input type="button" value="Ȯ��" onclick="updateKorDicWordNo('${idx}');" /></span>
