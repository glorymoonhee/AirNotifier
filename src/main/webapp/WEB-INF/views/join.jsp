<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="/WEB-INF/views/common/common.jsp"></jsp:include>
<script type="text/javascript">
$(document).ready(function() {
	$('#btnJoin').on('click', function() {
		var data = $('#frmJoin').serialize();
		var url = '<%=request.getContextPath()%>/doJoin';
		$.post(url ,data, function(res){
			if ( res.success ) {
				alert ( '가입성공');
				document.location.href = '<%=request.getContextPath()%>';
			}
		});
		
	});	
});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
<form id="frmJoin">
	<input type="text" class="form-control" id="email" name="email">
	<input type="password" class="form-control" id="password" name="password">
	
	<input type="button" value="가입하기" id="btnJoin">
</form>

</body>
</html>