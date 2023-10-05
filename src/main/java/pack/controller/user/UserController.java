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
	
	// 필드 객체 선언을 통한 의존성 주입
	@Autowired
	private UserDao userDao;
	
	// 메인페이지에서 로그인을 클릭했을 때 수행 (성공)
	@GetMapping("/firstLogin")
	public String LoginGo(HttpSession session) {
		// 사용자 세션 유지
	    if (session.getAttribute("userSession") != null) {    	
	        return "redirect:/usersessionkeep";	   
	    }
	    return "user/userlogin";
	}
	
	// 사용자 로그인 페이지에서 회원가입 링크를 클릭했을 때 (성공) 
	@GetMapping("/userJoinGo")
	public String userJoinGo(HttpSession session) {
	    if (session.getAttribute("userSession") != null) {    	
	        return "redirect:/usersessionkeep";	   
	    }
		return "user/userjoin";
	}
		
	// 사용자 로그인 페이지에서 공급자로그인을 클릭했을 때 (성공)
	@GetMapping("/ownerlogingo")
	public String ownerLoginGo(HttpSession session) {
	    if (session.getAttribute("ownerSession") != null) {    	
	        return "redirect:/ownersessionkeep";	   
	    }
		return "../templates/owner/ownerlogin";
	}
	
	// 사용자 로그인 패이지에서 아이디/비밀번호 찾기 클릭했을 때 (광진)
	@GetMapping("/userInfoFind")
	public String userInfoFinding() {
		return "user/useridfind";
	}
	
	
	// 사용자 회원가입에서 가입 버튼을 클릭하고 성공 했을 때 (성공)
	@PostMapping("userJoinClick")
	public String userLoginOK(UserDto userDto) {
		boolean b = userDao.userInsertData(userDto);		
		if(b) {
			return "user/userlogin";  
		} else {
			return "user/userjoin";  
		}	
	}
		
	
    // 사용자 로그인 페이지에서 요청 처리 (성공)
	// 클라이언트가 요청한 유저 아이디과 유저 비밀번호를 문자열 형식으로 받고, 타임리프 형식으로 뷰에 데이터를 전달하기 위해 Model 객체와
    // 사용자의 세션 관리를 위해 HttpSession객체를 파라미터 형식으로 받는다.
    @PostMapping("/userLogSuccess")
    public String processLoginForm(@RequestParam("user_id") String user_id,
            					   @RequestParam("user_pwd") String user_pwd,
            					   HttpSession session) {
    	
    	// 필드 객체 선언을 통한 의존성 주입으로 userDao 객체의 메서드를 호출하여 사용자 정보를 가져와 UserDto 객체에 저장하는 역할
    	// user는 사용자가 입력한 user는 사용자가 입력한 아이디와 비밀번호를 담고 있다. 
        UserDto user = userDao.userLoginProcess(user_id, user_pwd);
        
        if (user != null) { 
        	// 사용자 정보가 있는 경우 로그인 성공과 동시에 세션에 사용자 정보 설정
        	session.setAttribute("userSession", user);
            // 여기서 가독성을 위해 세션 키의 이름은 userSession으로 변경 하였고 값 이름은 그대로 user로 유지.

            // user는 앞서 사용자가 입력한 아이디와 비밀번호를 담고 있음 그거를 세션에 담는거라 생각하시면 되용
        	session.setAttribute("userSession", user); 
            // user는 사용자가 입력한 아이디와 비밀번호를 담고 있음 그거를 세션에 담는거라 생각하시면 되용
        	session.setAttribute("user_id", user.getUser_id()); 
            System.out.println("사용자 ID : " + user.getUser_id() + " " + "사용자 pwd : " + user.getUser_pwd());
        	return "redirect:/usersessionkeep"; // 로그인 성공 시 usersessionkeep로 리다이렉션 

        }
        // 사용자 정보가 DB에 없는 경우 즉, 아이디와 비밀번호가 없는 경우
		else { 
			System.out.println("제대로 입력해라");
			return "user/userlogin"; // 로그인 실패 시 userlogin.html로 이동.
		}
    }
         
    // 사용자 마이페이지에서 회원수정을 클릭했을 때 (성공)
	@GetMapping("/userupdate")
	public String userUpdatePage(Model model, HttpSession session) {
		// 반환되는 값은 Object 타입이니 "userSession"이라는 이름으로 저장된 객체를 UserDto 타입으로 형변환하여 사용자 정보를 가져온다.
		// 세션에서 가져온 사용자 정보를 UserDto 객체로 다시 사용할 수 있다.
		UserDto user = (UserDto) session.getAttribute("userSession");
		// 세션에서 회원 정보를 가져와서 모델에 추가
		model.addAttribute("userSession", user);
		return "user/userupdate"; // 회원 수정 페이지로 이동
	}
	
	// 회원수정 페이지에서 회원수정을 클릭했을 때 (광진)
	@PostMapping("/userInfoUpdate")
	public String userInfoupdate(UserDto userDto, Model model, HttpSession session) {
		// 여기서 b는 userDao에서 선언된 userDataUpdate 메서드의 반환값을 담고 있다.
		boolean b = userDao.userDataUpdate(userDto);
		// 반환값이 true 일때
		if(b) {
			return "user/userlogin"; 
		// 반환값이 false 일때
		} else {
			return "user/usermypage";  
		}
	}
		
    // 사용자 마이페이지에서 회원삭제을 클릭했을 때 (성공)
	@GetMapping("/userdelete")
	public String userDeletePage(Model model, HttpSession session) {
		// 세션에서 회원 정보를 가져와서 모델에 추가
		UserDto user = (UserDto) session.getAttribute("userSession");
		// userdelete.html에서 세션에 저장된 사용자의 정보를 보여주기 위해 model값에 세션을 담는다.
		model.addAttribute("userSession", user);
		return "user/userdelete"; // 회원 수정 페이지로 이동
	}
	
	// 회원삭제 페이지에서 버튼을 클릭할 때 수행 (광진)
	@PostMapping("/userInfoDelete")
	public String userInfoDelete(UserDto userDto, Model model, HttpSession session) {
		// 여기서 b는 userDao에서 선언된 userDataDelete 메서드의 반환값을 담고 있다.
		boolean b = userDao.userDataDelete(userDto);
		// 반환값이 true 일때
		if(b) {
			// 회원 탈퇴를 했을 때 세션 유지가 되는 것을 방지하기 위해 작성
			session.removeAttribute("userSession");
			return "user/userlogin";  
		} else {
		// 반환값이 false 일때
			return "user/userdelete";  
		}
	}
	
	// 추가 작업 사용자 마이페이지에서 로그아웃 하기 (광진)
	@GetMapping("/userlogoutgo")
	public String userLogoutProcess(HttpSession session) {
		// 세션 유지 종료
	    session.removeAttribute("userSession"); 
	    return "redirect:/"; // 로그아웃 클릭시 핸들러 매핑인 "/"을 리다이렉트 한다. 
	}
	
	// 사용자 회원가입시 아이디 중복체크 (광진)
	@ResponseBody 
	@PostMapping("/userIdCheck")
	public int IdCheck(@RequestParam("user_id") String user_id) {
		int result = userDao.userIdCheck(user_id);
		return result;
	}
	
	// 사용자 아이디 찾기 (광진)
	@ResponseBody // @ResponseBody 사용함으로서 http요청 body를 자바 객체로 전달받을 수 있다.
	@PostMapping("/userIdInfoFind")
	public String userIdFindProcess(@RequestParam("user_name") String user_name, 
	                                @RequestParam("user_email") String user_email, 
	                                @RequestParam("user_jumin") String user_jumin) {
	    
	    UserDto user = userDao.userIdFind(user_name, user_email, user_jumin);
	    if (user != null) {
	        return user.getUser_id(); // 사용자 아이디를 직접 반환
	    } else {
	        return "not_found"; // 사용자를 찾지 못한 경우를 특별한 문자열로 표시
	    }
	}
	
		
	// 사용자 세션을 확인하고, 세션에 사용자 정보가 있는 경우 유저 마이페이지로 이동하거나 그렇지 않으면 메인 페이지로 이동하게 되는 기능을 가진 메서드
	@GetMapping("/usersessionkeep")
	public String userSessionKeep(HttpSession session) {
	    // 세션에서 사용자 정보를 가져옵니다.
		// httpSession 객체인 session에서 "userSession"이라는 이름으로 저장된 세션 속성을 가져온다.
		// userSession은 사용자의 정보를 세션에 담고 있다.
	    UserDto userSession = (UserDto) session.getAttribute("userSession");

	    // 세션에 사용자 정보가 있는 경우
	    if (userSession != null) {
	        // 로그인 상태를 유지하면서 유저 마이페이지로 이동
	        return "user/usermypage";
	    } else {
	        // 세션에 사용자 정보가 없는 경우에는 메인 페이지로 이동
	        return "../templates/index";
	    }
	}
}
