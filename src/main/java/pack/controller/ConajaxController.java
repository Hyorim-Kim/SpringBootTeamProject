package pack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import pack.model.user.UserDto;

//templates 폴더 내의 파일은 URL에서 직접 접근할 수 없으므로 컨트롤러를 통해 접근, conajax.html을 호출
@Controller
public class ConajaxController {
    @GetMapping("/conajax")
    public String index(HttpSession session) {
       UserDto userSession = (UserDto) session.getAttribute("userSession");
       // 만약 세션에 사용자 정보가 있는 경우
       if (userSession != null) {
           // 찍어보자
           System.out.println("사용자 ID : " + userSession.getUser_id() + " " + "사용자 pwd : " + userSession.getUser_pwd());
           // 사용자 정보를 유지하면 로그인페이지에서 home 버튼을 눌러도 로그인 세션이 있는채로 마이페이지로 이동.
           return "conajax";
       }                              
       else {
           // 세션에 사용자 정보가 없는 경우에는 index로 이동.
           return "user-login";
       }
    }
}
