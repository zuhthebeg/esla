<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	
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


</script>
<div>
<span class="button black large" style="float: right; margin:5px;"><a href="#" onclick="loadWordSearch();">용례추출</a> </span>
<span class="button black large" style="float: right; margin:5px;"><a href="#" onclick="loadPageStatistics();">빈도분석</a> </span>
<table>		
	<caption>통계 - 빈도분석</caption>						
	<thead>
<%-- 		<tr>
			<th height="30">
				통계 범위
			</th>	
			<td colspan="100%">
				교육과정<select><c:forEach items="${processList}" var="process"><option value="${process.idx}">${process.val}</option></c:forEach></select>
			</td>
		</tr> --%>
		<tr>
			<th height="30">
				통계 범위
			</th>	
			<td colspan="100%">
				<span id="document_text"></span>
			</td>
		</tr>
		<tr>
			<th height="30">
				분석 범주
			</th>	
			<td colspan="100%">
				<%-- <input type="checkbox" id="isAllWord" onclick="changePart($('select[name=part]').val())" <c:if test="${isAllWord eq true}"> checked="checked"  </c:if> >전문어만 --%>
				
			<%-- 	<select name="part" id="part" onchange="changePart(this.value)">
					<option value="" <c:if test="${part eq null}"> selected="selected" </c:if>>전체</option>
					<option value="대단원" <c:if test="${part eq '대단원'}"> selected="selected" </c:if>>대단원</option>
					<option value="소단원" <c:if test="${part eq '소단원'}"> selected="selected" </c:if>>소단원</option>
					<option value="페이지" <c:if test="${part eq '페이지'}"> selected="selected" </c:if>>페이지</option>
					<option value="본문" <c:if test="${part eq '본문'}"> selected="selected" </c:if>>본문</option>
					<option value="탐구활동" <c:if test="${part eq '탐구활동'}"> selected="selected" </c:if>>탐구활동</option>
					<option value="문제" <c:if test="${part eq '문제'}"> selected="selected" </c:if>>문제</option>
				</select> 
				 --%>
					<input type="checkbox" class="part" id="bigPart" onclick="selectPart()" value="대단원" <c:if test="${bigPart eq '대단원'}"> checked="checked" </c:if> />대단원
					<input type="checkbox" class="part" id="smallPart" onclick="selectPart()" value="소단원" <c:if test="${smallPart eq '소단원'}"> checked="checked" </c:if> />소단원
					<input type="checkbox" class="part" id="pagePart" onclick="selectPart()" value="페이지" <c:if test="${pagePart eq '페이지'}"> checked="checked" </c:if> />페이지
					<input type="checkbox" class="part" id="contentPart" onclick="selectPart()" value="본문" <c:if test="${contentPart eq '본문'}"> checked="checked" </c:if> />본문
					<input type="checkbox" class="part" id="actionPart" onclick="selectPart()" value="탐구활동" <c:if test="${actionPart eq '탐구활동'}"> checked="checked" </c:if> />탐구활동
					<input type="checkbox" class="part" id="problemPart" onclick="selectPart()" value="문제" <c:if test="${problemPart eq '문제'}"> checked="checked" </c:if> />문제
			</td>	 
		</tr>
		<tr>
			<th colspan="100%" align="center">
				분석 내용
			</th>	
		</tr>
		<tr>
			<th align="center">어휘수</th>
			<th align="center">과학용어수(표준국어대사전)</th>
			<th align="center">과학용어수(전문용어집)</th>
		</tr>
		<tr id="word_checkbox">
			<td>
			연어휘수: ${totalWordCount}개
			<br/>
			개별어휘수: ${distinctWordCount}개<br/>
			<c:if test="${totalWordCount != 0}"> 평균빈도수: <fmt:formatNumber value="${totalWordCount/distinctWordCount}" pattern=".00"/>개</c:if><br/>
			</td>
			<td>
				<input type="checkbox" class="word_checkbox" id="st_phy" value="1"/>물: 
				연어휘수 ${phyStandardWordCountAll }개 / 개별어휘수 ${phyStandardWordCount }개
				<c:if test="${phyStandardWordCountAll != 0 }"> / 평균 빈도수 <fmt:formatNumber value="${phyStandardWordCountAll/phyStandardWordCount }" pattern=".00"/>개</c:if><br/>
				<input type="checkbox" class="word_checkbox" id="st_che" value="1"/>화: 
				연어휘수 ${cheStandardWordCountAll }개 / 개별어휘수 ${cheStandardWordCount }개
				<c:if test="${cheStandardWordCountAll != 0 }"> / 평균 빈도수 <fmt:formatNumber value="${cheStandardWordCountAll/cheStandardWordCount }" pattern=".00"/>개</c:if><br/>
				<input type="checkbox" class="word_checkbox" id="st_bio" value="1"/>생: 
				연어휘수 ${bioStandardWordCountAll }개 / 개별어휘수 ${bioStandardWordCount }개
				<c:if test="${bioStandardWordCountAll != 0 }"> / 평균 빈도수 <fmt:formatNumber value="${bioStandardWordCountAll/bioStandardWordCount }" pattern=".00"/>개</c:if><br/>
				<input type="checkbox" class="word_checkbox" id="st_ear" value="1"/>지: 
				연어휘수 ${earStandardWordCountAll }개 / 개별어휘수 ${earStandardWordCount }개
				<c:if test="${earStandardWordCountAll != 0 }"> / 평균 빈도수 <fmt:formatNumber value="${earStandardWordCountAll/earStandardWordCount }" pattern=".00"/>개</c:if><br/>
			</td>
			<td>
				<input type="checkbox" class="word_checkbox" id="ex_phy" value="1"/>물: 
				연어휘수 ${phyWordCountAll }개 / 개별어휘수 ${phyWordCount }개
				<c:if test="${phyWordCountAll != 0 }"> / 평균 빈도수 <fmt:formatNumber value="${phyWordCountAll/phyWordCount }" pattern=".00" />개</c:if><br/>
				<input type="checkbox" class="word_checkbox" id="ex_che" value="1"/>화: 
				연어휘수 ${cheWordCountAll }개 / 개별어휘수 ${cheWordCount }개
				<c:if test="${cheWordCountAll != 0 }"> / 평균 빈도수 <fmt:formatNumber value="${cheWordCountAll/cheWordCount }" pattern=".00"/>개</c:if><br/>
				<input type="checkbox" class="word_checkbox" id="ex_bio" value="1"/>생: 
				연어휘수 ${bioWordCountAll }개 / 개별어휘수 ${bioWordCount}개
				<c:if test="${bioWordCountAll != 0 }"> / 평균 빈도수 <fmt:formatNumber value="${bioWordCountAll/bioWordCount }" pattern=".00"/>개</c:if><br/>
				<input type="checkbox" class="word_checkbox" id="ex_ear" value="1"/>지: 
				연어휘수 ${earWordCountAll }개 / 개별어휘수 ${earWordCount }개
				<c:if test="${earWordCountAll != 0 }"> / 평균 빈도수 <fmt:formatNumber value="${earWordCountAll/earWordCount }" pattern=".00"/>개</c:if><br/>
			</td>
		</tr>
	</thead>
</table>
<span class="button black large" style="float: right; margin:5px;"><a href="#" onclick="saveToExcelByCondition();">용어추출</a> </span>
</div>
<%-- <div id="container" style="min-width: 400px; min-height : 400px; height: ${tagSize}; margin: 0 auto;"></div> --%>

<!-- <span class="button black large" style="float: right;"><a href="#" onclick="closeContent();">닫기</a> </span> -->
