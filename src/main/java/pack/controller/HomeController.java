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
    @GetMapping("/") // 사용자가 웹사이트에 처음 접속하거나 홈 페이지를 요청하는 경우에 이 핸들러 매핑이 작동
    public String home(HttpSession session) {
        // 세션 유지 상황에 따라 리다이렉션 처리

        // 사용자 세션이 유지되고 있는지 확인
        if (session.getAttribute("userSession") != null) {
            // 사용자 세션이 있으면 "/usersessionkeep"로 리다이렉션
            return "redirect:/usersessionkeep";
        }
        // 공급자 세션이 유지되고 있는지 확인
        else if (session.getAttribute("ownerSession") != null) {
            // 공급자 세션이 있으면 "/ownersessionkeep"로 리다이렉션
            return "redirect:/ownersessionkeep";        
        }
        // 관리자 세션이 유지되고 있는지 확인
        else if (session.getAttribute("adminSession") != null) {
            // 관리자 세션이 있으면 "/adminsessionkeep"로 리다이렉션
            return "redirect:/adminsessionkeep";
        }
        // 어떠한 세션도 없는 경우, 기본 페이지로 이동
        return "index";      
    }   
}