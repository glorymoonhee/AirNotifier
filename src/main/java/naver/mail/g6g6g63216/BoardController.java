package naver.mail.g6g6g63216;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import naver.mail.g6g6g63216.dao.BoardDao;
import naver.mail.g6g6g63216.service.Paging;
import naver.mail.g6g6g63216.vo.PostingVO;
@Controller
public class BoardController {

	
	@Autowired
	private BoardDao dao;
	
	 
	@RequestMapping(value = "/board", method = RequestMethod.GET)
    public String board(Model model){

		
		return "/board/listpost"; //페이지이동
	}
	
	
	@RequestMapping(value = "/board/write", method = RequestMethod.GET)
    public String board_write(Model model){
		
		return "/board/write";
	}
	
	
	@RequestMapping(value = "/board/doWrite", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public @ResponseBody String doWrite(Model model,HttpServletRequest request){
		  
		  String title = request.getParameter("title");
		  String writer = request.getParameter("writer");
		  String content = request.getParameter("content");
		  
		  System.out.println(title + ", " + writer + ", " + content);
		  dao.insertPost(title, writer, content);
		  System.out.println("OK!!!");
		return "{}";
	}
	
	
	@RequestMapping(value = "/board/read/{postseq}", method = RequestMethod.GET )
    public  String doRead(Model model,HttpServletRequest request, @PathVariable(value="postseq") Integer postseq){
		System.out.println("pnum: " + postseq);
		PostingVO post = dao.findBySeq(postseq);
		request.setAttribute("post", post);
		System.out.println(post.getDateTime());
		
		
		return "/board/read";
	}
	
	
	
	@RequestMapping(value ="/board/api/posting/{pnum}", method = RequestMethod.GET, produces="application/json; charset=UTF-8")
	public @ResponseBody  Map<String, Object> postingByPage( @PathVariable(value="pnum") Integer pnum){
		
		int total_count = dao.getCountPostings();
	   	 Paging paging = new Paging();
	     paging.setPageNo(pnum);
	     paging.setPageSize(5);
	     paging.setTotalCount(total_count);
	     paging.setPostSum(5 * (pnum-1) + 1);
	    // this.setPostSum( pageSize * (pageNo-1) + 1 );
		
		System.out.println(" page num : " + pnum );
		System.out.println(" count 번호 " + paging.getPostSum());
		
		
		List<PostingVO> p = dao.readAllpostings();
		Map<String, Object> resp = new HashMap<String, Object>();
		resp.put("sucess", Boolean.TRUE);
		resp.put("postings", p);
		resp.put("paging",paging);
	
		  return resp;
	}
	
}
