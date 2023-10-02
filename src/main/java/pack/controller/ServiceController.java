package pack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

//templates 폴더 내의 파일은 URL에서 직접 접근할 수 없으므로 컨트롤러를 통해 접근, service.html을 호출
@Controller
public class ServiceController {
	// 광진
    @GetMapping("/service")
    public String service(HttpSession session) {
    	// 사용자 세션 유지
	    if (session.getAttribute("userSession") != null) {
	        return "redirect:/usersessionkeep";
	    // 공급자 세션 유지
	    } else if (session.getAttribute("ownerSession") != null) {
	        return "redirect:/ownersessionkeep";	        
	     // 관리자 세션 유지
	    } else if (session.getAttribute("adminSession") != null) {
	        return "redirect:/adminsessionkeep";
	    }	   
        return "service";
    }
}
