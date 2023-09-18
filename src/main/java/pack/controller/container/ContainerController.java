package pack.controller.container;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.controller.FormBean;
import pack.model.container.ContainDao;
import pack.model.container.ContainerDto;


@Controller
@RequestMapping(value = "owner")
public class ContainerController {
	
// ******* 민혁 ************
    
    @Autowired
	private ContainDao ContainDao;

	@GetMapping("/ownerMain")
	public String main() {
		return "owner/ownerMain";
	}

	@GetMapping("/paid")
	public String cont_pay() {
		return "owner/container_paid";
	}

	@GetMapping("/reserve")
	public String cont_book() {
		return "owner/container_reserve";
	}

	@GetMapping("/register")
	public String cont_regs() {
		return "owner/container_register";
	}
	
	

	@GetMapping("/manage")
	public String cont_mgr(Model model) {
		// 창고관리 페이지로 매핑해주는 메소드 리스트값을 달고 가서 반복문을 통해 테이블에 값들을 밀어넣어줌
		ArrayList<ContainerDto> clist = (ArrayList<ContainerDto>)ContainDao.getDataAll();
		model.addAttribute("datas", clist);

		return "owner/container_manage";
	}

	@GetMapping("/detail")
	public String conDetail(@RequestParam("cont_no") String cont_no, Model model) {
		ContainerDto conDto = ContainDao.conDetail(cont_no);
		model.addAttribute("conDto", conDto);

		return "owner/container_detail";

	}

	@GetMapping("insert")
	// @RequestMapping(value="insert", method=RequestMethod.GET)
	// 창고관리(목록)페이지에서 창고등록 페이지로 넘어가는 링크 매핑
	// 창고관리 페이지에서 a th:href="@{/insert}" 요거 타고 들어옴
	public String insertContainer() {
		return "owner/container_register";
	}

	@PostMapping("insert")
	// @RequestMapping(value = "insert", method = RequestMethod.POST)
	// 인서트 페이지에서 폼-액션태그의 insert인가? post 방식으로 값 전달 받고..?
	public String insertSubmit(FormBean bean) {
		// 폼-액션태그에서 입력된 값을 bean에 밀어넣어주고
		boolean b = ContainDao.insertContainer(bean);
		// dao에서 bean값을 처리해 boolean b값을 받아서
		// 아래 if문을 통해 b가 true일때(bean에 값이 제대로 들어갔을때)
		// insert 쿼리문을 수행하고 manage 페이지로 돌아감
		// b가 false라면(bean에 값이 제대로 안들어갔을때)
		// insert 쿼리문을 수행하지 않고 error 페이지로 이동
		if (b) {
			return "redirect:/owner/manage";
			// 추가 후 목록보기
			// forwarding 하게 되면 서버에서 서버를 그냥 불러버려서 select를 만날 수 가 없대
		} else {
			return "error"; // 이거슨 포워딩
		}

	}
	
	@RequestMapping("update")
	public String update(FormBean bean) {
		boolean b = ContainDao.update(bean);
		if(b)
			return "redirect:/owner/manage";   // 수정 후 목록보기
		else return "error";
	}
	
	@GetMapping("delete")
	public String delete(@RequestParam("cont_no") String cont_no) {
		boolean b = ContainDao.delete(cont_no);
		if(b)
			return "redirect:/owner/manage";   // 수정 후 목록보기
		else return "error";
	}
}
