//package pack.controller.admin;
//
//import java.util.ArrayList;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import pack.controller.FormBean;
//import pack.model.DataDao;
//import pack.model.owner.OwnerDto;
//import pack.model.user.UserDto;
//
//@Controller
//public class SearchController {  // 조건을 검색했을 때 나오게 해주는 검색클래스
//	@Autowired
//	private DataDao dataDao;
//	
//	private int tot;        // 전체 FAQ 레코드 수를 저장하는 멤버 변수, 이 변수는 페이지 수 계산을 위해 사용
//	private int plist = 10;  //  페이지당 행 수를 나타내는 멤버 변수로, 한 페이지에 몇 개의 FAQ 항목을 표시할지를 결정
//	
//
//	
//	
//	@PostMapping("search2")
//	public String search2(@RequestParam("page")int page, FormBean bean, Model model) {  //넘어가니까 Model 사용
//		int spage = page;
//	    if (page <= 0) spage = 1 ;
//		
//		ArrayList<OwnerDto> slist2 = (ArrayList<OwnerDto>)dataDao.getOwnerSearch(bean);
//		ArrayList<OwnerDto> result = getownerListData(slist2, spage);
//		model.addAttribute("lists2", slist2);
//		return "../templates/owner/owner";
//	}
//}
