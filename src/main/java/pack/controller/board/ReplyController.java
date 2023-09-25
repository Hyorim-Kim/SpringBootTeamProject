package pack.controller.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;
import pack.model.board.BoardDaoImpl;

@Controller
@RequestMapping("/board")
public class ReplyController {
	@Autowired
	private BoardDaoImpl daoImpl;
	
	@GetMapping("reply")
	public String reply(@RequestParam("num")String num, 
			@RequestParam("page")String page,
			Model model) {
		model.addAttribute("data", daoImpl.detail(num));
		model.addAttribute("page", page);
		return "board/reply";
	}
	
	@PostMapping("reply")
	public String replyProcess(BoardBean bean, @RequestParam("page")String page){
		// onum 갱신
		bean.setOnum(bean.getOnum() + 1);
		daoImpl.updateOnum(bean);
		
		// 댓글 저장
		// Spring 에서 client ip 가져오는 법
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String ip = req.getHeader("X-FORWARDED-FOR");
		if (ip == null) ip = req.getRemoteAddr();
		// ------------------------------------------------
		bean.setBdate();
		bean.setNum(daoImpl.currentNum() + 1);
		bean.setNested(bean.getNested() + 1);
		
		if(daoImpl.insertReply(bean)) {
			return "redirect:list?page=" + page;
		}else{
			return "redirect:error";
		}
	}
}
