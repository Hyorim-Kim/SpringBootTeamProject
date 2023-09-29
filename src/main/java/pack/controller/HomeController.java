package pack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import pack.model.admin.AdminDto;
import pack.model.owner.OwnerDto;
import pack.model.user.UserDto;

//templates 폴더 내의 파일은 URL에서 직접 접근할 수 없으므로 컨트롤러를 통해 접근, index.html을 호출
@Controller


public class HomeController {
	
	// 세션 유지 (광진 해냈다~~)
    @GetMapping("/") // "/" 가 메인홈페이지로 가는 핸들러 매핑
    public String index(HttpSession session) {
    	
    	// 사용자, 공급자, 관리자 세션이 잘 찍히는지 확인하기 위해 객체 생성
    	UserDto userSession = (UserDto) session.getAttribute("userSession");
    	OwnerDto ownerSession = (OwnerDto) session.getAttribute("ownerSession");
    	AdminDto adminSession = (AdminDto) session.getAttribute("adminSession");
    	
    	// 사용자 세션 유지
	    if (session.getAttribute("userSession") != null) {
	    	// 찍어보자
	    	System.out.println("사용자 로그인 후 메인페이지로 뒤로가기 하고 새로고침 했을 때");
	    	System.out.println("사용자 ID : " + userSession.getUser_id() + " " + "사용자 pwd : " + userSession.getUser_pwd());
	        return "redirect:/usersessionkeep";
	    // 공급자 세션 유지
	    } else if (session.getAttribute("ownerSession") != null) {
	    	// 찍어보자
	    	System.out.println("공급자 로그인 후 메인페이지로 뒤로가기 하고 새로고침 했을 때");
	    	System.out.println("공급자 사업자번호 : " + ownerSession.getBusiness_num() + " " + "공급자 비번 : " + ownerSession.getOwner_pwd());
	        return "redirect:/ownersessionkeep";	        
	     // 관리자 세션 유지
	    } else if (session.getAttribute("adminSession") != null) {
	    	// 찍어보자
	    	System.out.println("관리자 로그인 후 메인페이지로 뒤로가기 하고 새로고침 했을 때");
	    	System.out.println("관리자 Id : " + adminSession.getAdmin_id() + " " + "관리자 비번 : " + adminSession.getAdmin_pwd());
	        return "redirect:/adminsessionkeep";
	    }
	   
        return "index";
    }
}
