<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/common/common.jsp"></jsp:include>
<style type="text/css">
html, body {
	height: 100%;
	margin: 0;
	padding: 0;
}

#map {
	height: 100%;
}
</style>
<script type="text/javascript">
var map;
var stations = [];

// 원래는 서버에서 얻어와야 하는데 관련된 기능이 없으니까 일단 몇개 넣어두고 화면에 띄워본디ㅏ.

function initMap() {
	
	/*
	 * 1. google map 을 초기화 하는 부분입니다.
	 */
	var map = new google.maps.Map(document.getElementById('map'), {
		center : {
			lat : -34.397,
			lng : 150.644
		},
		zoom : 14
	});
	
	/*
	 * 2. info window 를 띄웁니다.
	 */ 
	var infoWindow = new google.maps.InfoWindow({
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
			infoWindow.setContent('Location found.');
			map.setCenter(pos);
		}, function() {
			handleLocationError(true, infoWindow, map.getCenter());
		});
	} else {
		// Browser doesn't support Geolocation
		handleLocationError(false, infoWindow, map.getCenter());
	}
	
	var param = {sido: '경기'};
	// /query/stations?sido=경기
	$.get( '<%=request.getContextPath()%>'+'/query/stations', param , function(resp ){
		
		console.log ( resp.stations );
		
		for(var i = 0; i<resp.stations.length; i++){
			
			var pos = {lat: parseFloat(resp.stations[i].lat), lng: parseFloat(resp.stations[i].lng)};
			
			var marker = new google.maps.Marker({
			    position: pos,
			    map: map,
			    title: resp.stations[i].name
			});	
		}
		
	});
	

	
	
} // end initMap

function handleLocationError(browserHasGeolocation, infoWindow, pos) {
	  infoWindow.setPosition(pos);
	  infoWindow.setContent(browserHasGeolocation ?
	                        'Error: The Geolocation service failed.' :
	                        'Error: Your browser doesn\'t support geolocation.');
	}
	
	
</script>
</head>
<body>
	<div id="map"></div>

	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAHMC2m-a0168rs3InKfOwn-O7a_fYSjVM&callback=initMap">
		
	</script>
</body>
</html>