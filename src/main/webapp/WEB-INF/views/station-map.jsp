<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>관측소 목록</title>
<jsp:include page="common/common.jsp"></jsp:include>
<script type="text/javascript" src="//apis.daum.net/maps/maps3.js?apikey=42a3c1c1c01d76ef4f0e89355847dbbb"></script>
<!-- 42a3c1c1c01d76ef4f0e89355847dbbb -->


 
<script>
var ctxpath = '<%=application.getContextPath() %>';
var fakePoints = [
    { name : '중구', lat: '37.564639', lng: '126.975961'} ,
    { name : '한강대로', lat: '37.549389', lng: '126.971519'} ,
    { name : '종로구', lat: '37.572025', lng: '127.005028'} 
];

function renderStations ( stations ) {
	var bounds = new daum.maps.LatLngBounds();    

	var i, marker;
	for (i = 0; i < stations.length; i++) {
	    // 배열의 좌표들이 잘 보이게 마커를 지도에 추가합니다
	    var pos = new daum.maps.LatLng(stations[i].lat, stations[i].lng);
	    marker =     new daum.maps.Marker({ position : pos });
	    marker.setMap(map);
	    
	    /* var infowindow = new daum.maps.InfoWindow({
	        position : pos, 
	        content : stations[i].name 
	    });
	    infowindow.open(map, marker); */
	    
	    // LatLngBounds 객체에 좌표를 추가합니다
	    bounds.extend(pos);
	}
	map.setBounds(bounds);

	
}

var map ;
$(document).ready ( function() {
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new daum.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 10 // 지도의 확대 레벨
    };	
	// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
	map = new daum.maps.Map(mapContainer, mapOption);
	
    $("btn_load").click(function(e){
    	
    	renderStations(fakePoints);
    })
	
	$("#sido").on("change",function(e){
		console.log( this.value );
		$.get(ctxpath + '/stations',{sido: this.value}, function(res){
			console.log( res );
			renderStations(res);
		} ); // "/api/stations?sido=서울 "
	});
	
});

</script>
</head>
<body>

<div class="container-fluid">
<!-- 
	<div class="row">
		<div class="col-xs-12">
		<ul>
		<li> select 넣기(서울, 경기, 강원...)
		<li> select로 선택을 하면 관측소를 json 으로 응답을 받아옴.
		</ul>
		</div>
	</div>
 -->
 	<div class="row">
 	   <div class="col-xs-12">
 	     <select class="form-control" id="sido">
 	       <option value="서울">서울</option>
 	       <option value="경기">경기</option>
 	       <option value="제주">제주</option>
 	     </select>
 	   </div>
  	 </div>
 	
	<div class="row">
		<div class="col-xs-12">
			<div id="map" style="width:100%;height:350px;"></div>
			<button class="btn btn-primary" id="btn_load">테스트</button>
		</div>
	</div>
</div>

</body>
</html>