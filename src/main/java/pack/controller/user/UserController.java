package pack.controller.user;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pack.model.user.UserDao;
import pack.model.user.UserDto;

// **** 광진 ****// 
@Controller
public class UserController {
	
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
		return "../templates/user/useridfind";
	}
	
	
	// 사용자 회원가입에서 가입 버튼을 클릭하고 성공 했을 때 (성공)
	@PostMapping("userJoinClick")
	public String userLoginOK(UserDto userDto) {
		boolean b = userDao.userInsertData(userDto);
		
		if(b) {
			return "../templates/user/userlogin";  
		} else {
			return "../templates/user/userjoin";  
		}	
	}
		
	
    // 사용자 로그인 페이지에서 요청 처리 (성공)
    @PostMapping("/userLogSuccess")
    public String processLoginForm(@RequestParam("user_id") String user_id,
            					   @RequestParam("user_pwd") String user_pwd,
            					   Model model, HttpSession session) {
        // 사용자가 입력한 아이디(user_id)와 비밀번호(user_pwd)를 사용하여 데이터베이스에서 해당 사용자를 찾아오는 코딩
    	// userDao 객체를 사용하여 데이터베이스와 연결하고, 데이터베이스에서 찾은 사용자 정보를 UserDto 객체에 저장
        UserDto user = userDao.userLoginProcess(user_id, user_pwd);
        
        if (user != null) { // 사용자 정보가 있는 경우 로그인 성공
            // 세션 최대 유지 시간을 30분(1800초)으로 설정
            session.setMaxInactiveInterval(1800);
            // 로그인 성공과 동시에 세션에 사용자 정보 설정
            // 여기서 가독성을 위해 세션 키의 이름은 userSession으로 변경 하였고 값 이름은 그대로 user로 유지.
            // user는 앞서 사용자가 입력한 아이디와 비밀번호를 담고 있음 그거를 세션에 담는거라 생각하시면 되용
        	session.setAttribute("userSession", user); 
        	System.out.println("사용자 ID : " + user.getUser_id() + " " + "사용자 pwd : " + user.getUser_pwd());
        	return "redirect:/usersessionkeep"; // 로그인 성공 시 usermypage.html로 이동.
        }
		else { // 사용자 정보가 DB에 없는 경우 즉, 아이디와 비밀번호가 없는 경우
			System.out.println("제대로 입력해라");
			return "user/userlogin"; // 로그인 실패 시 userlogin.html로 이동.
		}
    }
           
    /*** 9/15일 추가 작업 (회원수정) 광진 ***/
    
    // 사용자 마이페이지에서 회원수정을 클릭했을 때 (성공)
	@GetMapping("/userupdate")
	public String userUpdatePage(Model model, HttpSession session) {
		// 반환되는 값은 Object 타입이니 "userSession"이라는 이름으로 저장된 객체를 UserDto 타입으로 형변환하여 사용자 정보를 가져온다.
		// 세션에서 가져온 사용자 정보를 UserDto 객체로 다시 사용할 수 있다.
		UserDto user = (UserDto) session.getAttribute("userSession");
		// 세션에서 회원 정보를 가져와서 모델에 추가
		model.addAttribute("userSession", user);
		System.out.println("사용자 ID : " + user.getUser_id() + " " + "사용자 pwd : " + user.getUser_pwd());

		return "user/userupdate"; // 회원 수정 페이지로 이동
	}
	
	// 회원수정 페이지에서 회원수정을 클릭했을 때 (광진)
	@PostMapping("/userInfoUpdate")
	public String userInfoupdate(UserDto userDto, Model model, HttpSession session) {
		boolean b = userDao.userDataUpdate(userDto);
		if(b) {
			UserDto user = (UserDto) session.getAttribute("userSession"); 
			model.addAttribute("userSession", user); // 세션에 담겨져 있는 정보를 출력하기 위해 사용
			return "user/userlogin";  
		} else {
			return "user/usermypage";  
		}
	}
	
	 /*** 9/18일 추가 작업 (회원탈퇴) 광진 ***/
	
	
    // 사용자 마이페이지에서 회원삭제을 클릭했을 때 (성공)
	@GetMapping("/userdelete")
	public String userDeletePage(Model model, HttpSession session) {
		// 세션에서 회원 정보를 가져와서 모델에 추가
		UserDto user = (UserDto) session.getAttribute("userSession");
		model.addAttribute("userSession", user);
		return "user/userdelete"; // 회원 수정 페이지로 이동
	}
	
	// 회원삭제 페이지에서 버튼을 클릭할 때 수행 (광진)
	@PostMapping("/userInfoDelete")
	public String userInfoDelete(UserDto userDto, Model model, HttpSession session) {
		boolean b = userDao.userDataDelete(userDto);
		if(b) {
			// 탈퇴니까 세션 유지 코드가 있을 필요가 없어서 코드 삭제
			return "user/userlogin";  
		} else {
			return "user/userdelete";  
		}
	}
	
	/*** 9/19일 추가 작업 사용자 마이페이지에서 로그아웃 하기 (광진) ***/
	@GetMapping("/userlogoutgo")
	public String userLogoutProcess(HttpSession session) {
		// 세션 유지 종료
	    session.removeAttribute("userSession"); 
	    //session.invalidate(); 이거 쓰면 큰일난다 
	    return "redirect:/"; // 로그아웃 클릭시 메인 홈페이지로 이동 
	}
	
	// 사용자 회원가입시 아이디 중복체크 (광진)
	@ResponseBody
	@PostMapping("/userIdCheck")
	public int IdCheck(@RequestParam("user_id") String user_id) {
		int result = userDao.userIdCheck(user_id);
		return result;
		
	}
	
	// 사용자 아이디 찾기 (광진)
	@ResponseBody
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
	
		
	// 세션 유지 (광진)
	@GetMapping("/usersessionkeep")
	public String userSessionKeep(HttpSession session) {
	    // 세션에서 사용자 정보를 가져온다.
	    UserDto userSession = (UserDto) session.getAttribute("userSession"); // 여기서 "userSession"은 user값을 담고 있다.
	    
	    // 만약 세션에 사용자 정보가 있는 경우
	    if (userSession != null) {
	        // 찍어보자
	        System.out.println("사용자 ID : " + userSession.getUser_id() + " " + "사용자 pwd : " + userSession.getUser_pwd());
	        // 사용자 정보를 유지하면 로그인페이지에서 home 버튼을 눌러도 로그인 세션이 있는채로 마이페이지로 이동.
	        return "user/usermypage";
	    } 	    	            	    
	    else {
	        // 세션에 사용자 정보가 없는 경우에는 index로 이동.
	        return "../templates/index";
	    }
	}
}
