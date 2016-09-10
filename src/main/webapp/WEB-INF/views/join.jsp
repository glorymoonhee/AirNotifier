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

<div class="container">

  <form class="form-horizontal" role="form" id="frmJoin">
    <div class="row">
  <div class="col-sm-4"></div>
  <div class="col-sm-4">    <h2>회원가입</h2> </div>
  <div class="col-sm-4"></div>
</div>
  



     <div class="form-group">
        <label class="control-label col-sm-2" for="email">Email:</label>
      <div class="col-sm-10">
	<input type="text" class="form-control" id="email" name="email">
      </div>
  <label class="control-label col-sm-2" for="email">password:</label>
      <div class="col-sm-10">
	<input type="password" class="form-control" id="password" name="password">
	 </div>
	<input type="button" value="가입하기" id="btnJoin">
	
	</div>
</form>
</div>

</body>
</html>