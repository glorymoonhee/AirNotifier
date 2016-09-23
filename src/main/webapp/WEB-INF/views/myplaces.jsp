<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>로그인</title>
<jsp:include page="/WEB-INF/views/common/common.jsp"></jsp:include>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
var ctxpath = '<%=application.getContextPath()%>';
var pmdata =  { }; // new HashMap(); 

/*
 * ${stations}
 * 
 @param stationName String 
 @param pmdata      Array, 
 */
function drawChart (stationName, pmdata ) {
	
	google.charts.setOnLoadCallback( function() {
		
		var dataSource = [];
		dataSource.push ( ['Time', 'PM 10'] );
		for ( var i = pmdata.length - 1 ; i >= 0 ; i -- ) {
			var pm = pmdata[i];
			// 2016-03-20 11:00:00
			var arr = [ pm.dataTime.substring(8), parseInt(pm.pm100) ];
			dataSource.push ( arr );
			
		}
		
		var data = google.visualization.arrayToDataTable(dataSource );
		
		var options = {
				width: '100%',
				height: 500,
		          title: 'PM 수치',
		          curveType: 'function',
		          legend: { position: 'bottom' },
		          chartArea : {
						left : 40,
						top : 20,
						width: '90%',
						height: '80%'
					}
		};
		var chart = new google.visualization.LineChart(document.getElementById('pm_chart'));
		chart.draw(data, options);
	});
	
	 
	
}
 
 function drawChartAll ( dataMap ) {
	var dataSource = [];
	/* Set<String> keys = map.keySet();
	 * {
		 {key0, val0),
		 {key1, val1}
		 }
		 amp.get(key);
	   }
	 * for ( String key : keys ) {
		 
	 }
	 */
	 
	var region = ['Time'];
	for(var station in dataMap){
		 region.push(station);
	}
	dataSource.push ( region );
	
	for( var i = 25 - 1 ; i >= 0 ; i --  ){
		console.log('prop: ', station);
		var data = dataMap [ station ]; //25개 각 station의 배열
		// 2016-07-13 15:00 8~13
		var arr = [ data[i].dataTime.substring(8,10) + '일 ' +  data[i].dataTime.substring(11,13) + '시'] ;// each row
		for(var station in dataMap){
			var s = dataMap[station];
			arr.push(parseFloat(s[i].pm100));
		}
		
		dataSource.push ( arr );
		
	}
	/*
	dataSource.push ( ['Time', 'PM 10'] );
	for ( var i = pmdata.length - 1 ; i >= 0 ; i -- ) {
		var pm = pmdata[i];
		// 2016-03-20 11:00:00
		var arr = [ pm.dataTime.substring(8), parseInt(pm.pm100) ];
		dataSource.push ( arr );
		
	}
	*/
	 
		google.charts.setOnLoadCallback( function() {
			
			var data = google.visualization.arrayToDataTable(dataSource );
			
			var options = {
					width: '100%',
					height: 500,
			          title: 'PM 수치',
			          curveType: 'function',
			          legend: { position: 'bottom' },
			          chartArea : {
							left : 40,
							top : 20,
							width: '90%',
							height: '80%'
						}
			};
			var chart = new google.visualization.LineChart(document.getElementById('pm_chart'));
			chart.draw(data, options);
		});
		
		 
		
	}

$(document).ready ( function(){
	
	var stations = '${stations}' ;
	console.log ('나와라',  stations );
	
	google.charts.load('current', {'packages':['corechart']});
	var stationName ;
	$('#place_all').click(function(){
		
		drawChartAll(pmdata) ;
	});
	
	$('.place').click(function(e){
		stationName = $(this).text() ;
		console.log ( stationName );
		drawChart ( stationName,  pmdata[stationName] ); // pmdata.get(stationName);
	
	});
	
	  $(".dropdown-menu li a").click(function(){
	  var url = '<%=request.getContextPath()%>/DeleteUserStation';
	 
	  var station_name = {station_name: ($(this).text())};
	  var btnId = $(this).data('btnid');
	
		$.post(url ,station_name , function(res){
			if ( res.success ) {
			
				console.log('delete 성공');
				$('#' + btnId).remove();
			}
		});
	});
	
	var ap = $('.place');
	var stationName ;
	var tmp ;
	for ( var i = 0 ; i < ap.length ; i++ ) {
		stationName = $(ap[i]).text() ;
		tmp = stationName ;
		var url = ctxpath + '/query/station/' + stationName ;
		/*
		 * 요청을 보내는 시점과 응답이 오는 시점은 우리가 통제할 수 없음.
		 * 그런데 이미 코드상에서 closure와 hoisting으로 stationName이 배열의 맨 마지막 값으로 fix되어버리고
		 * 응답이 들어옴.
		 */
		$.get( url,  function( resp ){
			console.log ( resp );
			pmdata[ resp.station ] = resp.pmdata ; 
		
			
		});
	}
	
	
	

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
			 <div id="places">
             <c:forEach items="${stations}" var="s" varStatus="vs">
             	<span id="place${vs.index}" class="btn btn-primary place">${ s.name }</span>
            	
             </c:forEach>
			 </div>
              <button id="place_all">전체보기</button>
              <!-- 버튼그룹시작 -->
              
              <div class="btn-group">
    <button class="btn">삭제할 항목</button>
    <button class="btn dropdown-toggle" data-toggle="dropdown">
        <span class="caret"></span>
    </button>
    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">

       <c:forEach items="${stations}" var="s" varStatus="vs">
             
            	  <li><a tabindex="-1"  data-btnid="place${vs.index}">${ s.name }</a></li>
             </c:forEach>
     
    </ul>
</div>

            <!--  버튼그룹끝 -->
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<div id="pm_chart"></div>
		</div>
	</div>
</div>
</body>
</html>