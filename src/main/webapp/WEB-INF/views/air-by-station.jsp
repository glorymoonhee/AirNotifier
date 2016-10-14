<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>시도별 관측</title>
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
	
	$("#btn-add-place").on("click", function(){
		var stationName = $('#stationName').val();
		console.log ("station" , stationName);
		var url = ctxpath + '/station/register';
		var pm10 = $('#pm10Value').val();
		
		if ( ! /^[0-9]+$/.test ( pm10 ) ) {
			alert ( '값이 이상하다.');
			return ;
		}
		
		$.post ( url , {station: stationName, pm10 :pm10  }, function( resp ) {
			console.log ( resp );
			if ( resp.success ) {
				$('#place-dialog').modal('hide');
			} else {
				if ( resp.cause == 'BAD_PM10') {
					alert( 'pm 10 수치값이 이상합니다' );
				} else {
					alert( '알 수 없는 오류 : ' + resp.cause );
				}
			}
		});
		
	});
	
	
	
	$("#btn-remove-place").on("click", function(){
		var stationName = $('#stationName').val();
		var url = ctxpath + '/station/delete';
		
		$.post ( url , {station: stationName}, function( resp ) {
			console.log ( resp );
			if ( resp.success ) {
				alert('OK!!! SUCCESS')
			} else {
				alert('Fail' + resp.cause);
			}
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
<c:if test="${not empty sessionScope.user}"><!--  session.getAttribute("user") != null  -->
	
	<c:if test="${favoredPlace}"><!-- request.getAttribute("favforedPlace") == True -->
			<button id="btn-remove-place">장소해제</button>
	</c:if>
	
	<c:if test="${not favoredPlace}"><!-- request.getAttribute("favforedPlace") == True -->
	<button id="btn-show-modal"  data-toggle="modal" data-target="#place-dialog">장소등록</button>
	</c:if>
	

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

          <c:set var="grade100" value="${i.pm100Grade}" />
            <c:choose>
    <c:when test="${grade100 eq 1}">
         <td>좋음</td>
    </c:when>
    <c:when test="${grade100 eq 2}">
         <td>보통</td>
    </c:when>
      <c:when test="${grade100 eq 3}">
         <td>나쁨</td>
    </c:when>
      <c:when test="${grade100 eq 4}">
         <td>매우나쁨</td>
          <c:otherwise>
           <td>--</td>
        </c:otherwise>
    </c:when>
  
       </c:choose> 

        
        <td>${i.pm025}ppm</td> 
     
          <c:set var="grade25" value="${i.pm025Grade}" />
            <c:choose>
    <c:when test="${grade25 eq 1}">
         <td>좋음</td>
    </c:when>
    <c:when test="${grade25 eq 2}">
         <td>보통</td>
    </c:when>
      <c:when test="${grade25 eq 3}">
         <td>나쁨</td>
    </c:when>
      <c:when test="${grade25 eq 4}">
         <td>매우나쁨</td>
          <c:otherwise>
           <td>--</td>
        </c:otherwise>
    </c:when>
  
       </c:choose> 
 </c:forEach>
 </tbody>
 </table>
 
 
<div id="place-dialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">장소등록</h4>
      </div>
      <div class="modal-body">
      	<p>통보받을 PM10 수치를 입력합니다(비우면 통보받지 않습니다). </p>
        <p><input type="text" id="pm10Value" class="form-control" ></p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">취소 </button>
        <button type="button" class="btn btn-primary" id="btn-add-place">등록하기</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
</body>
</html>