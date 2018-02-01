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
			<th>�±׹�ȣ</th>					
			<th>����</th>
			<th>��������</th>
			<th>�г�</th>
			<th>���ǻ�</th>
			<th>�ܿ�</th>
			<th>������</th>
			<th>�����ȣ</th>
			<th>�ܾ��ȣ</th>
			<th>ǰ��</th>
			<th>����</th>
			<th>����</th>
			<th>�⺻��</th>
			<th>���������</th>
			<th>ǥ�ر�������</th>
			<th>ǥ���� �з�</th>
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
							<c:when test="${metainfo.dic_physics_cnt > 0}">�����п���� </c:when> 
							<c:when test="${metainfo.dic_biology_cnt > 0}">�����п����</c:when>
							<c:when test="${metainfo.dic_earth_cnt > 0}">�������п����</c:when>
							<c:when test="${metainfo.dic_chemistry_cnt > 0}">ȭ�п����</c:when>
						</c:choose>
					</td>
					<td><c:if test="${metainfo.word_no != 0}">
						ǥ�ر�������
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