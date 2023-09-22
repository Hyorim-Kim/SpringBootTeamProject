package pack.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pack.model.DataDao;
import pack.model.container.ContainerDto;
import pack.model.owner.OwnerDto;
import pack.model.user.UserDto;

@Controller
public class ListController {  // 리스트 목록 보게 도와주는 컨트롤러 클래스
	@Autowired
	private DataDao dataDao; //model로 감
	
	@GetMapping("/user") //dispathcher에서 위임받은 handlemapper가 ..
	public String listProcess(Model model) {
		ArrayList<UserDto> slist = (ArrayList<UserDto>)dataDao.getDataAll();
		model.addAttribute("lists", slist);
		return "../templates/user/user";
	}
	
	@GetMapping("/owner")
	public String listProcess2(Model model) {
		ArrayList<OwnerDto> slist2 = (ArrayList<OwnerDto>)dataDao.getDataAll2();
		model.addAttribute("lists2", slist2);
		return "../templates/owner/owner";
	}
	
	@GetMapping("/registered")
	public String listProcess3(Model model) {
		ArrayList<ContainerDto> slist3 = (ArrayList<ContainerDto>)dataDao.getDataAll3();
		model.addAttribute("lists3", slist3);
		return "../templates/container/registered";
	}

}
