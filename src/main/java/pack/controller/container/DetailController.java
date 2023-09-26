package pack.controller.container;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.model.DataDao;
import pack.model.container.ContainerDto;



@Controller
public class DetailController {
	@Autowired
	private DataDao dataDao;
	
	@GetMapping("detail")
	public String detail(@RequestParam("cont_no")String cont_no, Model model) {
		ContainerDto containerDto = dataDao.condetail(cont_no);
		model.addAttribute("containerDto",containerDto);
		return "../templates/container/detail";
	}
}

