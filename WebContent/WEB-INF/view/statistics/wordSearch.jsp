<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
	var chart;
	var node = $("#tree").dynatree("getActiveNode");
	var path = "";
	while(node.parent){
		path = node.data.title +">"+ path;
		node = node.getParent();
	}
	path = path.substring(0, path.length-1);
	$('#document_text').html(path);  
	
	function searchWord(){
		var doc_idx = $('#doc_idx').val();
		var search_word = $('#search_word').val();
		
		$.ajax({
			   url:'/esla/statistics/searchword',
			   type:'post',
			   data: ({doc_idx:doc_idx, tag:search_word}),
			   success:function(data){
				   $('#searchResult').html(data); 
			   }
		});	
	}
</script>
<div>
<span class="button black large" style="float: right; margin:5px;"><a href="#" onclick="loadPageStatistics();">용례추출</a> </span>
<span class="button black large" style="float: right; margin:5px;"><a href="#" onclick="loadPageStatistics();">빈도분석</a> </span>
<table>		
	<caption>통계 - 용례추출</caption>						
	<thead>
		<tr>
			<th height="30">
				검색 범위
			</th>	
			<td colspan="100%">
				<span id="document_text"></span>
			</td>
		</tr>
		<tr>
			<th height="30">
				용어 검색
			</th>	
			<td colspan="100%">
				<input type="text" id="search_word"/>
				<span class="button black large" style="float: right; margin:5px;"><a href="#" onclick="searchWord();">검색</a> </span>
			</td>	 
		</tr>
		<tr>
			<th colspan="100%" align="center">
				검색결과
			</th>	
		</tr>
		<tr>
			<td id="searchResult" colspan="100%">
			</td>
		</tr>
		<tr>
			<th colspan="100%" align="center">
				분석결과
			</th>	
		</tr>
		<tr>
			<td id="infomationResult" colspan="100%"></td>
		</tr>
	</thead>
</table>
</div>
