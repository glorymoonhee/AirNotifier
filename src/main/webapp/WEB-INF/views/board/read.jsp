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


 $(document).ready(function() {
	 $('.btn-group button').click(function(){
		 var data_id = this.id;// $(this).id  this.id 차이  
		 var postnum = $(this).val();
		 var url = '<%=request.getContextPath()%>/board/'+ data_id + '/'+ postnum;
		  console.log(url);
	    $.get(url,postnum,function(res){
	    	if(res.success){
	    		  document.location.href = '<%=request.getContextPath()%>' + '/board';
	    		
	    		} 
	    	
	    	
	    	}); 
		 
		 });
	 
 	});
 
 

</script>

</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

<div class="container-fluid">
  
          <div class="panel panel-default">
               <div class="panel-heading"><font size="3"><strong>${requestScope.post.title }</strong></font></div>
            
                  <div class="panel-heading">
                        <div class="row">
                            <div class="col-sm-8"><font size="2">글쓴이: ${requestScope.user.email}</font></div>
                            <div class="col-sm-4"><font size="2">날짜: ${requestScope.post.dateTime}</font></div>
                        </div>
                  </div>
                  
               <div class="panel-body" style="height:400px">${requestScope.post.content }</div>
          
          </div>
          
          
          <div class='wrapper text-center'>
       <div class="btn-group">
  <button type="button" class="btn btn-primary" style="text-align:center" id="doUpdate" value=${requestScope.post.seq}>수정</button>
  <button type="button" class="btn btn-primary" style="text-align:center" id="doDelete" value=${requestScope.post.seq}>삭제</button>
         </div>
          </div>
</div>
</body>
</html>