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

   $(document).ready(function() {
   
       var colors = Highcharts.getOptions().colors,
           categories = ['기타', '물리용어집', '화학용어집', '생물용어집', '지구과학용어집'],
           name = '전문용어집',
           data = [{
                   y: 50.11,
                   color: colors[0],
                   drilldown: {
                       name: categories[0],
                       categories: ['탐구활동', '심화학습', '연습문제', '본문'],
                       data: [10.85, 7.35, 28.06, 2.81],
                       color: colors[0]
                   }
               }, {
                   y: 21.63,
                   color: colors[1],
                   drilldown: {
                       name: categories[1],
                       categories: ['본문', '탐구활동', '심화학습', '연습문제', '기타'],
                       data: [0.20, 0.83, 1.58, 13.12, 5.43],
                       color: colors[1]
                   }
               }, {
                   y: 11.94,
                   color: colors[2],
                   drilldown: {
                       name: categories[2],
                       categories: ['본문', '탐구활동', '심화학습', '연습문제', '기타'],
                       data: [1.03, 0.19, 9.84, 0.36, 0.32],
                       color: colors[2]
                   }
               }, {
                   y: 7.15,
                   color: colors[3],
                   drilldown: {
                       name: categories[3],
                       categories: ['본문', '탐구활동', '심화학습', '연습문제', '기타'],
                       data: [4.55, 1.42, 0.23, 0.21, 0.53],
                       color: colors[3]
                   }
               }, {
                   y: 7.14,
                   color: colors[4],
                   drilldown: {
                       name: categories[4],
                       categories: ['본문', '탐구활동', '심화학습'],
                       data: [ 5.12, 0.37, 1.65],
                       color: colors[4]
                   }
               }];
   
   
       // Build the data arrays
       var browserData = [];
       var versionsData = [];
       for (var i = 0; i < data.length; i++) {
   
           // add browser data
           browserData.push({
               name: categories[i],
               y: data[i].y,
               color: data[i].color
           });
           // add version data
           for (var j = 0; j < data[i].drilldown.data.length; j++) {
               var brightness = 0.2 - (j / data[i].drilldown.data.length) / 5 ;
               versionsData.push({
                   name: data[i].drilldown.categories[j],
                   y: data[i].drilldown.data[j],
                   color: Highcharts.Color(data[i].color).brighten(brightness).get()
               });
           }
       }
   
       // Create the chart
       chart = new Highcharts.Chart({
           chart: {
               renderTo: 'container',
               type: 'pie'
           },
           title: {
               text: '전문용어집별 전문어 비율'
           },
           subtitle: {
               text: '세부 구분'
           },
           yAxis: {
               title: {
                   text: 'Total percent market share'
               }
           },
           plotOptions: {
               pie: {
                   shadow: false
               }
           },
           tooltip: {
       	    valueSuffix: '%'
           },
           series: [{
               name: '전문용어집',
               data: browserData,
               size: '60%',
               dataLabels: {
                   formatter: function() {
                       return this.y > 5 ? this.point.name : null;
                   },
                   color: 'white',
                   distance: -30
               }
           }, {
               name: '구분',
               data: versionsData,
               innerSize: '60%',
               dataLabels: {
                   formatter: function() {
                       // display only if larger than 1
                       return this.y > 1 ? '<b>'+ this.point.name +':</b> '+ this.y +'%'  : null;
                   }
               }
           }]
       });
   });
</script>
<div>
<table>		
	<caption>통계</caption>						
	<thead>
		<tr>
			<th height="30">
				통계 범위
			</th>	
			<td>
				<span id="document_text"></span>
			</td>
		<tr>
			<th height="30">
				통계 유형
			</th>	
			<td>
				<select onchange="changeChart(this.value)">
					<option value="bar">단어별 출현 횟수</option>
					<option selected="selected" value="pie">용어집별 전문어 비율</option>
				</select>
			</td>	 
		</tr>
	</thead>
</table>
</div>
<div id="container" style="min-width: 400px; height: 400px; margin: 0 auto;"></div>
