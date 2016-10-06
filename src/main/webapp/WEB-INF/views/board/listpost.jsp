<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">	
<title>로그인</title>
<jsp:include page="/WEB-INF/views/common/common.jsp"></jsp:include>
<script type="text/javascript">
var fake = [{
		seq : 1900,
		title : 'hahaha',
		writer : 'ppoo',
		date : '2016-09-12 12:33:12',
		view : 1200
	  },
	  {
				seq : 1899,
				title : 'yesterday',
				writer : 'wang',
				date : '2016-09-11 22:00:12',
				view : 1200
	 }];
	 
 $(document).ready(function(e){
	 
   loadPosting(1);
   //renderPostings(fake);
   
  $('.pagination').on('click',function(e){
	  
	  var ul = $(e.target).parent().parent(); // .closes( 'ul' )
	  ul.children().removeClass('active');
	  $(e.target).parent().addClass('active');
	  console.log('pnum click', $(e.target).text());
	  var pagenum = $(e.target).text();
	  
	  loadPosting(pagenum);
  });
 
 
	 
 });
 
function loadPosting ( pagenum ) {
	var url = "<%=request.getContextPath()%>/board/api/posting/"+ pagenum;
	$.get(url, function(resp){
		 
	      console.log(resp.sucess);
	       console.log(resp.postings[0].writer);
		   console.log(resp.postings[0].view);
		   console.log(resp.postings[0].date); 
	        
		  
		 // resp.success = true;
		  resp.pagenum = 1;
		 // resp.postings = fake;
		  
		renderPostings(resp.pagenum, resp.postings);
	});	
}

function renderPostings (pagenum, postings ) {
	
	var template = '<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td><td>{5}</td></tr>';
	var trContent = '';
	for(var i=0; i<postings.length ; i++){
		var p = postings[i];
		var row  = template.replace('{0}', p.seq); //<tr><td>1222</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td></tr>
		row = row.replace('{1}', p.title); // <tr><td>1222</td><td>글내용</td><td>{2}</td><td>{3}</td><td>{4}</td></tr>
		row = row.replace('{2}', p.writer);// <tr><td>1222</td><td>글내용</td><td>ppoo</td><td>{3}</td><td>{4}</td></tr>
		row = row.replace('{3}', p.content);
		row = row.replace('{4}', p.date);
		row = row.replace('{5}', p.view);
		trContent += row;
	}
	
	$('#posting tr:last').after(trContent);
}


</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
<body>
<div>
	<div><span>2</span> of <span>32</span></div>
     <table class="table" id="posting">
     	<tbody>
		<tr class="header">
			<td>글번호</td>
			<td>제목</td>
			<td>작성자</td>
			<td>내용</td>
			<td>날짜</td>
			<td>조회수</td>
		</tr>
     	</tbody>
		<!-- 
    	<tr class="each">
    		<td>1900</td>
    		<td><a href="<%=request.getContextPath()%>/board/read/1900">이것은 게시판 글이다.</a></td>
    		<td>pppo</td>
    		<td>20160514 12:33:12</td>
    		<td>1223</td>
    	</tr>
    	<tr class="each">
    		<td>1900</td>
    		<td>이것은 게시판 글이다.</td>
    		<td>pppo</td>
    		<td>20160514 12:33:12</td>
    		<td>1223</td>
    	</tr>
    	<tr class="each">
    		<td>1900</td>
    		<td>이것은 게시판 글이다.</td>
    		<td>pppo</td>
    		<td>20160514 12:33:12</td>
    		<td>1223</td>
    	</tr>
    	<tr class="each">
    		<td>1900</td>
    		<td>이것은 게시판 글이다.</td>
    		<td>pppo</td>
    		<td>20160514 12:33:12</td>
    		<td>1223</td>
    	</tr>
		 -->
    </table>
    
    
    
    <div align="center">
    <ul class="pagination">
  <li><a href="#">1</a></li>
  <li><a href="#">2</a></li>
  <li><a href="#">3</a></li>
  <li><a href="#">4</a></li>
  <li><a href="#">5</a></li>
	</ul>
	</div>
</div>
</body>
</html>