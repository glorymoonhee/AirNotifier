<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인</title>
<jsp:include page="/WEB-INF/views/common/common.jsp"></jsp:include>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
var ctxpath = '<%=application.getContextPath()%>';

function drawChart (stationName, pmdata ) {
	
	google.charts.setOnLoadCallback( function() {
		
		var dataSource = [];
		dataSource.push ( ['Time', 'PM 10', 'PM 2.5'] );
		for ( var i = pmdata.length - 1 ; i >= 0 ; i -- ) {
			var pm = pmdata[i];
			// 2016-03-20 11:00:00
			var arr = [ pm.dataTime.substring(8), parseInt(pm.pm100), parseInt ( pm.pm25 ) ];
			dataSource.push ( arr );
			
		}
		
		var data = google.visualization.arrayToDataTable(dataSource );
		
		var options = {
				// width : ????????, 
		          title: stationName,
		          curveType: 'function',
		          legend: { position: 'bottom' }
		};
		var chart = new google.visualization.LineChart(document.getElementById('pm_chart'));
		chart.draw(data, options);
	});
	
	 
	
}
$(document).ready ( function(){
	google.charts.load('current', {'packages':['corechart']});
	$('.place').click(function(e){
		var stationName = $(this).text() ;
		// /query/station/수내동
		var url = ctxpath + '/query/station/' + stationName ;
		$.get(url,  function( resp ){
			console.log ( resp );
			drawChart (stationName, resp.pmdata );
			
		});
	});
});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
<div class="container-fluid">
	<div class="row">
		<div class="col-xs-12">
			<!--
			for ( int i = 0 ; i < stations.size() ; i ++ ) {
				StationVO s = stations.get(i);
				s.getSeq();
			} 
			 -->
			 
             <c:forEach items="${stations}" var="s" >
             	<span class="btn btn-primary place">${ s.name }</span>
             </c:forEach>

		</div>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<div id="pm_chart" style="height:500px;"></div>
		</div>
	</div>
</div>
</body>
</html>