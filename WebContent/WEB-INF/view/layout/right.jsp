<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
	function extractBase(){
		var base_text = $('#base_text').val();
		if(base_text == "") return;
		base_text = $.trim(base_text);
		 $.ajax({
			   type:'post',
			   url:'/esla/tags/extract.json',
			   data: ({text : base_text }),
			   success:function(data){
				 //$('#extractResult').html(data);
				 $('#baseWordResult').val(data.baseWord);
				 $('#baseWordPart').val(data.basePart);
			   }
		});
	}
	
	function searchDictionary(){
		var bigram_checked = document.getElementById("checkBiGram").checked;
		
		var search_text = $('#search_text').val();
		if(search_text == "") return;
		search_text = $.trim(search_text);
		 $.ajax({
			   type:'post',
			   url:'/esla/tags/search',
			   data: ({text : search_text, bigram:bigram_checked }),
			   success:function(data){
				   $('#searchResult').html(data);
				   $('#tabs-min').tabs();
			   }
		});
	}
	
	function bigramOperation(){
		var bigram_text = $('#bigram_text').val();
		if(bigram_text == "") return;
		bigram_text = $.trim(bigram_text);
		 $.ajax({
			   type:'post',
			   url:'/esla/tags/bigram',
			   data: ({text : bigram_text }),
			   success:function(data){
				   $('#bigramResult').html(data);
				   
				   /* case when json
				   for(var i=0; i<data.bigramArray.length; i++)
					   console.log(data.bigramArray[i]);
				   */
			   }
		});
	}
</script>
<h2>Sub Operation</h2> 
<h3>기본형 추출</h3> 
<input type="text" size="10" placeholder="어절 입력" id="base_text">
<input type="button" value="변환" onclick="extractBase();">
<div id="extractResult">
	<h4>형태소 분석 결과</h4>
	기본형 : <input type="text" id="baseWordResult" size="8" value="${baseWord}" onfocus="$('#bigram_text').val(this.value)"> <br/>
	품사 : <input type="text" id="baseWordPart" size="8" value="${basePart}"> <br/>
</div>

<hr style="margin-bottom : 100px;" />

<h3>용어후보 추출</h3> 
<input type="text" size="10" placeholder="어절입력" id="bigram_text">
<input type="button" value="추출" onclick="bigramOperation();">
<h4>바이그램 결과</h4>
<div id="bigramResult">
	
</div>

<hr style="margin-bottom : 100px;" />

<h3>전문용어집 검색</h3> 
<input type="text" size="10" placeholder="기본형 입력" id="search_text">
<input type="button" value="검색" onclick="searchDictionary();">
<h4>검색 결과(<input type="checkbox" id="checkBiGram"/>바이그램적용)</h4>
<div id="searchResult">
</div>


