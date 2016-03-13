<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>로그인</title>
<jsp:include page="/WEB-INF/views/common/common.jsp"></jsp:include>
<script type="text/javascript">
$(document).ready(function() {
	$('#btnJoin').on('click', function() {
		var data = $('#frmLogin').serialize();
		var url = '<%=request.getContextPath()%>/doLogin';
		$.post(url ,data, function(res){
			if ( res.success ) {
				alert ( '로그인 성공 ');
				document.location.href = '<%=request.getContextPath()%>';
			}
		});
		
	});	
});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
<h2>로그인</h2>
<form id="frmLogin">
	<input type="text" class="form-control" id="email" name="email" placeholder="이메일" value="gg@naver.com">
	<input type="password" class="form-control" id="password" name="password" placeholder="비밀번호" value="1111">
	
	<input type="button" value="로그인" id="btnJoin">
</form>

</body>
</html>