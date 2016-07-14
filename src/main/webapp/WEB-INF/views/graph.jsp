<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Home</title>
	<jsp:include page="/WEB-INF/views/common/common.jsp"></jsp:include>
 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript">







$(document).ready(function(){
	
	
	$.get('<%=request.getContextPath()%>'+'/graph',function(){
		
		
	});
	
	
	
	$("#check_all").click(function() {
		$("input[name=mycheck]:checkbox").attr("checked", true);
	});
	$("#uncheck_all").click(function() {
		$("input[name=mycheck]:checkbox").attr("checked", false);
	});
	$("#count_check").click(function() {
		alert($("input[name=mycheck]:checkbox:checked").length);
	});
});
</script>

</head>
<body>

<form name='my_form'>
<label><input type='checkbox' name='mycheck' value='apple' />사과</label>
<label><input type='checkbox' name='mycheck' value='pear' checked='checked' />배</label>
<label><input type='checkbox' name='mycheck' value='banana' />바나나</label>
<label><input type='checkbox' name='mycheck' value='melon' />멜론</label>
<br/>
<input type='button' id='check_all' value='모두 선택' />
<input type='button' id='uncheck_all' value='모두 해제' />
<input type='button' id='count_check' value='체크 수 확인' />
</form>



</body>
</html>