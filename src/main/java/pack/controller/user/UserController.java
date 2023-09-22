package pack.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import pack.model.user.UserDao;
import pack.model.user.UserDto;

// **** 광진 ****// 
@Controller
public class UserController {
	
	@Autowired
	private UserDao userDao;
	
	// 메인페이지 헤더메뉴에서 회원가입 선택할 때 (성공)
	@GetMapping("joinChoiceGo")
	public String joinchoiceGo() {
	    return "../templates/user/joinchoice"; 
	}
	
	// 메인페이지에서 로그인을 클릭했을 때 수행 (성공)
	@GetMapping("firstLogin")
	public String loginGo() {
		return "../templates/user/userlogin";
	}
	
	// 사용자 로그인 페이지에서 회원가입 링크를 클릭했을 때 (성공) 
	@GetMapping("userJoinGo")
	public String userJoinGo() {
		return "../templates/user/userjoin";
	}
	
	// 회원가입 선택란에서 사용자를 선택할 때 (성공)
	@GetMapping("userJoinChoiceGo")
	public String userchoice() {
		return "../templates/user/userjoin";
	}
	
	
	// 사용자 회원가입에서 가입 버튼을 클릭하고 성공 했을 때 (성공)
	@PostMapping("userJoinClick")
	public String userloginOK(UserDto userDto) {
		boolean b = userDao.userInsertData(userDto);
		
		if(b) {
			return "../templates/user/userlogin";  
		} else {
			return "../templates/user/userjoin";  
		}	
	}
		
	// 사용자로그인 페이지에서 공급자로그인을 클릭했을 때 (성공)
	@GetMapping("ownerlogingo")
	public String ownerloginGo() {
		return "../templates/owner/ownerlogin";
	}
	
    // 사용자 로그인 페이지에서 요청 처리 (성공)
    @PostMapping("/userLogSuccess")
    public String processLoginForm(@RequestParam("user_id") String user_id,
            					   @RequestParam("user_pwd") String user_pwd,
            					   Model model, HttpSession session){
        // 사용자 로그인 처리
        UserDto user = userDao.userloginProcess(user_id, user_pwd);
        
        if (user != null) {
            // 로그인 성공과 동시에 세션에 사용자 정보 저장
        	session.setAttribute("user", user); 
        	session.setAttribute("user_name", user.getUser_name());
            return "../templates/user/usermypage"; // 로그인 성공 시 usermypage.html로 이동.
            
        } else {
            return "../templates/user/userlogin"; // 로그인 실패 시 userlogin.html로 이동.
        }
    }
    
    /*** 9/15일 추가 작업 (회원수정) 광진 ***/
    
    // 사용자 마이페이지에서 회원수정을 클릭했을 때 (성공)
	@GetMapping("/userupdate")
	public String userUpdatePage(Model model, HttpSession session) {
		// 세션에서 회원 정보를 가져와서 모델에 추가
		UserDto user = (UserDto) session.getAttribute("user");
		model.addAttribute("user", user);

		return "../templates/user/userupdate"; // 회원 수정 페이지로 이동
	}
	
	// 회원수정 페이지에서 회원수정을 클릭했을 때 (일단 성공이지만 세션값 최신으로 유지되게 설정하기)
	@PostMapping("/userInfoUpdate")
	public String userInfoupdate(UserDto userDto, Model model, HttpSession session) {
		boolean b = userDao.userupdate(userDto);
		if(b) {
			UserDto user = (UserDto) session.getAttribute("user");
			model.addAttribute("user", user);
			return "../templates/user/userlogin";  
		} else {
			return "../templates/user/usermypage";  
		}
	}
	
	 /*** 9/18일 추가 작업 (회원탈퇴) 광진 ***/
	
	
    // 사용자 마이페이지에서 회원삭제을 클릭했을 때 (성공)
	@GetMapping("/userdelete")
	public String userDeletePage(Model model, HttpSession session) {
		// 세션에서 회원 정보를 가져와서 모델에 추가
		UserDto user = (UserDto) session.getAttribute("user");
		model.addAttribute("user", user);

		return "../templates/user/userdelete"; // 회원 수정 페이지로 이동
	}
	
	// 회원삭제 페이지에서 버튼을 클릭할 때 수행 (광진)
	@PostMapping("/userInfoDelete")
	public String userInfoDelete(UserDto userDto, Model model, HttpSession session) {
		boolean b = userDao.userdelete(userDto);
		if(b) {
			UserDto user = (UserDto) session.getAttribute("user");
			model.addAttribute("user", user);
			return "../templates/user/userlogin";  
		} else {
			return "../templates/user/userdelete";  
		}
	}
	
	/*** 9/19일 추가 작업 사용자 마이페이지에서 로그아웃 하기 (광진) ***/
	@GetMapping("/userlogoutgo")
	public String userLogoutProcess(HttpSession session) {
	    session.removeAttribute("user"); // 세션 유지 종료
	    return "redirect:/"; // 로그아웃 클릭시 메인 홈페이지로 이동 
	}
	
	// 사용자 회원가입시 아이디 중복체크 (광진)
	@ResponseBody
	@PostMapping("/userIdCheck")
	public int IdCheck(@RequestParam("user_id") String user_id) {
		int result = userDao.userIdCheck(user_id);
		return result;
		
	}
	
	// 예약페이지에서 마이페이지로 돌아가기
	@GetMapping("/usermypageback")
	public String userBack(HttpSession session) {
		session.getAttribute("user");
		return "../templates/user/usermypage";
	}

		
}
