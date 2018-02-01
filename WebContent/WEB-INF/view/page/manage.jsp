<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	function modifyMetaInfomation(par_idx){
		var meta_idx = $('#meta_idx').val();
		var meta_val = $('#meta_val').val();
		
		 $.ajax({
			   type:'post',
			   url:'/esla/tree/update.json',
			   data: ({idx:meta_idx, val : meta_val }),
			   success:function(data){
				   if(data.status == "success"){
					   $("#tree").dynatree("getActiveNode").getParent().reloadChildren();
					   alert("수정되었습니다.");
					   $("#tree").dynatree("getTree").getNodeByKey(par_idx).activate();
				   }else
					   alert("권한이 부족하여 수정할 수 없습니다.");
			   }
		});
	}
	
	function modifyMetaInfoOrder(par_idx){
		var meta_idx = $('#meta_idx').val();
		var meta_order = $('#meta_order').val();
		
		 $.ajax({
			   type:'post',
			   url:'/esla/tree/order.json',
			   data: ({idx:meta_idx, class_order : meta_order }),
			   success:function(data){
				   if(data.status == "success"){
					   $("#tree").dynatree("getActiveNode").getParent().reloadChildren();
					   alert("수정되었습니다.");
					   $("#tree").dynatree("getTree").getNodeByKey(par_idx).activate();
				   }else
					   alert("권한이 부족하여 수정할 수 없습니다.");
			   }
		});	
		
	}
	function deleteMetaInfomation(par_idx){
		if(!confirm("하위 노드 및 태그들이 전부 삭제됩니다.\n삭제하시겠습니까?")) return;
		
		var meta_idx = $('#meta_idx').val();
		
		 $.ajax({
			   type:'post',
			   url:'/esla/tree/delete.json',
			   data: ({idx:meta_idx}),
			   success:function(data){
				   if(data.status == "success"){
					   alert("삭제되었습니다.");
					   
					   if($("#tree").dynatree("getActiveNode").getParent().data.key == 0){
						   location.reload();
						   //$("#tree").dynatree("getTree").reload();
					   }else{
					   	$("#tree").dynatree("getActiveNode").getParent().reloadChildren();
					   	$("#tree").dynatree("getTree").getNodeByKey(par_idx).activate();
					   }
				   }else
					   alert("권한이 부족하여 삭제할 수 없습니다.");
			   }
		});		
	}
	
	function orderUp(meta_idx, class_order){
		var node = $("#tree").dynatree("getTree").getNodeByKey(meta_idx);	// 선택한 노드의 아래 노드 선택
		
		if(node.getPrevSibling() == null) alert("상위 노드입니다.");
		else {
			swapNodeOrder(meta_idx, class_order, node.getPrevSibling().data.key, node.getParent().data.key);
		}
	}
	
	function orderDown(meta_idx, class_order){
		var node = $("#tree").dynatree("getTree").getNodeByKey(meta_idx);	// 선택한 노드의 아래 노드 선택
		if(node.getNextSibling() == null) alert("마지막 노드입니다.");
		else {
			swapNodeOrder(meta_idx, class_order, node.getNextSibling().data.key, node.getParent().data.key);
		}		
	}
	
	function swapNodeOrder(idx, class_order, swap_idx, par_idx){
		 $.ajax({
			   type:'post',
			   url:'/esla/tree/swapOrder.json',
			   data: ({idx:idx, class_order:class_order, swap_idx:swap_idx}),
			   success:function(data){
				   if(data.status == "success"){
					   $("#tree").dynatree("getActiveNode").getParent().reloadChildren();
					   alert("수정되었습니다.");
					   $("#tree").dynatree("getTree").getNodeByKey(par_idx).activate();
				   }else
					   alert("권한이 부족하여 수정할 수 없습니다.");
			   }
		});
	}
	
	function addChildInputbox(valueCnt){
		$('#addChildTD').empty();
		for(var i=0; i<valueCnt; i++)
			$('#addChildTD').append('<input type="text" name="val"  class="val" id="val" /><br/>');
	}
	
	function addChildNode(name, par_idx){
		
		var addCount = $('.val').length;
		
		for(var i=0; i<addCount; i++){
			var val = $('.val')[i].value;
			if($.trim(val) == "") { alert('값을 입력해주세요'); $('.val')[i].focus(); return;}
			$.ajax({
				   type:'post',
				   url:'/esla/tree/addnode.json',
				   data: ({par_idx:par_idx, val : val, name:name }),
				   success:function(data){
					   if(data.status != "success"){
						   alert("입력실패. 관리자만 가능합니다.");
					   }
				   }
			});
		}
		alert("입력중입니다.");
		if($("#tree").dynatree("getActiveNode").data.key == 0){
			$("#tree").dynatree("getTree").reload();
		}else
			$("#tree").dynatree("getActiveNode").reloadChildren();
	}
</script>
<c:if test="${sessionScope.user.rank > 10 }">
	<table>		
		<caption>메타정보관리</caption>						
		<thead>
			<tr>	
				<th>
					정보 수정
					(${metaMap[node.name]})
				</th>	
				<td>
					<input type="text" id="meta_val" value="${node.val}" />
					<span class="button black large"><input type="button" value="수정" onclick="modifyMetaInfomation('${node.par_idx}')"/></span>
				</td>
				<td>
					<span class="button black large"><input type="button" value="삭제" onclick="deleteMetaInfomation('${node.par_idx}')"/></span>
				</td>	 
			</tr>
			<tr>
				<th>
					순서 변경 (순위)
				</th>
				<td>
					<input type="text" id="meta_order" value="${node.class_order}" />
					<span class="button black large"><input type="button" value="수정" onclick="modifyMetaInfoOrder('${node.par_idx}')"/></span>
				</td>
				<td>
					<span class="button black large"><input type="button" value="위로" onclick="orderUp('${node.idx}','${node.class_order}')"/></span>
					<span class="button black large"><input type="button" value="아래로" onclick="orderDown('${node.idx}','${node.class_order}')"/></span>
				</td>
			</tr>
			<c:if test="${node.is_leaf eq false }">
				<tr>
					<th>
						하위 추가
						(
							<c:if test="${level eq 1}">교육과정</c:if>
							<c:if test="${level eq 2}">학년</c:if>
							<c:if test="${level eq 3}">출판사</c:if>
							<c:if test="${level eq 4}">과목</c:if>
							<c:if test="${level eq 5}">단원</c:if> 
						)
					</th>
					<td id="addChildTD">
						<input type="text" name="val" class="val" id="val" /><br/>
					</td>
					<td>
						<span class="button black large">
							<c:if test="${level eq 1}"><a href="#" onclick="addChildNode('process', '${node.idx}' )">추가</a></c:if>
							<c:if test="${level eq 2}"><a href="#" onclick="addChildNode('grade', '${node.idx}' )">추가</a></c:if>
							<c:if test="${level eq 3}"><a href="#" onclick="addChildNode('publisher', '${node.idx}' )">추가</a></c:if>
							<c:if test="${level eq 4}"><a href="#" onclick="addChildNode('title', '${node.idx}' )">추가</a></c:if>
							<c:if test="${level eq 5}"><a href="#" onclick="addChildNode('chapter', '${node.idx}' )">추가</a></c:if> 
						</span>
						<select onchange="addChildInputbox(this.value)"> 
							<option selected="selected" >1</option>
							<c:forEach begin="1" end="19" varStatus="i"><option>${i.count+1}</option></c:forEach>
						</select>
						&nbsp;&nbsp;(일괄입력)
					</td>
				</tr>
			</c:if>
		</thead> 
	</table>
	<br/>
	<span class="button black large"><a href="#" onclick="loadPageStatistics()">빈도 분석</a></span>
	
		<span class="button black large"><a href="#" onclick="saveToExcelAsChapter()">파일 저장</a></span>
	<br/><br/>
	<input type="hidden" id="meta_idx" value="${node.idx}" />
</c:if>

<div id="pageList">
</div>
