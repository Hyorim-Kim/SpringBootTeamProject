package pack.controller.container;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import pack.controller.FormBean;
import pack.model.DataDao;

@Controller
public class SearchController {  // 조건을 검색했을 때 나오게 해주는 검색클래스
	@Autowired
	private DataDao dataDao;
	
	@PostMapping("search")
	public String search(FormBean bean, Model model) {  //넘어가니까 Model 사용
		ArrayList<pack.model.user.UserDto> slist = (ArrayList<pack.model.user.UserDto>)dataDao.getDataSearch(bean);
		model.addAttribute("lists", slist);
		return "../templates/user/user";
	}
	
	@PostMapping("search2")
	public String search2(FormBean bean, Model model) {  //넘어가니까 Model 사용
		ArrayList<pack.model.owner.OwnerDto> slist2 = (ArrayList<pack.model.owner.OwnerDto>)dataDao.getDataSearch2(bean);
		model.addAttribute("lists2", slist2);
		return "../templates/owner/owner";
	}
}
