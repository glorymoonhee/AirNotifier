<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>�α���</title>
<jsp:include page="/WEB-INF/views/common/common.jsp"></jsp:include>
<script type="text/javascript">
$(document).ready(function() {
	$('#btnJoin').on('click', function() {
		var data = $('#frmLogin').serialize();
		var url = '<%=request.getContextPath()%>/doLogin';
		$.post(url ,data, function(res){
			if ( res.success ) {
				alert ( '�α��� ���� ');
				document.location.href = '<%=request.getContextPath()%>';
			}
		});
		
	});	
});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
<h2>�α���</h2>
<form id="frmLogin">
	<input type="text" class="form-control" id="email" name="email" placeholder="�̸���" value="gg@naver.com">
	<input type="password" class="form-control" id="password" name="password" placeholder="��й�ȣ" value="1111">
	
	<input type="button" value="�α���" id="btnJoin">
</form>

</body>
</html>