<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<<!DOCTYPE html>
<html>
<head>
	<title>Home</title>
	<jsp:include page="/WEB-INF/views/common/common.jsp"></jsp:include>
 
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
<div class="container-fluid">
	<div class="row">
		<!-- 그리스 시스템 : http://bootstrapk.com/css/#grid -->
		<c:forEach var="data" items="${sidoData}" >
			<div class="col-md-3 col-sm-4 col-xs-6" style="background-color: yellow">
			<div>${sidoMap[data] }</div> <!--  sidoMap.get(data); -->
		 	<div>PM10 : ${data.pm100} (${data.pm100Grade} )</div>
			<div>PM2.5 : ${data.pm025}(${data.pm025Grade} )</div>
		</div>
		</c:forEach>
		<%-- <div class="col-md-3 col-sm-6 col-xs-6" style="background-color: yellow">
		<div>서울</div>
 	    <div>PM10 : ${seoul.pm100} (${seoul.pm100Grade} )</div>
		<div>PM2.5 : ${seoul.pm025}(${seoul.pm025Grade} )</div>
		</div>
		<div class="col-md-3 col-sm-4 col-xs-6" style="background-color: magenta">
		<div>경기</div>
		<div>PM10 : ${gyeonggi.pm100} (${gyeonggi.pm100Grade} )</div>
		<div>PM2.5 : ${gyeonggi.pm025}(${gyeonggi.pm025Grade} )</div>
		</div>
		<div class="col-md-3 col-sm-4 col-xs-6" style="background-color: yellow">
		<div>강원</div>
		<div>PM10 : ${gangwon.pm100} (${gangwon.pm100Grade} )</div>
		<div>PM2.5 : ${gangwon.pm025}(${gangwon.pm025Grade} )</div>
		</div>				
		<div class="col-md-3 col-sm-4 col-xs-6" style="background-color: yellow">충청남도</div>
		<div class="col-md-3 col-sm-4 col-xs-6" style="background-color: yellow">충청북도</div>
		<div class="col-md-3 col-sm-4 col-xs-6" style="background-color: yellow">전북</div>
		<div class="col-md-3 col-sm-4 col-xs-6" style="background-color: yellow">전남</div> --%>
	</div>
</div>
</body>
</html>
