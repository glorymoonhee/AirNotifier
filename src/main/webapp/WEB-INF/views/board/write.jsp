<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">	
<title>로그인</title>
<jsp:include page="/WEB-INF/views/common/common.jsp"></jsp:include>
<!-- include summernote css/js-->
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.2/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.2/summernote.js"></script>
<script type="text/javascript">

 var ctxPath = '<%=request.getContextPath()%>';
 
$(document).ready(function() {
	  $('#summernote').summernote({
		  height:400
	  });
	  
	  $('#btn_write').click(function(e){
		   
		  var url = ctxPath+'/board/doWrite'; // "/doWrite?id=3233"
		  var data = $('#posting').serialize(); //title=sxxxx&writer=ggggg&content=	  
		  console.log ( 'data ? ', data);
		  
		  var markupStr = $('#summernote').summernote('code');
		  data += "&content=" + markupStr;
		  $.post(url, {
			  title : $('#title').val(), 
			  content : markupStr, 
			  writer:'no' }, function(e){
			  console.log('응답 왔음?', e);
			  document.location.href = ctxPath + '/board';
		  });
		
		 
		  
	  });
	});

</script>
</head>

<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

<form id="posting">
	<div><input type="text" id="title" name ="title" placeholder="글제목" class="form-control"/></div>
	<div><input type="text" id="writer" name ="writer" placeholder="작성자" class="form-control"/></div>
	<div id="summernote" name="content">Hello Summernote</div>
	
	<div><button id="btn_write" >확인</button></div>
</form>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

</body>
</html>