<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="<c:url value="/resource/script/common_utils.js"/>"></script>
<script type="text/javascript">
	function openContent(idx){
		 //$('.mw_layer').addClass('open');
		 $.ajax({
			   type:'post',
			   url:'/esla/count',
			   data: ({idx:idx}),
			   success:function(data){
				 $('#content').html(data);
			   }
		});
	}
	
	function loadNextPage(){
		var page = $('#page').val();
		page = parseInt(page);
		page += 10;
		 $.ajax({
			   type:'post',
			   url:'/esla/ajaxList',
			   data: ({page:page}),
			   success:function(data){
				   if(data.trim() == "")
					   alert("���̻� �������� �����ϴ�.");
				   else{
					   $('table').append(data);
					   $('#page').val(page);
				   }
			   }
		});
	}
	
	function loadWriteForm(){
		var doc_idx = $('#doc_idx').val();
		 $.ajax({
			   type:'post',
			   url:'/esla/write',
			   data: ({doc_idx:doc_idx}),
			   success:function(data){
				   $('.mw_layer').addClass('open');
				   $('#layer').html(data);
			   }
		});
	}
	
</script>
<table>		
	<caption onclick="location.href='/esla/'" style="cursor:pointer">���¼� �м���</caption>						
	<thead>
		<tr>	
			<th width="10%">������</th>
			<th width="20%">�ۼ���</th>			
			<th width="20%">����</th>			
			<th width="20%">�ܿ�</th>
			<th width="10%">�г�</th>
			<th width="20%">���ǻ�</th>
		</tr>
	</thead> 
		<c:forEach items="${articleList}" var="article">
			<tr style="cursor: pointer;" onclick="openContent('${article.idx}')">
				<td>${article.page}</td> 
				<td>${article.writer}</td>
				<td>${article.title}</td>
				<td>${article.chapter}</td>
				<td>${article.grade}</td>
				<td>${article.publisher}</td>  
			</tr>
		</c:forEach>
</table>  
<br/>
<input type="hidden" name="page" id="page" value="${page}">
<span class="button black large" style="float: right;"><a href="#" onclick="loadWriteForm();" >������ �Է�</a></span> 
<!-- <span style="cursor: pointer;float: right;" onclick="loadNextPage()">������</span> -->
