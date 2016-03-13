<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <nav class="navbar navbar-default navbar-fixed-top">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="<%=application.getContextPath()%>">미세먼지 알리미</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav navbar-right">
      	<li><a href="<%=application.getContextPath()%>/pm/station">시도별관측</a></li>
        <c:if test="${empty sessionScope.user}">
        <li><a href="<%=application.getContextPath()%>/login">로그인</a></li>
        </c:if>
         <c:if test="${not empty sessionScope.user}">
        <li><a href="<%=application.getContextPath()%>/logout">로그아웃</a></li>
        </c:if>
      </ul>
      
      <ul class="nav navbar-nav navbar-right">
        <li><a href="<%=application.getContextPath()%>/join">회원가입</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
<div style="height:70px;"></div>