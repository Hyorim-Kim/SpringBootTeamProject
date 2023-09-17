package pack.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pack.model.user.UserDao;
import pack.model.user.UserDto;

// **** 광진 ****// 
/**** 광진  *****/
@Controller
public class UserController {
	
	@Autowired
	private UserDao userDao;
	
	// 메인페이지 헤더메뉴에서 회원가입 선택할 때
	@GetMapping("joinchoice")
	public String joinchoice() {
		return "joinchoice";
	}
	
	// 회원가입 선택란에서 사용자를 선택할 때 
	@GetMapping("userjoin")
	public String userchoice() {
		return "userjoin";
	}
	
	
	// 사용자 회원가입에서 가입 버튼을 클릭하고 성공 했을 때
	@PostMapping("userjoin")
	public String userloginOK(UserDto userDto) {
		boolean b = userDao.userinsertData(userDto);
		if(b) {
			return "userlogin";  
		} else {
			return "testfail";  
		}	
	}
	
	
	// 메인페이지에서 로그인을 클릭했을 때 수행
	@GetMapping("login")
	public String loginGo() {
		return "userlogin";
	}
	
	// 사용자로그인 페이지에서 공급자로그인을 클릭했을 때
	@GetMapping("/user/owner")
	public String ownerloginGo() {
		return "ownerlogin";
	}
	
    // 로그인 요청 처리
    @PostMapping("/user/login")
    public String processLoginForm(@RequestParam("user_id") String user_id,
            					   @RequestParam("user_pwd") String user_pwd,
            					   Model model,
            					   HttpSession session){
        // 사용자 로그인 처리
        UserDto user = userDao.userloginProcess(user_id, user_pwd);
        
        if (user != null) {
            // 로그인 성공
        	session.setAttribute("user", user); // 세션에 사용자 정보 저장
            return "userloginok"; // 로그인 성공 시 userloginok.html로 이동
            
        } else {
            return "testfail"; // 로그인 실패 시 userlogin.html로 이동하고 에러 메시지를 표시
        }
    }
    
    /*** 9/15일 추가 작업 (회원수정) 광진 ***/
    
	@GetMapping("/userupdate")
	public String userUpdatePage(Model model, HttpSession session) {
		// 세션에서 회원 정보를 가져와서 모델에 추가
		UserDto user = (UserDto) session.getAttribute("user");
		model.addAttribute("user", user);

		return "userupdate"; // 회원 수정 페이지로 이동
	}
	
	@PostMapping("/userinfoupdate")
	public String userInfoupdate(UserDto userDto, Model model, HttpSession session) {
		boolean b = userDao.userupdate(userDto);
		if(b) {
			UserDto user = (UserDto) session.getAttribute("user");
			model.addAttribute("user", user);
			return "updateok";  
		} else {
			return "testfail";  
		}
	}
	
}
