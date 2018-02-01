<%@ page contentType="application/vnd.ms-excel; charset=EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String FileName = "download_excel.xls";
    
    response.setContentType("application/vnd.ms-excel; charset=euc-kr");
    response.setHeader("Content-Disposition", "filename="+FileName);
    response.setHeader("Content-Description", "JSP Generated Data");
    
    String clientBrowser = request.getHeader("User-Agent");    
    
    if(clientBrowser.indexOf("MSIE 5.5") != -1 || clientBrowser.indexOf("MSIE 6.0") != -1) {
        response.setHeader("Content-Disposition", "attachment;filename="+FileName);
    }else {
        response.setHeader("Content-Disposition", "filename="+FileName);
    }
%>
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
			<th>표국대 분류</th>
		</tr>
	</thead>
	<tbody>
			<c:forEach items="${tags}" var="metainfo">
				<tr>
					<td>${metainfo.idx}</td>
					<td>${article.title}</td>
					<td>${article.process}</td>
					<td>${article.grade}</td>
					<td>${article.publisher}</td>
					<td>${article.chapter}</td>
					<td>${article.page}</td>
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
					<td><c:if test="${metainfo.word_no != 0}">
						${metainfo.tech_term_name}
						</c:if>
					</td>
				</tr>
			</c:forEach>
	</tbody>
</table>