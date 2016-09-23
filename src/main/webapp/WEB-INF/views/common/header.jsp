<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <nav class="navbar navbar-default navbar-fixed-top navbar-inverse">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="<%=application.getContextPath()%>/">미세먼지 알리미</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse " id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav navbar-right">
      	<li><a href="<%=application.getContextPath()%>/pm/station"><span class="glyphicon glyphicon-search"></span>시도별관측</a></li>
        <c:if test="${empty sessionScope.user}">
        <li><a href="<%=application.getContextPath()%>/login"><span class="glyphicon glyphicon-log-in"></span>로그인</a></li>
        </c:if>
         <c:if test="${not empty sessionScope.user}">
        <li><a href="<%=application.getContextPath()%>/board"><span class="glyphicon glyphicon-user"></span>게시판</a></li>
        <li><a href="<%=application.getContextPath()%>/myplaces"><span class="glyphicon glyphicon-heart-empty"></span>관심장소</a></li>
            <li><a href="<%=application.getContextPath()%>/info"><span class="glyphicon glyphicon-heart-empty"></span>내정보</a></li>
      
        <li><a href="<%=application.getContextPath()%>/logout"><span class="glyphicon glyphicon-log-out"></span>로그아웃</a></li>
        </c:if>
      </ul>
      
      <ul class="nav navbar-nav navbar-right">
      <c:if test="${empty sessionScope.user}">
        <li><a href="<%=application.getContextPath()%>/join"><span class="glyphicon glyphicon-user"></span>회원가입</a></li>
        <li><a href="<%=application.getContextPath()%>/board"><span class="glyphicon glyphicon-user"></span>게시판</a></li>
      </c:if>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
<div style="height:50px;"></div>