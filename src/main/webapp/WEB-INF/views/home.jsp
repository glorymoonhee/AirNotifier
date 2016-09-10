<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Home</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<jsp:include page="/WEB-INF/views/common/common.jsp"></jsp:include>
 <script type="text/javascript" src="<%=application.getContextPath() %>/resources/js/vticker.min.js"></script>
 <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
 <style type="text/css">
html, body {
	height: 100%;
	margin: 0;
	padding: 0;
}

#map {
	height: 300px;
}
</style>
 <script type="text/javascript">
var ctxpath = '<%=request.getContextPath()%>';
var map;
var infoWindow;
var markers = [] ;
var stations = [];

function addMarkerListener ( marker,station, infowindow ) {
	marker.addListener('click', function(e) {
		/*
		 * 1. uri 정해야 하고
		 * 2. 서버쪽에서 특정 스테이션의 먼지 정보를 json으로 반환해줌.
		 * 3. infowindow.setContent 로 먼지 정보를 업데이트 해줌.
		 */
		
		 var stationName = station.name;
		 var url = ctxpath + '/query/station/' + stationName ;
		 $.get( url,  function( resp ){
				 
		 	drawChart(stationName,resp.pmdata);
		 });
		 
		  console.log(stationName);
		 var url =  '<%=request.getContextPath()%>' + '/query/station/' + stationName ;

		
		 $.get( url,  function(resp){
			
		     console.log(resp.success);
		     console.log(resp.pmdata[0].pm100Grade);
	
		    infowindow.open(map, marker ); // 이동을 시켜주려면 open을 다시 호출
		    infowindow.setContent ( '<b>' + station.name + '</b><br/><b>pm10: ' +resp.pmdata[0].pm100 +'</b><br/><b>pm2.5: ' 
		    		+resp.pmdata[0].pm025 +'</b><br/>');
		    
		    var center = {lat: parseFloat(station.lat), lng: parseFloat(station.lng)};
		    map.panTo ( center );
		} );  
	});
}
// 원래는 서버에서 얻어와야 하는데 관련된 기능이 없으니까 일단 몇개 넣어두고 화면에 띄워본디ㅏ.


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
		var chart = new google.visualization.LineChart(document.getElementById('chart'));
		chart.draw(data, options);
	});
	
	 
	
}

function CenterControl(controlDiv, map) {

  // Set CSS for the control border.
  var controlUI = document.createElement('div');
  controlUI.style.backgroundColor = '#fff';
  controlUI.style.border = '2px solid #fff';
  controlUI.style.borderRadius = '3px';
  controlUI.style.boxShadow = '0 2px 6px rgba(0,0,0,.3)';
  controlUI.style.cursor = 'pointer';
  controlUI.style.marginBottom = '22px';
  controlUI.style.textAlign = 'center';
  controlUI.title = 'Click to recenter the map';
  controlDiv.appendChild(controlUI);

  
  // Set CSS for the control interior.
  var legend = $('<div>' 
		  + '<div><span style="background-color: blue; width:14px;height:14px;display:inline-block;"></span> 맑음</div>'
		  + '<div><span style="background-color: green; width:14px;height:14px;display:inline-block;"></span> 보통</div>' 
		  + '<div><span style="background-color: orange; width:14px;height:14px;display:inline-block;"></span> 나쁨</div>'
		  + '<div><span style="background-color: red; width:14px;height:14px;display:inline-block;"></span> 매우나쁨</div></div>');
  controlUI.appendChild( legend[0] );
  

}

function initMap() {
	
	/*
	 * 1. google map 을 초기화 하는 부분입니다.
	 */
	map = new google.maps.Map(document.getElementById('map'), {
		center : {
			lat : -34.397,
			lng : 150.644
		},
		zoom : 14
	});
	var centerControlDiv = document.createElement('div');
	var centerControl = new CenterControl(centerControlDiv, map);
	centerControlDiv.index = 1;
	
	map.controls[google.maps.ControlPosition.TOP_RIGHT].push(centerControlDiv);
	
	
	/*
	 * 2. info window 를 띄웁니다.
	 */ 
	infoWindow = new google.maps.InfoWindow({
		map : map
	});

	// Try HTML5 geolocation.
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			var pos = {
				lat : position.coords.latitude,
				lng : position.coords.longitude
			};

			infoWindow.setPosition(pos);
			infoWindow.setContent('현재 위치'); //현재위치  확인
			map.setCenter(pos);
			topKStation();
			
			map.addListener('dragend', function() {
			    // 3 seconds after the center of the map has changed, pan back to the
			    // marker.
				topKStation();
			  });

			
			
			
		}, function() {
			handleLocationError(true, infoWindow, map.getCenter());
		});
	} else {
		// Browser doesn't support Geolocation
		handleLocationError(false, infoWindow, map.getCenter());
	}
		

	
	
} // end initMap

function handleLocationError(browserHasGeolocation, infoWindow, pos) {
	  infoWindow.setPosition(pos);
	  infoWindow.setContent(browserHasGeolocation ?
	                        'Error: The Geolocation service failed.' :
	                        'Error: Your browser doesn\'t support geolocation.');
	}
	
	
	
function topKStation (){
	var center = map.getCenter();
	// url/333
	var url = ctxpath + '/nearest/3/{0}/{1}';
	url = url.replace('{0}', center.lat())
	         .replace('{1}', center.lng());
	
	console.log ( url );
	
	
	var stnIcon = {
		    path: 'M-7,0a7,7 0 1,0 14,0a7,7 0 1,0 -14,0',
		    fillColor: 'blue',
		    fillOpacity: 0.2,
		    scale: 1,
		    strokeColor: 'blue',
		    strokeWeight: 2
		  };
	
	$.get(url, function(resp){
	
		if ( resp.success ) {
			
			for ( var i = 0 ; i < markers.length ; i++ ) {
				markers[i].setMap ( null );
			}
			markers = [] ;
			
			var stations = resp.nearest;
			var bnd = new google.maps.LatLngBounds();
			
			for( var i = 0; i < stations.length; i ++){
				
				var pos = {lat: parseFloat(stations[i].lat), lng: parseFloat(stations[i].lng)};
				var stnName = stations[i].name;
				var pm100 = resp[stnName]; // by 30 : blue, by 80 green , by 150 yellow, else red
				var color = '#000';
				if(pm100<=30){
					color = 'blue'
				}else if(pm100<=80){
					color = 'green'
				}else if(pm100<=150){
					color =  'orange'
				}else{
					color = 'red'
				}
				
				var marker = new google.maps.Marker({
				    position: pos,
				    map: map,
				    icon : {
					    path: 'M-7,0a7,7 0 1,0 14,0a7,7 0 1,0 -14,0',
					    fillColor: color,
					    fillOpacity: 0.8,
					    scale: 1,
					    strokeColor: color,
					    strokeWeight: 2
					},
				    title: stations[i].name
				});
				markers.push ( marker );
				addMarkerListener ( marker, stations[i], infoWindow );
				
				bnd.extend ( marker.getPosition() );
			}
			
			// map.fitBounds ( bnd );
			
			
		}
	});
	
}
	
$(document).ready ( function() {
      
	
	 $('#example').vTicker('init', {
		    speed: 400, 
		    pause: 1000,
		    showItems: 2,
		    padding: 4
		  });
	
	 google.charts.load('current', {'packages':['corechart']});
	
});
	
</script>
 
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
<div class="container-fluid">
	
	<div class="row">
		<div id="map"></div>
		<div>
		<!-- 	<button id="btnCurLoc" >중심점</button> -->
		</div>
	</div>
	
	<div class="row" >
		<!-- 그리스 시스템 : http://bootstrapk.com/css/#grid -->
		
		 <div id ="example">
		
		<ul>
		   <c:forEach var="data" items="${sidoData}" >
		   
		       <li>${sidoMap[data]} PM10 : ${data.pm100} (${data.pm100Grade}등급)  PM2.5 : ${data.pm025}(${data.pm025Grade}등급)</li>
		       
		       
		   </c:forEach>
	 	</ul>
		
		</div>
		<div id="chart" >
		
		</div>
	
	</div>
</div>

</body>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAHMC2m-a0168rs3InKfOwn-O7a_fYSjVM&callback=initMap"></script>
<script type="text/javascript">

</script>
</html>
