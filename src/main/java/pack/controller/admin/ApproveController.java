package pack.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.model.DataDao;
import pack.model.admin.AdminBean;
import pack.model.admin.AdminDao;
import pack.model.container.ContainerDto;


@Controller
public class ApproveController {
	@Autowired
	private DataDao dataDao;
	@Autowired
	private AdminDao adminDao;
	
	@GetMapping("/detail")
	public String detail(@RequestParam("cont_no")String cont_no, Model model) {
		ContainerDto containerDto = dataDao.condetail(cont_no); 
		model.addAttribute("containerDto",containerDto);
		return "../templates/admin/cont_approve";
	}
	
	
	@PostMapping("/apprProcess")
	public String approveProcess(AdminBean bean) {
		System.out.println("bean" + bean); 
		boolean b = adminDao.approve(bean);
		 System.out.println("b" + b); 
		if (b)
			return "redirect:/registered"; // 수정 후 목록보기
		else
			return "error";
	}
	
	@PostMapping("/denyProcess")
	public String denyProcess(AdminBean bean) {
		System.out.println("bean" + bean); 
		boolean b = adminDao.deny(bean);
		 System.out.println("b" + b); 
		if (b)
			return "redirect:/registered"; // 수정 후 목록보기
		else
			return "error";
	}
}
