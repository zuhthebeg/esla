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
		 if(confirm("�����Ͻðڽ��ϱ�?")){
			 $.ajax({
				   type:'post',
				   url:'/esla/delete.json',
				   data: ({idx:idx}),
				   success:function(data){
					   console.log(data);
					  if(data.status == "success"){
						  alert("�����Ǿ����ϴ�.");
						  nodeManagerAndPageList('${article.document_idx}',6);
					  }
				   }
			});
		}
	}
	function deleteTagInfo(idx, sNum, wNum, obj){
		if(!confirm('�����Ͻðڽ��ϱ�?')) return false;
		 $.ajax({
			   type:'post',
			   url:'/esla/tags/delete.json',
			   data: ({idx:idx}),
			   success:function(data){
				  if(data.status == "success"){
					alert("�����Ǿ����ϴ�.");		
					obj.parentNode.removeChild(obj);
					$('#tagsTable > tbody').children('tr').each(function(){ 
					  	sentence =  $(this).find('input')[1]; 				// �����ȣ
					    word = $(this).find('input')[2]; 					//	�ܾ��ȣ
						if(sentence.value == sNum && word.value > wNum){ 	// �����ȣ�� ���� �ܾ��ȣ�� ������ ��ȣ���� ū��츸
							word.value = word.value -1;						// �ܾ��ȣ -1������
						}
					});
				  }
			   }
		});
	}
	
	function createTagInfo(article_idx, sNum, wNum){
		if(!confirm('������ �±׸� �߰��Ͻðڽ��ϱ�?')) return false;
		
		 $.ajax({
			   type:'post',
			   url:'/esla/tags/insert.json',
			   data: ({idx:article_idx, sentence : sNum, word : wNum }),
			   success:function(data){
				  if(data.status == "success"){
					alert("�߰��Ǿ����ϴ�.");		
					openContent(article_idx);
				  }
			   }
		});
	}
/* 	function setSameSentenceValue(obj, sNum, wNum, val){
		$('#tagsTable > tbody').children('tr').each(function(){ 
			var save = $(this).find('input')[0]; 					// ���� üũ ����
		  	var part = $(this).find('input')[1]; 					// ����
		  	var sentence =  $(this).find('input')[2]; 				// �����ȣ
		  	var word =  $(this).find('input')[3]; 				// �ܾ��ȣ
			if(sentence.value == sNum && word.value > wNum){ 	// �����ȣ�� ���� ���� �ܾ��ȣ���� Ŭ��� 
				part.value = val;								// ���� ����
				save.checked = false;
			}
		});
	} */
	function setSameSentencePart(obj, sNum, wNum, val){
		uncheckSave(obj);
		
		$('#tagsTable > tbody').children('tr').each(function(){ 
			var save = $(this).find('input')[0]; 					// ���� üũ ����
		  	var part = $(this).find('select')[0]; 					// ����
		  	var sentence =  $(this).find('input')[1]; 				// �����ȣ
		  	var word =  $(this).find('input')[2]; 				// �ܾ��ȣ
			if(sentence.value == sNum && word.value > wNum){ 	// �����ȣ�� ���� ���� �ܾ��ȣ���� Ŭ��� 
				part.value = val;								// ���� ����
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
					//alert('����Ǿ����ϴ�.');
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
		obj.value = '����';
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
		<caption>������ ��ȸ</caption>		
		<thead>				
			<tr>									
				<th width="55px">������</th>						
				<td>${article.page}</td>
				<th>�ۼ���</th>
				<td>${article.writer}</td>
				<th>�����</th>
				<td>${article.regdate}</td>
				<th>���� �̹���</th>			
				<td  colspan="100%">
					<c:if test="${empty article.filename}">����</c:if>
					<c:if test="${not empty article.filename}"><a href="/esla/download/${article.idx}">${article.filename}</a></c:if>
				</td>
			</tr>
			<tr>
				<th>��Ÿ����</th>
				<td colspan="100%">${article.title}_${article.process}_${article.grade}_${article.publisher}_${article.chapter}_${article.page}</td>
			</tr>
			<tr>
				<th>����<br/>�ؽ�Ʈ</th>						
				<td colspan="100%"><textarea style="width: 100%;height:130px; overflow-y: scroll;">${article.content}</textarea></td>
			</tr>
		</thead>
	</table>
	<div style="height: 500px; overflow-y : scroll; overflow-x : hidden;">
		<table id="tagsTable">	
		<caption>�±� ����(������� üũ�ڽ� Ŭ��)</caption>	
			<thead>
				<tr> 
					<th>-</th>
					<th>����</th>
					<th>����</th>
					<th>�ܾ�</th>
					<th>����</th>
					<th>�⺻��</th>
					<th>ǥ���� <br/>(����/�Ϲ�)</th>
					<th>ǥ���� <br/>�з�</th>
					<th>ǰ��</th>
					<th>�������</th>
					<th>����</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${tags}" var="metainfo">
				<form class="tagForms" id="tagForm${metainfo.idx}" action="/esla/tags/save.json" method="post">
					<input type="hidden" name="idx" value="${metainfo.idx}" />
					<tr>
						<th title="������ üũ�ٶ��ϴ�.">
							<input class="saveCheck" type="checkbox" onclick="saveTagInfo(this,${metainfo.idx})" checked="checked"/>
						</th>
						<td title="����">
							<select name="part" onchange="setSameSentencePart(this.parentNode.parentNode, '${metainfo.sentence}', '${metainfo.word}',this.value);">
								<option value="��ܿ�" <c:if test="${metainfo.part eq '��ܿ�'}"> selected="selected" </c:if>>��ܿ�</option>
								<option value="�Ҵܿ�" <c:if test="${metainfo.part eq '�Ҵܿ�'}"> selected="selected" </c:if>>�Ҵܿ�</option>
								<option value="������" <c:if test="${metainfo.part eq '������'}"> selected="selected" </c:if>>������</option>
								<option value="����" <c:if test="${metainfo.part eq '����'}"> selected="selected" </c:if>>����</option>
								<option value="Ž��Ȱ��" <c:if test="${metainfo.part eq 'Ž��Ȱ��'}"> selected="selected" </c:if>>Ž��Ȱ��</option>
								<option value="����" <c:if test="${metainfo.part eq '����'}"> selected="selected" </c:if>>����</option>
							</select>
							<!-- 
								<input type="text" name="part" size="4" 
								onforminput="uncheckSave(this.parentNode.parentNode);" 
								onfocus="uncheckSave(this.parentNode.parentNode);" 
								onkeyup="setSameSentenceValue(this.parentNode.parentNode, '${metainfo.sentence}', '${metainfo.word}',this.value);" value="${metainfo.part}"/>
							 -->
						
						</td>
						<td title="�����ȣ"><input type="text" name="sentence" size="1" value="${metainfo.sentence}"  onfocus="uncheckSave(this.parentNode.parentNode);"/></td>
						<td title="�ܾ��ȣ"><input type="text" name="word" size="1" value="${metainfo.word}"  onfocus="uncheckSave(this.parentNode.parentNode);"/></td>						
						<td><input type="text" name="tag" size="8" 	value="${metainfo.tag}"  onfocus="uncheckSave(this.parentNode.parentNode);baseReady(this);" onkeyup="baseReady(this);" title="����"/></td>
						<td><input type="text" id="baseword${metainfo.idx}" name="base" size="8" value="${metainfo.base}" onfocus="uncheckSave(this.parentNode.parentNode);searchReady(this);" onkeyup="searchReady(this);" title="�⺻��"/> </td>
						<td>
							<input type="button" value="Ȯ��" onclick="getKorDicResult(this, ${metainfo.idx}, $('#baseword${metainfo.idx}').val())" />
							<c:if test="${metainfo.result_std_count > 0 || metainfo.result_count > 0}">
								(${metainfo.result_count}/${metainfo.result_std_count})
							</c:if> 
							<c:if test="${metainfo.result_std_count == 0 && metainfo.result_count == 0}">
								(-)
							</c:if> 
							<input type="hidden" value="${metainfo.word_no}" name="word_no" />
						</td>
						<td><input type="text" name="tech_term_name" id="tech_term_name${metainfo.idx}" size="5" value="${metainfo.tech_term_name}" onfocus="uncheckSave(this.parentNode.parentNode);"/></td>
						<td><input type="text" name="word_class" id="word_class${metainfo.idx}" size="3" value="${metainfo.word_class}" onfocus="uncheckSave(this.parentNode.parentNode);" title="ǰ��"/></td>
						<td>
							<select onchange="uncheckSave(this.parentNode.parentNode)" name="dic_part"> 
								<option selected="selected" >����</option>
								<option value="dic_physics_cnt" <c:if test="${metainfo.dic_physics_cnt != 0}"> selected="selected" </c:if> >������</option>
								<option value="dic_biology_cnt" <c:if test="${metainfo.dic_biology_cnt != 0}"> selected="selected" </c:if> >������</option>
								<option value="dic_earth_cnt" <c:if test="${metainfo.dic_earth_cnt != 0}"> selected="selected" </c:if> >��������</option>
								<option value="dic_chemistry_cnt" <c:if test="${metainfo.dic_chemistry_cnt != 0}"> selected="selected" </c:if> >ȭ��</option>
							</select>
						</td>
						<td>
							<input type="button" onclick="deleteTagInfo('${metainfo.idx}','${metainfo.sentence}','${metainfo.word}', this.parentNode.parentNode)" value="����"/>
							<input type="button" onclick="createTagInfo('${article.idx}','${metainfo.sentence}',${metainfo.word}+1)" value="�߰�"/>
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
<!-- <span class="button black small"><a href="#" onclick="saveAllTags()">��� ����</a></span> -->
<c:if test="${sessionScope.user.rank > 10 }">
	<span class="button black small"><a href="#" onclick="saveToExcel(${article.idx})">������ ����</a></span>
	<span class="button black small"><a href="#" onclick="getTagList(${article.idx});" style="float: right;">�������</a></span> 
</c:if>
<span class="button black small"><a href="#" onclick="deleteArticle('${article.idx}')">������ ����</a></span>
<span class="button black small" style="float: right;"><a href="#" onclick="nodeManagerAndPageList(${article.document_idx},6);" >�������</a></span> 