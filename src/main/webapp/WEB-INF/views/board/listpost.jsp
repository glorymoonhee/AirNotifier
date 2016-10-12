<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">	
<title>로그인</title>
<jsp:include page="/WEB-INF/views/common/common.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
   
 
  $('#write_post').on('click',function(e){
	  
	   document.location.href = contextPath + '/board/write';
	  
  });
	 
 });
 
function loadPosting ( pagenum ) {
	var url = "<%=request.getContextPath()%>/board/api/posting/"+ pagenum;
	$.get(url, function(resp){
		 
	    console.log(resp.sucess+'   성공여부');
	
	    
		 // resp.success = true;
		  resp.paging.pagenum = pagenum; //대체 해야함;
		 
		  
		 // resp.postings = fake;
	 	  $('#posting tr:gt(0)').empty();
	   //  $('#posting tbody').empty(); //empty() #posting (제외) 한 하위필드와 text 다 삭제
	                              //remove() #posting 포함 다 삭제
		renderPostings(resp.paging.pagenum, resp.postings, resp.paging.pageSize, resp.paging.postSum);
		pagenate(resp.paging.startPageNo, resp.paging.endPageNo ,resp.paging.firstPageNo,resp.paging.prevPageNo,resp.paging.nextPageNo,resp.paging.finalPageNo);
	});	
}

function renderPostings (pagenum, postings, pageSize, postSum ) {
	
	var template = '<tr><td>{0}</td><td><a href="board/read/{pagenum}">{1}</a></td><td>{2}</td><td>{4}</td><td>{5}</td></tr>';
	var trContent = '';
	var k =  postSum-1 ;
	
	
	console.log('총개수'+postSum);
	console.log('페이지번호'+pagenum);
	console.log('페이지 사이즈 5가 나온다' + pageSize);

	for(var i= k ; i< k+pageSize ; i++){
              // i = 10, i< 15     10 11 12 13 14
              //                          만약 14번이 없으면...
             
		var p = postings[i];
           if (typeof(p) =='undefined'){
        	   continue;
           } 
              
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
	 var html ='<ul class="pagination">';
	 var pageinfo ='야야야';	 

	 
	 
	for(var i=st_no; i<=end_no; i++){
		
		 html +='<li><a href="javascript:loadPosting('+ i + ');">' + i + '</a></li>';
	}
	html +='</ul>'; 
	
	
	
	$("#pagenumbers").append(html);
}



</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>


 <c:if test="${not empty sessionScope.user}">
 
  <div>안녕하세요</div> 
 </c:if>
   <div> 
	<div align="left" style="display:inline">여기 페이지번호 나타내라</div>

	<div style="display:inline; float:right" ><button id="write_post" class="btn btn-info" >글쓰기</button></div>
	</div>



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
    







<div align="center" id="pagenumbers" >

</div>    
    


</body>
</html>