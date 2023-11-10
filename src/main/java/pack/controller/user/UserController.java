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

@Controller
public class UserController {
	

	@Autowired
	private UserDao userDao;

	@GetMapping("/firstLogin")
	public String LoginGo(HttpSession session) {
	    if (session.getAttribute("userSession") != null) {    	
	        return "redirect:/usersessionkeep";	   
	    }
	    return "user/userlogin";
	}

	@GetMapping("/userJoinGo")
	public String userJoinGo(HttpSession session) {
	    if (session.getAttribute("userSession") != null) {    	
	        return "redirect:/usersessionkeep";	   
	    }
		return "user/userjoin";
	}

	@GetMapping("/ownerlogingo")
	public String ownerLoginGo(HttpSession session) {
	    if (session.getAttribute("ownerSession") != null) {    	
	        return "redirect:/ownersessionkeep";	   
	    }
		return "../templates/owner/ownerlogin";
	}

	@GetMapping("/userInfoFind")
	public String userInfoFinding() {
		return "useridfind";
	}

	@PostMapping("userJoinClick")
	public String userLoginOK(UserDto userDto) {
		boolean b = userDao.userInsertData(userDto);		
		if(b) {
			return "user/userlogin";
		} else {
			return "user/userjoin";
		}	
	}

    @PostMapping("/userLogSuccess")
    public String processLoginForm(@RequestParam("user_id") String user_id,
            					   @RequestParam("user_pwd") String user_pwd,
            					   HttpSession session) {

        UserDto user = userDao.userLoginProcess(user_id, user_pwd);
        
        if (user != null) {
        	session.setAttribute("userSession", user);
        	session.setAttribute("user_id", user.getUser_id()); 
            System.out.println("사용자 ID : " + user.getUser_id() + " " + "사용자 pwd : " + user.getUser_pwd());
        	return "redirect:/usersessionkeep";

        }
		else {
			return "user/userlogin";
		}
    }

	@GetMapping("/userupdate")
	public String userUpdatePage(Model model, HttpSession session) {
		UserDto user = (UserDto) session.getAttribute("userSession");
		model.addAttribute("userSession", user);
		return "user/userupdate";
	}

	@PostMapping("/userInfoUpdate")
	public String userInfoupdate(UserDto userDto, Model model) {
		boolean b = userDao.userDataUpdate(userDto);
		if(b) {
			return "user/userlogin";
		} else {
			return "user/usermypage";  
		}
	}

	@GetMapping("/userdelete")
	public String userDeletePage(Model model, HttpSession session) {
		UserDto user = (UserDto) session.getAttribute("userSession");
		model.addAttribute("userSession", user);
		return "user/userdelete";
	}

	@PostMapping("/userInfoDelete")
	public String userInfoDelete(UserDto userDto, Model model, HttpSession session) {
		boolean b = userDao.userDataDelete(userDto);
		if(b) {
			session.removeAttribute("userSession");
			return "user/userlogin";
		} else {
			return "user/userdelete";
		}
	}

	@GetMapping("/userlogoutgo")
	public String userLogoutProcess(HttpSession session) {

	    session.removeAttribute("userSession"); 
	    return "redirect:/";
	}

	@ResponseBody
	@PostMapping("/userIdCheck")
	public int IdCheck(@RequestParam("user_id") String user_id) {
		int result = userDao.userIdCheck(user_id);
		return result;
	}

	@ResponseBody
	@PostMapping("/userIdInfoFind")
	public String userIdFindProcess(@RequestParam("user_name") String user_name, 
	                                @RequestParam("user_email") String user_email, 
	                                @RequestParam("user_jumin") String user_jumin) {
	    UserDto user = userDao.userIdFind(user_name, user_email, user_jumin);

	    if (user != null) {
	        return user.getUser_id();
	    } else {
	        return "not_found";
	    }
	}

	@GetMapping("/usersessionkeep")
	public String userSessionKeep(HttpSession session) {
	    UserDto userSession = (UserDto) session.getAttribute("userSession");

	    if (userSession != null) {
	        return "user/usermypage";
	    } else {
	        return "../templates/index";
	    }
	}
}
