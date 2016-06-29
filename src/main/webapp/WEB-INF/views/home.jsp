<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Home</title>
	<jsp:include page="/WEB-INF/views/common/common.jsp"></jsp:include>
 
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
var map;
var stations = [];

function addMarkerListener ( marker,station, infowindow ) {
	marker.addListener('click', function(e) {
		/*
		 * 1. uri 정해야 하고
		 * 2. 서버쪽에서 특정 스테이션의 먼지 정보를 json으로 반환해줌.
		 * 3. infowindow.setContent 로 먼지 정보를 업데이트 해줌.
		 */
		$.get( '/url', 'station=23', function(resp){
			
		    infowindow.open(map, marker ); // 이동을 시켜주려면 open을 다시 호출
		    infowindow.setContent ( '<b>' + station.name + '</b><br/>pm10: 23.4' );
		    
		    var center = {lat: parseFloat(station.lat), lng: parseFloat(station.lng)};
		    map.panTo ( center );
		} );
	});
}
// 원래는 서버에서 얻어와야 하는데 관련된 기능이 없으니까 일단 몇개 넣어두고 화면에 띄워본디ㅏ.

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
		var marker ; 
		for(var i = 0; i<resp.stations.length; i++){
			
			var pos = {lat: parseFloat(resp.stations[i].lat), lng: parseFloat(resp.stations[i].lng)};
			
			marker = new google.maps.Marker({
			    position: pos,
			    map: map,
			    title: resp.stations[i].name
			    
			});	
			// closure 를 피하기 위해서 marker의 참조를 복사해서 넘겨줍니다.
			// 안에서는 특정 marekr에 이벤트 리스너를 등록하게 됩니다.
			addMarkerListener ( marker, resp.stations[i], infoWindow );
			
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
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
<div class="container-fluid">
	
	<div class="row">
		<div id="map"></div>
	</div>
	
	<div class="row">
		<!-- 그리스 시스템 : http://bootstrapk.com/css/#grid -->
		<c:forEach var="data" items="${sidoData}" >
			<div class="col-md-3 col-sm-4 col-xs-6" style="background-color: yellow">
			<div>${sidoMap[data] }</div> <!--  sidoMap.get(data); -->
		 	<div>PM10 : ${data.pm100} (${data.pm100Grade} )</div>
			<div>PM2.5 : ${data.pm025}(${data.pm025Grade} )</div>
		</div>
		</c:forEach>
	</div>
</div>
</body>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAHMC2m-a0168rs3InKfOwn-O7a_fYSjVM&callback=initMap"></script>
</html>
