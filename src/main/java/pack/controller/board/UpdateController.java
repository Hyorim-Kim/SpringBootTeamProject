package pack.controller.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.model.board.BoardDaoImpl;
import pack.model.board.BoardDto;

@Controller
@RequestMapping("/list")
public class UpdateController {
	@Autowired
	private BoardDaoImpl daoImpl;
	
	@GetMapping("update")
	public String edit(@RequestParam("num")String num, 
			@RequestParam("page")String page,
			Model model) {
		// 수정 대상 자료 읽기
		BoardDto dto = daoImpl.detail(num);
		
		model.addAttribute("data", dto);
		model.addAttribute("page", page);
		
		return "update";
	}
	
	@PostMapping("update")
	public String editProcess(BoardBean bean, 
			@RequestParam("page") String page, 
			Model model) {
		// 비밀번호 확인을 위해 DB에서 비밀번호 읽기
		String pass = daoImpl.selectPass(Integer.toString(bean.getNum()));
		System.out.println("bean.getPass:" + bean.getAdmin_pwd() + " pass : " + pass);
		if(bean.getAdmin_pwd().equals(pass) || bean.getAdmin_pwd()==pass) {  // 비밀번호 비교 
			boolean b = daoImpl.update(bean);
			if(b) {
				// 상세보기로 이동
				//return "redirect:detail?num=" + bean.getNum() + "&page=" + page;
				
				// 목록보기로 이동
				return "redirect:list?page=" + page;
			}else {
				return "redirect:error";
			}
		}else{
			model.addAttribute("msg", "비밀번호 불일치~");
			model.addAttribute("page", page);
			return "update_err";
		}
	}
}
