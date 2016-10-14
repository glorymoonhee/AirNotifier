package naver.mail.g6g6g63216;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import naver.mail.g6g6g63216.dao.BoardDao;
import naver.mail.g6g6g63216.service.Paging;
import naver.mail.g6g6g63216.vo.PostingVO;
import naver.mail.g6g6g63216.vo.UserVO;
@Controller
public class BoardController {

	
	@Autowired
	private BoardDao dao;
	

	 
	@RequestMapping(value = "/board", method = RequestMethod.GET)
    public String board(Model model){

		
		return "/board/listpost"; //페이지이동
	}
	
	
	@RequestMapping(value = "/board/write", method = RequestMethod.GET)
    public String board_write(Model model,HttpSession session, HttpServletRequest req){
		/*
		  UserVO loginUser = (UserVO) session.getAttribute("user");
		  
		  if(loginUser ==null){
			 
			  session.setAttribute("nextUri", req.getRequestURI());
			  return "redirect:/login";
		  }
		  */
//		session.invalidate();
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
	
	
	@RequestMapping(value = "/board/doDelete/{postseq}", method = RequestMethod.GET, produces="application/json; charset=UTF-8")
    public @ResponseBody String doDelete(Model model,HttpServletRequest request,@PathVariable(value="postseq") Integer postseq){
		  
                         		
		int n = dao.deletePost(postseq);
	    System.out.println(n+"n의 개수는뭐냐");
	    return "{\"success\": true}";
		//질문 pathvariable 없이 get(url,data,function )request로 받을 수 잇아
	}
	
	
	
	
	
	@RequestMapping(value = "/board/read/{postseq}", method = RequestMethod.GET )
    public  String doRead(Model model,HttpServletRequest request, @PathVariable(value="postseq") Integer postseq){
		PostingVO post = dao.findBySeq(postseq);
		UserVO user = dao.findUserByseq(postseq);
		System.out.println(user.getName()+ "Boardcontroller 부분에서 나오는 user.getnName");
		request.setAttribute("post", post);
		request.setAttribute("user", user); //model 형식으로 보내도 됨;

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

		
		List<PostingVO> p = dao.readAllpostings();
		Map<String, Object> resp = new HashMap<String, Object>();
		resp.put("sucess", Boolean.TRUE);
		resp.put("postings", p);
		resp.put("paging",paging);
	  
		  return resp;
	}
	
}
