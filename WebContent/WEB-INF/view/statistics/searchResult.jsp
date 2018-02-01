<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>

	function searchTagInfomation(tag_idx){
		$.ajax({
			   url:'/esla/statistics/tag/'+tag_idx,
			   success:function(data){
				   $('#infomationResult').html(data); 
			   }
		});	
	}
</script>
<table border=1>	
	<thead>
		<tr> 
			<th>태그번호</th>					
			<th>과목</th>
			<th>교육과정</th>
			<th>학년</th>
			<th>출판사</th>
			<th>단원</th>
			<th>페이지</th>
			<th>문장번호</th>
			<th>단어번호</th>
			<th>품사</th>
			<th>구분</th>
			<th>어절</th>
			<th>기본형</th>
			<th>전문용어집</th>
			<th>표준국어대사전</th>
		</tr>
	</thead>
	<tbody>
			<c:forEach items="${tags}" var="metainfo">
				<tr style="cursor:pointer;" onclick="searchTagInfomation(${metainfo.idx});">
					<td>${metainfo.idx}</td>
					<td>${metainfo.title}</td>
					<td>${metainfo.process}</td>
					<td>${metainfo.grade}</td>
					<td>${metainfo.publisher}</td>
					<td>${metainfo.chapter}</td>
					<td>${metainfo.page}</td>
					<td>${metainfo.sentence}</td>
					<td>${metainfo.word}</td>
					<td>${metainfo.word_class}</td>
					<td>${metainfo.part}</td>
					<td>${metainfo.tag}</td>
					<td>${metainfo.base}</td>
					<td><c:choose>
							<c:when test="${metainfo.dic_physics_cnt > 0}">물리학용어집 </c:when> 
							<c:when test="${metainfo.dic_biology_cnt > 0}">생물학용어집</c:when>
							<c:when test="${metainfo.dic_earth_cnt > 0}">지구과학용어집</c:when>
							<c:when test="${metainfo.dic_chemistry_cnt > 0}">화학용어집</c:when>
						</c:choose>
					</td>
					<td><c:if test="${metainfo.word_no != 0}">
						표준국어대사전
						</c:if>
					</td>
				</tr>
			</c:forEach>
	</tbody>
</table>