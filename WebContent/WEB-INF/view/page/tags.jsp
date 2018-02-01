<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<table id="tagsTable">	
	<caption>태그 정보</caption>	
		<thead>
			<tr> 
				<th>태그번호</th>					
				<th colspan="100%">결과태그정보</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${tags}" var="metainfo">
				<tr>
					<th title="수정시 체크바랍니다.">
						${metainfo.idx}
					</th>
					<td title="결과태그정보">
					${article.title}
					_${article.process}
					_${article.grade}
					_${article.publisher}
					_${article.chapter}
					_${article.page}
					_${metainfo.sentence}
					_${metainfo.word}
					_${metainfo.word_class}
					_${metainfo.part}
					_${metainfo.tag}
					_${metainfo.base}
						<c:choose>
							<c:when test="${metainfo.dic_physics_cnt > 0}">_물리학용어집 </c:when> 
							<c:when test="${metainfo.dic_biology_cnt > 0}">_생물학용어집</c:when>
							<c:when test="${metainfo.dic_earth_cnt > 0}">_지구과학용어집</c:when>
							<c:when test="${metainfo.dic_chemistry_cnt > 0}">_화학용어집</c:when>
						</c:choose>
						<c:if test="${metainfo.word_no != 0}">
						, 표준국어대사전_
						${metainfo.tech_term_name}
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
<br/>
<script>
	function saveToExcel(idx){
		window.open("/esla/excel/"+idx);
	}
</script>
<span class="button black small"><a href="#" onclick="saveToExcel(${article.idx})">엑셀로 저장</a></span>
<span class="button black small" style="float: right;"><a href="#" onclick="openContent(${article.idx});" >뒤로</a></span> 