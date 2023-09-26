package pack.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.model.DataDao;
import pack.model.container.ContainerDto;

@RequestMapping("approve")
@Controller
public class ApproveController {
	@Autowired
	private DataDao dataDao;
	
	@GetMapping("detail")
	public String detail(@RequestParam("cont_no")String cont_no, Model model) {
		ContainerDto containerDto = dataDao.detail(cont_no);
		model.addAttribute("containerDto",containerDto);
		return "admin/cont_approve";
	}
	@GetMapping("/process")
	public String goProcess() {
		return "cont_approve";
	}
}
