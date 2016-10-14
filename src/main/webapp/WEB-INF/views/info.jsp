<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:include page="/WEB-INF/views/common/common.jsp"></jsp:include>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>


<title>회원정보</title>
<script type="text/javascript">
 $(document).ready(function(){
	 
	 $('#btnUpdate').on('click',function(){
		
		 var url = '<%=request.getContextPath()%>/doUpdate';
		 var data = $('#frmUpdate').serialize();
		 
		 $.post(url,data,function(res){
			  if(res.sucess){
			 alert('수정 되었습니다.'); 
			  }
		 });
		 
		 
	 });
	 
	 
 });
 
 



 </script>

</head>
<body>


<div class="container">

  <form class="form-horizontal" role="form" id="frmUpdate">
    <div class="row">
  <div class="col-sm-4"></div>
  <div class="col-sm-4">    <h2>UPDATE</h2> </div>
  <div class="col-sm-4"></div>
</div>
  
  
\
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Email:</label>
      <div class="col-sm-10">
      	
      	<input type="text" class="form-control" id="email" name="email" readonly value="${sessionScope.user.email} "/>

      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="pwd">Password:</label>
      <div class="col-sm-10">          
       <input type="password" class="form-control" id="password" name="password" placeholder="비밀번호" value="${sessionScope.user.password}">
      </div>
    </div>
    <div class="form-group">        
      <div class="col-sm-offset-2 col-sm-10">
        <div class="checkbox">
          <label><input type="checkbox"> Remember me</label>
        </div>
      </div>
    </div>
    <div class="form-group">        
      <div class="col-sm-offset-2 col-sm-10">
     <input type="button" value="update" id="btnUpdate">
       <input type="button" value="delete account" style="color:#ff0000" id="delete">
      </div>
    </div>
  </form>
</div>



<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>




</body>
</html>