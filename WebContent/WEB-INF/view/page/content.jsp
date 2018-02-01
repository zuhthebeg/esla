<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>

	function getTagList(idx){
		 //$('.mw_layer').addClass('open');
		 $.ajax({
			   type:'post',
			   url:'/esla/tags',
			   data: ({idx:idx}),
			   success:function(data){
				 $('#content').html(data);
			   }
		});
	}
	
	function deleteArticle(idx){
		 if(confirm("삭제하시겠습니까?")){
			 $.ajax({
				   type:'post',
				   url:'/esla/delete.json',
				   data: ({idx:idx}),
				   success:function(data){
					   console.log(data);
					  if(data.status == "success"){
						  alert("삭제되었습니다.");
						  nodeManagerAndPageList('${article.document_idx}',6);
					  }
				   }
			});
		}
	}
	function deleteTagInfo(idx, sNum, wNum, obj){
		if(!confirm('삭제하시겠습니까?')) return false;
		 $.ajax({
			   type:'post',
			   url:'/esla/tags/delete.json',
			   data: ({idx:idx}),
			   success:function(data){
				  if(data.status == "success"){
					alert("삭제되었습니다.");		
					obj.parentNode.removeChild(obj);
					$('#tagsTable > tbody').children('tr').each(function(){ 
					  	sentence =  $(this).find('input')[1]; 				// 문장번호
					    word = $(this).find('input')[2]; 					//	단어번호
						if(sentence.value == sNum && word.value > wNum){ 	// 문장번호가 같고 단어번호가 삭제될 번호보다 큰경우만
							word.value = word.value -1;						// 단어번호 -1씩해줌
						}
					});
				  }
			   }
		});
	}
	
	function createTagInfo(article_idx, sNum, wNum){
		if(!confirm('하위에 태그를 추가하시겠습니까?')) return false;
		
		 $.ajax({
			   type:'post',
			   url:'/esla/tags/insert.json',
			   data: ({idx:article_idx, sentence : sNum, word : wNum }),
			   success:function(data){
				  if(data.status == "success"){
					alert("추가되었습니다.");		
					openContent(article_idx);
				  }
			   }
		});
	}
/* 	function setSameSentenceValue(obj, sNum, wNum, val){
		$('#tagsTable > tbody').children('tr').each(function(){ 
			var save = $(this).find('input')[0]; 					// 저장 체크 해제
		  	var part = $(this).find('input')[1]; 					// 구분
		  	var sentence =  $(this).find('input')[2]; 				// 문장번호
		  	var word =  $(this).find('input')[3]; 				// 단어번호
			if(sentence.value == sNum && word.value > wNum){ 	// 문장번호가 같고 현재 단어번호보다 클경우 
				part.value = val;								// 구분 셋팅
				save.checked = false;
			}
		});
	} */
	function setSameSentencePart(obj, sNum, wNum, val){
		uncheckSave(obj);
		
		$('#tagsTable > tbody').children('tr').each(function(){ 
			var save = $(this).find('input')[0]; 					// 저장 체크 해제
		  	var part = $(this).find('select')[0]; 					// 구분
		  	var sentence =  $(this).find('input')[1]; 				// 문장번호
		  	var word =  $(this).find('input')[2]; 				// 단어번호
			if(sentence.value == sNum && word.value > wNum){ 	// 문장번호가 같고 현재 단어번호보다 클경우 
				part.value = val;								// 구분 셋팅
				save.checked = false;
			}
		});
	}	
	function uncheckSave(obj){
		$(obj).children('th').each(function(){
			$(this).find('input')[0].checked = false;
		});
	}
	
	function saveTagInfo(obj, idx){
		if(obj.checked == false) return;
		
		$("#tagForm"+idx).ajaxForm({
	        beforeSubmit: function(a,f,o) {
	        	/*
	        	for(var i=0; i<a.length; i++){
	        		console.log(a[i].name);
	        		console.log(a[i].value);
	        	}*/
	        },
	        success: function(data) {
				if(data.status == 'success')console.log('save succeed');
					//alert('저장되었습니다.');
				else alert('something have error');
	        }
	    }).submit();
	}
	function searchReady(obj){
		$('#search_text').val(obj.value);
	}
	
	function baseReady(obj){
		$('#base_text').val(obj.value);
	}
	
	function getKorDicResult(obj, idx, baseWord){
		obj.value = '선택';
		 $.ajax({
			   type:'post',
			   url:'/esla/tags/kordic',
			   data: ({baseWord:baseWord, idx:idx}),
			   success:function(data){
				   $('.mw_layer').addClass('open');
				   $('#layer').html(data);
			   }
		});
	}
	function saveAllTags(){
		alert('not yet.');
		/*
			$(".tagForms").ajaxForm({
		        beforeSubmit: function(a,f,o) {

		        },
		        success: function(data) {
		        }
		    }).submit();
			
			$(".tagForms > input:checkbox").each(function(){
				this.checked = true;
			});
		*/
	}
</script>	
	<table style="width: 100%;">	
		<caption>페이지 조회</caption>		
		<thead>				
			<tr>									
				<th width="55px">페이지</th>						
				<td>${article.page}</td>
				<th>작성자</th>
				<td>${article.writer}</td>
				<th>등록일</th>
				<td>${article.regdate}</td>
				<th>원본 이미지</th>			
				<td  colspan="100%">
					<c:if test="${empty article.filename}">없음</c:if>
					<c:if test="${not empty article.filename}"><a href="/esla/download/${article.idx}">${article.filename}</a></c:if>
				</td>
			</tr>
			<tr>
				<th>메타정보</th>
				<td colspan="100%">${article.title}_${article.process}_${article.grade}_${article.publisher}_${article.chapter}_${article.page}</td>
			</tr>
			<tr>
				<th>원본<br/>텍스트</th>						
				<td colspan="100%"><textarea style="width: 100%;height:130px; overflow-y: scroll;">${article.content}</textarea></td>
			</tr>
		</thead>
	</table>
	<div style="height: 500px; overflow-y : scroll; overflow-x : hidden;">
		<table id="tagsTable">	
		<caption>태그 정보(※저장시 체크박스 클릭)</caption>	
			<thead>
				<tr> 
					<th>-</th>
					<th>구분</th>
					<th>문장</th>
					<th>단어</th>
					<th>어절</th>
					<th>기본형</th>
					<th>표국대 <br/>(전문/일반)</th>
					<th>표국대 <br/>분류</th>
					<th>품사</th>
					<th>전문어구분</th>
					<th>관리</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${tags}" var="metainfo">
				<form class="tagForms" id="tagForm${metainfo.idx}" action="/esla/tags/save.json" method="post">
					<input type="hidden" name="idx" value="${metainfo.idx}" />
					<tr>
						<th title="수정시 체크바랍니다.">
							<input class="saveCheck" type="checkbox" onclick="saveTagInfo(this,${metainfo.idx})" checked="checked"/>
						</th>
						<td title="구분">
							<select name="part" onchange="setSameSentencePart(this.parentNode.parentNode, '${metainfo.sentence}', '${metainfo.word}',this.value);">
								<option value="대단원" <c:if test="${metainfo.part eq '대단원'}"> selected="selected" </c:if>>대단원</option>
								<option value="소단원" <c:if test="${metainfo.part eq '소단원'}"> selected="selected" </c:if>>소단원</option>
								<option value="페이지" <c:if test="${metainfo.part eq '페이지'}"> selected="selected" </c:if>>페이지</option>
								<option value="본문" <c:if test="${metainfo.part eq '본문'}"> selected="selected" </c:if>>본문</option>
								<option value="탐구활동" <c:if test="${metainfo.part eq '탐구활동'}"> selected="selected" </c:if>>탐구활동</option>
								<option value="문제" <c:if test="${metainfo.part eq '문제'}"> selected="selected" </c:if>>문제</option>
							</select>
							<!-- 
								<input type="text" name="part" size="4" 
								onforminput="uncheckSave(this.parentNode.parentNode);" 
								onfocus="uncheckSave(this.parentNode.parentNode);" 
								onkeyup="setSameSentenceValue(this.parentNode.parentNode, '${metainfo.sentence}', '${metainfo.word}',this.value);" value="${metainfo.part}"/>
							 -->
						
						</td>
						<td title="문장번호"><input type="text" name="sentence" size="1" value="${metainfo.sentence}"  onfocus="uncheckSave(this.parentNode.parentNode);"/></td>
						<td title="단어번호"><input type="text" name="word" size="1" value="${metainfo.word}"  onfocus="uncheckSave(this.parentNode.parentNode);"/></td>						
						<td><input type="text" name="tag" size="8" 	value="${metainfo.tag}"  onfocus="uncheckSave(this.parentNode.parentNode);baseReady(this);" onkeyup="baseReady(this);" title="어절"/></td>
						<td><input type="text" id="baseword${metainfo.idx}" name="base" size="8" value="${metainfo.base}" onfocus="uncheckSave(this.parentNode.parentNode);searchReady(this);" onkeyup="searchReady(this);" title="기본형"/> </td>
						<td>
							<input type="button" value="확인" onclick="getKorDicResult(this, ${metainfo.idx}, $('#baseword${metainfo.idx}').val())" />
							<c:if test="${metainfo.result_std_count > 0 || metainfo.result_count > 0}">
								(${metainfo.result_count}/${metainfo.result_std_count})
							</c:if> 
							<c:if test="${metainfo.result_std_count == 0 && metainfo.result_count == 0}">
								(-)
							</c:if> 
							<input type="hidden" value="${metainfo.word_no}" name="word_no" />
						</td>
						<td><input type="text" name="tech_term_name" id="tech_term_name${metainfo.idx}" size="5" value="${metainfo.tech_term_name}" onfocus="uncheckSave(this.parentNode.parentNode);"/></td>
						<td><input type="text" name="word_class" id="word_class${metainfo.idx}" size="3" value="${metainfo.word_class}" onfocus="uncheckSave(this.parentNode.parentNode);" title="품사"/></td>
						<td>
							<select onchange="uncheckSave(this.parentNode.parentNode)" name="dic_part"> 
								<option selected="selected" >없음</option>
								<option value="dic_physics_cnt" <c:if test="${metainfo.dic_physics_cnt != 0}"> selected="selected" </c:if> >물리학</option>
								<option value="dic_biology_cnt" <c:if test="${metainfo.dic_biology_cnt != 0}"> selected="selected" </c:if> >생물학</option>
								<option value="dic_earth_cnt" <c:if test="${metainfo.dic_earth_cnt != 0}"> selected="selected" </c:if> >지구과학</option>
								<option value="dic_chemistry_cnt" <c:if test="${metainfo.dic_chemistry_cnt != 0}"> selected="selected" </c:if> >화학</option>
							</select>
						</td>
						<td>
							<input type="button" onclick="deleteTagInfo('${metainfo.idx}','${metainfo.sentence}','${metainfo.word}', this.parentNode.parentNode)" value="삭제"/>
							<input type="button" onclick="createTagInfo('${article.idx}','${metainfo.sentence}',${metainfo.word}+1)" value="추가"/>
						</td>
					</tr>
				</form>
				</c:forEach>
			</tbody>
		</table>
	</div>
<br/>
<script>
	function saveToExcel(idx){
		window.open("/esla/excel/"+idx);
	}
</script>
<!-- <span class="button black small"><a href="#" onclick="saveAllTags()">모두 저장</a></span> -->
<c:if test="${sessionScope.user.rank > 10 }">
	<span class="button black small"><a href="#" onclick="saveToExcel(${article.idx})">엑셀로 저장</a></span>
	<span class="button black small"><a href="#" onclick="getTagList(${article.idx});" style="float: right;">결과보기</a></span> 
</c:if>
<span class="button black small"><a href="#" onclick="deleteArticle('${article.idx}')">페이지 삭제</a></span>
<span class="button black small" style="float: right;"><a href="#" onclick="nodeManagerAndPageList(${article.document_idx},6);" >목록으로</a></span> 