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



var contextPath = '<%=request.getContextPath()%>';

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
   
/*   $('.pagination').on('click',function(e){
	  
	  var ul = $(e.target).parent().parent(); // .closes( 'ul' )
	  ul.children().removeClass('active');
	  $(e.target).parent().addClass('active');
	  console.log('pnum click', $(e.target).text());
	  var pagenum = $(e.target).text();
	  
	  loadPosting(pagenum);
  }); */
 
  $('#write_post').on('click',function(e){
	  
	   document.location.href = contextPath + '/board/write';
	  
  });
	 
 });
 
function loadPosting ( pagenum ) {
	var url = "<%=request.getContextPath()%>/board/api/posting/"+ pagenum;
	$.get(url, function(resp){
		 
	    console.log(resp.sucess+'   성공여부');
	
	    
		 // resp.success = true;
		  resp.pagenum = 1; //대체 해야함;
		 // resp.postings = fake;
		  
		renderPostings(resp.pagenum, resp.postings,resp.paging.pageSize,resp.paging.postSum);
		// $("#pagenumbers").remove();
		pagenate(resp.paging.startPageNo, resp.paging.endPageNo ,resp.paging.firstPageNo,resp.paging.prevPageNo,resp.paging.nextPageNo,resp.paging.finalPageNo);
	});	
}

function renderPostings (pagenum, postings, pageSize, postSum ) {
	
	var template = '<tr><td>{0}</td><td><a href="board/read/{pagenum}">{1}</a></td><td>{2}</td><td>{4}</td><td>{5}</td></tr>';
	var trContent = '';
	for(var i= postSum-1 ; i< pageSize ; i++){
		var p = postings[i];
		var row  = template.replace('{0}', p.seq); //<tr><td>1222</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td></tr>
		row = row.replace('{1}', p.title); // <tr><td>1222</td><td>글내용</td><td>{2}</td><td>{3}</td><td>{4}</td></tr>
		row = row.replace('{2}', p.writer);// <tr><td>1222</td><td>글내용</td><td>ppoo</td><td>{3}</td><td>{4}</td></tr>
		row = row.replace('{4}', p.dateTime);
		row = row.replace('{5}', p.viewcount);
		row = row.replace('{pagenum}', p.seq);
		trContent += row;
	}
	
	$('#posting tr:last').after(trContent);
}


 function pagenate(st_no, end_no,firstpage,prevpage,nextpage,finalpage){
	 $("#pagenumbers").empty();
	 //location.reload(); 
	  var html ='<ul class="pagination">';
	for(var i=st_no; i<=end_no; i++){
	 
		
		 html +='<li><a href="javascript:loadPosting('+ i + ');">' + i + '</a></li>';
	}
	html +='</ul>'; 
	$("#pagenumbers").append(html);
}

 
 
 /*
	  var html ='<ul class="pagination"><li><a href="#">'+firstpage+'</a></li><li><a href="#">'+prevpage+'</a></li>';
	for(var i=st_no; i<=end_no; i++){
		 html +='<li><a href="#">'+ i +'</a></li>';
	}
	html +='<li><a href="#">'+nextpage+'</a></li><li><a href="#">'+finalpage+'<a/></li></ul>'; 
	$("#pagenumbers").append(html);
}
 
 
 
 */
 
 
 
 
 
/* function goPage(page,year) {
 if( year >= '2000' ){ // 년도체크
            location.href = "etc08.jsp?div=08&page=" + page + "&year=" + year;     
            alert("----통과----");
 } else {
     location.href = "etc08.jsp?div=08&page=" + page + "&year=";
 }
} */


</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>


	<div><span>2</span> of <span>32</span></div>
     <table class="table" id="posting">
     	<tbody>
		<tr class="header">
			<td>글번호</td>
			<td>제목</td>
			<td>작성자</td>
			
			<td>날짜</td>
			<td>조회수</td>
		</tr>
     	</tbody>
     	
     	
		
    </table>
      <button id="write_post">글쓰기</button>






<div align="center" id="pagenumbers" >

</div>    
    


</body>
</html>