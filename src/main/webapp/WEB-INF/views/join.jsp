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
		
	    var email = $('#frmJoin input[type=email]').val();
	    var pass = $('#frmJoin input[type=password]').val();
	    var name = $('#frmJoin input[type=text]').val();
	   
	    if( email =="" || pass=="" || name==""){
	    	alert (' Email,비밀번호,이름에 빈 칸이 없는지 확인해주세요.');
	    }
	    else{
	    	
	    	var data = $('#frmJoin').serialize();
			var url = '<%=request.getContextPath()%>/doJoin';
			$.post(url ,data, function(res){
				if ( res.success ) {
					alert ( '가입성공');
					document.location.href = '<%=request.getContextPath()%>';
				}
			});
	    	
	    }
		
	});	
});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

  <article class="container">
    
        <div class="col-md-12">
        <div class="page-header">
    	    <h1>회원가입 </h1>
        </div>
        <form class="form-horizontal" id="frmJoin">
        <div class="form-group">
          <label class="col-sm-3 control-label" for="inputEmail">이메일</label>
        <div class="col-sm-6">
          <input class="form-control" id="inputEmail" type="email" placeholder="이메일" id="email" name="email">
        </div>
        </div>
        <div class="form-group">
          <label class="col-sm-3 control-label" for="inputPassword">비밀번호</label>
        <div class="col-sm-6">
          <input class="form-control" id="inputPassword" type="password" placeholder="비밀번호" id="password" name="password">
        <p class="help-block">숫자, 특수문자 포함 8자 이상</p>
        </div>
        </div>
         
        <div class="form-group">
            <label class="col-sm-3 control-label" for="inputName">이름</label>
          <div class="col-sm-6">
            <input class="form-control" id="inputName" type="text" placeholder="이름" id="name" name="name">
          </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label" for="inputNumber">휴대폰번호</label>
             <div class="col-sm-6">
                <div class="input-group">
                  <input type="tel" class="form-control" id="inputNumber" placeholder="- 없이 입력해 주세요" id="phonenumber" name="phonenumber" />
                  </div>
        </div>
        </div>
       <div class="form-group">
              <label class="col-sm-3 control-label" for="inputAgree">약관 동의</label>
            <div class="col-sm-6" data-toggle="buttons">
              <label class="btn btn-warning active">
                <input id="agree" type="checkbox" autocomplete="off" checked>
                  <span class="fa fa-check"></span>
              </label>
              <a href="#">이용약관</a> 에 동의 합니다.
            </div>
          </div>
        <div class="form-group">
          <div class="col-sm-12 text-center">
            <button class="btn btn-primary" type="submit" id="btnJoin">회원가입<i class="fa fa-check spaceLeft"></i></button>
            <button class="btn btn-danger" type="submit">가입취소<i class="fa fa-times spaceLeft"></i></button>
          </div>
        </div>
        </form>
          <hr>
        </div>
      </article>
      <jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
</html>