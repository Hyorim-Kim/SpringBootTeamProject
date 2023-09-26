package pack.controller.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class ErrorController {
	@GetMapping("error")
	public String err() {
		return "board/error";
	}
}
