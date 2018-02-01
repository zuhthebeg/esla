<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table border=1>	
	<thead>
		<tr> 
			<th align="center" width="30%">용례(문장전체)</th>
			<th align="center" width="30%">주변어휘(좌우5어절)</th>
			<th align="center" width="30%">빈도정보</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>
				<c:forEach items="${sentence}" var="tag">
				${tag} 
				</c:forEach>
			</td>
			<td>
				<c:forEach items="${neighbor}" var="tag">
				${tag},
				</c:forEach>
			</td>
			<td>
				<c:forEach items="${statResult}" var="tag">
					${tag.path} >> ${tag.cnt }회 <br/>
				</c:forEach>
			</td>
		</tr>
	</tbody>
</table>