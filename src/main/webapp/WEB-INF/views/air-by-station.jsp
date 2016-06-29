<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=application.getContextPath() %>/resources/css/bootstrap.css" />
<script type="text/javascript" src="<%=application.getContextPath() %>/resources/js/jquery-1.12.0.js"></script> 
<script type="text/javascript" src="<%=application.getContextPath() %>/resources/js/bootstrap.js"></script>
<script type="text/javascript">
var ctxpath = '<%=request.getContextPath()%>';
$(document).ready( function() {
	

	$("#stationName").on("change", function(){
		
		var stationName = $('#stationName option:selected').val();
		var sido = $('#sido option:selected').val();
		document.location.href = ctxpath + '/pm/station?sido='+ sido + '&name=' + stationName ;
	});

	function renderStations ( stations ) {
		$("#stationName").empty();
		
		console.log(stations);
		var stations = stations;
		stations.sort();
		var template = '<option value="{$n}">{$n}</option>'
			$("#stationName").append("<option>[관측소선택]</option>");
		for( var i=0; i<stations.length; i++){
			var html = template.replace('{$n}',stations[i].name).replace('{$n}', stations[i].name);
			$("#stationName").append(html); 
		}
	}
	
	$("#sido").on("change", function(){
		var sidoName = $('#sido option:selected').val();
		// 1. [지역선택] 이면 건너뜀.
		
		$.get ( ctxpath + "/query/stations", {sido: sidoName}, function(res){
			// /air/query/stations?sido=sldkjafkdjfkd
			/*
			 *  { success : true,
				  stations : {..... }
				}
			 */
			if ( res.success ) {
				console.log('success');
				renderStations ( res.stations );
			} else {
				console.log('fail');
			}
		});
	});
	
	$("#btn_add_place").on("click", function(){
		
		var stationName = $('#stationName').val();
		console.log ("station" , stationName);
		var url = ctxpath + '/station/register';
		$.post ( url , {station: stationName }, function( resp ) {
			console.log ( resp );
		});
	});
	
});
</script> 
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
<select id="sido">

<c:forEach var="i" items="${sidos}">
<c:if test="${sido eq i}"><option value="${i}" selected="selected" >${i}</option></c:if>
<c:if test="${sido ne i}"><option value="${i}"  >${i}</option></c:if>
</c:forEach>
</select>
<select id="stationName">

	<c:forEach var="i" items="${stations}">
	 <c:if test="${station eq i.name}"><option value="${i.name}" selected="selected" >${i.name}</option></c:if>
	 <c:if test="${station ne i.name}"><option value="${i.name}"  >${i.name}</option></c:if>
	</c:forEach>
</select>
 관측소 : ${station}
<c:if test="${not empty sessionScope.user}">
	<button id="btn_add_place">장소등록</button>
	<input type="hidden" id="loginUser" value="${sessionScope.user.seq }">
</c:if>
 <table class="table table-striped">
 <thead>
	 <tr>
	 	<th>#</th>
	 	<th>관측시간</th>
	 	<th>PM 10</th>
	 	<th>PM 10 등급</th>
	 	<th>PM 2.5</th>
	 	<th>PM 2.5 등급</th>
	 </tr>
 </thead>
 <tbody>
 <c:forEach var="i" items="${pmData}">
  <tr>
  	<th scope="row"></th>
  	<td>${i.dataTime}</td>  
       <td>${i.pm100}ppm</td> 
       <td>(${i.pm100Grade }등급)</td> 
        <td>${i.pm025}ppm</td> 
        <td>(${i.pm025Grade }등급)</td> </tr>
 </c:forEach>
 </tbody>
 </table>
 
 

</body>
</html>