package pack.controller.admin;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pack.controller.FormBean;
import pack.model.DataDao;
import pack.model.container.ContainerDto;
import pack.model.owner.OwnerDto;
import pack.model.user.UserDto;

@Controller

public class ListController {  // 리스트 목록 보게 도와주는 컨트롤러 클래스
   @Autowired
   private DataDao dataDao; //model로 감
   
   private int tot;        // 전체 FAQ 레코드 수를 저장하는 멤버 변수, 이 변수는 페이지 수 계산을 위해 사용
   private int plist = 10;  //  페이지당 행 수를 나타내는 멤버 변수로, 한 페이지에 몇 개의 FAQ 항목을 표시할지를 결정
   
   public ArrayList<UserDto> getuserListData(ArrayList<UserDto> list, int page){ // 페이지 번호(page)와 FAQ 목록(list)을 받아와서 해당 페이지에 표시할 FAQ 항목을 추출하여 반환하는 메서드, 페이징 처리를 위해 사용
         ArrayList<UserDto> result = new ArrayList<UserDto>();
         
         int start = (page - 1) * plist;   // 현재 페이지에서 표시할 FAQ 항목의 시작 인덱스를 계산
         int end = Math.min(start + plist, list.size());  // 페이지에 표시할 FAQ 항목의 끝 인덱스를 계산하며, 리스트 크기를 초과하지 않도록 조정
         
         for (int i = start; i < end; i++) {
            result.add(list.get(i));
         }
         return result; // 페이지에 표시할 FAQ 항목을 담을 ArrayList를 초기화하고, 해당 페이지의 FAQ 항목을 복사하여 반환
      }
      
      public int getuserPageSu() { // 총 user 페이지 수 얻기
         tot = dataDao.totalUser();
         int pagesu = tot / plist;
         if(tot % plist > 0) pagesu += 1;
         // FAQ 데이터베이스에 있는 전체 FAQ 레코드 수를 조회하고, 페이지당 행 수(plist)로 나눈 후 나머지가 있으면 페이지 수를 1 증가시켜 반환
         return pagesu;
      }

   @GetMapping("/user")   // user 페이지로 이동
   public String userlist(@RequestParam("page")int page, Model model) {
      int spage = page;
       if (page <= 0) spage = 1;
       
      ArrayList<UserDto> slist = (ArrayList<UserDto>)dataDao.getUserAll();
      ArrayList<UserDto> result = getuserListData(slist, spage);

      
      int user_records = dataDao.usercount(); // user 전체 레코드 수

      model.addAttribute("lists", result);
      model.addAttribute("pagesu", getuserPageSu());
      model.addAttribute("page", spage);
      model.addAttribute("user_records", user_records);
       
      return "../templates/user/user";
   }
   
   @PostMapping("usersearch")  // user에서 검색하기
   public String usersearch(@RequestParam(name = "page", required = false, defaultValue = "1")int page,FormBean bean, Model model) {  //넘어가니까 Model 사용
      int spage = page;
       if (page <= 0) spage = 1 ;
      ArrayList<UserDto> userlist = (ArrayList<UserDto>)dataDao.getUserSearch(bean);
      ArrayList<UserDto> userresult = getuserListData(userlist, spage);
      
      model.addAttribute("lists", userresult);
      model.addAttribute("pagesu", getuserPageSu());
       model.addAttribute("page", spage);
      return "../templates/user/user";
   }
   
   @PostMapping("userdelete")  // user에서 삭제하기
   public String userdel(@RequestParam("user_id")String user_id,
         @RequestParam(name = "page", defaultValue = "1") int page) {
      if(dataDao.userdelete(user_id))
         return "redirect:user?page=" + page;
      else
         return "redirect:error";
   }

   public ArrayList<OwnerDto> getownerListData(ArrayList<OwnerDto> list, int page){ // 페이지 번호(page)와 FAQ 목록(list)을 받아와서 해당 페이지에 표시할 FAQ 항목을 추출하여 반환하는 메서드, 페이징 처리를 위해 사용
         ArrayList<OwnerDto> ownresult = new ArrayList<OwnerDto>();
         
         int start = (page - 1) * plist;   // 현재 페이지에서 표시할 FAQ 항목의 시작 인덱스를 계산
         int end = Math.min(start + plist, list.size());  // 페이지에 표시할 FAQ 항목의 끝 인덱스를 계산하며, 리스트 크기를 초과하지 않도록 조정
         
         for (int i = start; i < end; i++) {
            ownresult.add(list.get(i));
         }
         return ownresult; // 페이지에 표시할 FAQ 항목을 담을 ArrayList를 초기화하고, 해당 페이지의 FAQ 항목을 복사하여 반환
      }
      
      public int getownerPageSu() { // 총 owner 페이지 수 얻기
         tot = dataDao.totalOwner();
         int pagesu = tot / plist;
         if(tot % plist > 0) pagesu += 1;
         // FAQ 데이터베이스에 있는 전체 FAQ 레코드 수를 조회하고, 페이지당 행 수(plist)로 나눈 후 나머지가 있으면 페이지 수를 1 증가시켜 반환
         return pagesu;
      }
   



	@GetMapping("/owner")  // owner 페이지로 이동
	public String ownerlist(@RequestParam("page")int page, Model model) {
		int spage = page;
	    if (page <= 0) spage = 1;
		
		ArrayList<OwnerDto> ownerlist = (ArrayList<OwnerDto>)dataDao.getOwnerAll();
		ArrayList<OwnerDto> ownerresult = getownerListData(ownerlist, spage);
		
		int owner_records = dataDao.getownerrecords(); // 전체 레코드 수
		
		model.addAttribute("lists2", ownerresult);
		model.addAttribute("pagesu", getownerPageSu());
		model.addAttribute("page", spage);
		model.addAttribute("owner_records",owner_records);
		return "../templates/owner/owner";
	}
	
	@PostMapping("ownersearch")  // owner에서 검색하기
	public String ownersearch(@RequestParam(name = "page", required = false, defaultValue = "1")int page, FormBean bean, Model model) {  //넘어가니까 Model 사용
		int spage = page;
	    if (page <= 0) spage = 1 ;
		
		ArrayList<OwnerDto> slist2 = (ArrayList<OwnerDto>)dataDao.getOwnerSearch(bean);
		ArrayList<OwnerDto> result = getownerListData(slist2, spage);
		
		model.addAttribute("lists2", result);
		model.addAttribute("pagesu", getownerPageSu());
		model.addAttribute("page", spage);
		
		return "../templates/owner/owner";
	}
	
//	@PostMapping("ownerdelete")  // owner에서 삭제하기
//	public String ownerdel(@RequestParam("business_num")String business_num,
//			@RequestParam(name = "page", defaultValue = "1") int page) {
//		if(dataDao.ownerdelete(business_num))
//			return "redirect:owner?page=" + page;
//		else
//			return "/owner/error.html";
//	}
	@PostMapping("ownerdelete")  // owner에서 삭제하기
	public String ownerdel(@RequestParam("business_num") String business_num,
	        @RequestParam(name = "page", defaultValue = "1") int page) {
	    try {
	        if (dataDao.ownerdelete(business_num)) {
	            return "redirect:owner?page=" + page;
	        } else {
	            return "redirect:error"; // 데이터 삭제에 실패한 경우
	        }
	    } catch (Exception e) {
	        return "/owner/error"; // 예외 발생 시 에러 페이지로 리다이렉트
	    }
	}


   public ArrayList<ContainerDto> getregisteredListData(ArrayList<ContainerDto> list, int page){ // 페이지 번호(page)와 FAQ 목록(list)을 받아와서 해당 페이지에 표시할 FAQ 항목을 추출하여 반환하는 메서드, 페이징 처리를 위해 사용
         ArrayList<ContainerDto> regresult = new ArrayList<ContainerDto>();
         
         int start = (page - 1) * plist;   // 현재 페이지에서 표시할 FAQ 항목의 시작 인덱스를 계산
         int end = Math.min(start + plist, list.size());  // 페이지에 표시할 FAQ 항목의 끝 인덱스를 계산하며, 리스트 크기를 초과하지 않도록 조정
         
         for (int i = start; i < end; i++) {
            regresult.add(list.get(i));
         }
         return regresult; // 페이지에 표시할 FAQ 항목을 담을 ArrayList를 초기화하고, 해당 페이지의 FAQ 항목을 복사하여 반환
      }
      
      public int getregisteredPageSu() { // 총 registered 페이지 수 얻기
         tot = dataDao.totalRegistered();
         int pagesu = tot / plist;
         if(tot % plist > 0) pagesu += 1;
         // FAQ 데이터베이스에 있는 전체 FAQ 레코드 수를 조회하고, 페이지당 행 수(plist)로 나눈 후 나머지가 있으면 페이지 수를 1 증가시켜 반환
         return pagesu;
      }
   
   
   @GetMapping("/registered")  // 등록된 창고 목록 출력
   public String registeredlist(@RequestParam(name = "page", required = false, defaultValue = "1") int page,Model model) {
      int spage = page;
       if (page <= 0) spage = 1;
       
      ArrayList<ContainerDto> slist3 = (ArrayList<ContainerDto>)dataDao.getConAll();
      ArrayList<ContainerDto> result = getregisteredListData(slist3, spage);
      
      model.addAttribute("lists3", result);
      model.addAttribute("pagesu", getregisteredPageSu());
       model.addAttribute("page", spage);
      return "../templates/admin/cont_registered";
   }
   
   @PostMapping("regsearch")  // registered에서 검색하기
   public String regsearch(@RequestParam(name="page", required = false, defaultValue = "1")int page, FormBean bean, Model model) {
      int spage = page;
      if(page <= 0) spage = 1;
      
      ArrayList<ContainerDto> slist3 = (ArrayList<ContainerDto>)dataDao.getRegSearch(bean);
      ArrayList<ContainerDto> result = getregisteredListData(slist3, spage);
      
      model.addAttribute("lists3", result);
      model.addAttribute("pagesu", getregisteredPageSu());
       model.addAttribute("page", spage);
      
      return "../templates/admin/cont_registered";
   }

}

