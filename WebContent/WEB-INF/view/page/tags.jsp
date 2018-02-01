<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<table id="tagsTable">	
	<caption>�±� ����</caption>	
		<thead>
			<tr> 
				<th>�±׹�ȣ</th>					
				<th colspan="100%">����±�����</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${tags}" var="metainfo">
				<tr>
					<th title="������ üũ�ٶ��ϴ�.">
						${metainfo.idx}
					</th>
					<td title="����±�����">
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
							<c:when test="${metainfo.dic_physics_cnt > 0}">_�����п���� </c:when> 
							<c:when test="${metainfo.dic_biology_cnt > 0}">_�����п����</c:when>
							<c:when test="${metainfo.dic_earth_cnt > 0}">_�������п����</c:when>
							<c:when test="${metainfo.dic_chemistry_cnt > 0}">_ȭ�п����</c:when>
						</c:choose>
						<c:if test="${metainfo.word_no != 0}">
						, ǥ�ر�������_
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
<span class="button black small"><a href="#" onclick="saveToExcel(${article.idx})">������ ����</a></span>
<span class="button black small" style="float: right;"><a href="#" onclick="openContent(${article.idx});" >�ڷ�</a></span> 