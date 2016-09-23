package naver.mail.g6g6g63216;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class BoardController {
	
	
	@RequestMapping(value = "/board", method = RequestMethod.GET)
    public String board(Model model){
		
		return "/board/listpost";
	}
	
	@RequestMapping(value ="/board/api/posting/{pnum}", method = RequestMethod.GET,produces="application/json; charset=UTF-8")
	public @ResponseBody String postingByPage( @PathVariable(value="pnum") Integer pnum){
		
		System.out.println(" page num : " + pnum );
		return "{}";
		
	}
	
	
}
