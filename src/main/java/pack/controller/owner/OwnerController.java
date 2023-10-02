package pack.controller.owner;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pack.model.owner.OwnerDao;
import pack.model.owner.OwnerDto;



//**** 광진 ****//
@Controller
public class OwnerController {
	
	@Autowired
	private OwnerDao ownerDao;
	
	// 공급자 로그인 페이지에서 회원가입을 클릭했을 때 (성공)
	@GetMapping("ownerJoinGo")
	public String ownerChoice(HttpSession session) {
	    if (session.getAttribute("ownerSession") != null) {    	
	        return "redirect:/ownersessionkeep";	   
	    }
		return "owner/ownerjoin";
	}
	
	// 공급자 로그인 페이지에서 사용자 로그인을 클릭했을 때 (성공)
	@GetMapping("userlogingo")
	public String userLoginGo(HttpSession session) {
	    if (session.getAttribute("userSession") != null) {    	
	        return "redirect:/usersessionkeep";	   
	    }
		return "user/userlogin";
	}
	
	
	// 공급자 회원가입에서 가입 버튼을 클릭하고 성공 했을 때 (성공)
	@PostMapping("ownerJoinClick")
	public String ownerloginOK(OwnerDto ownerDto) {
		boolean b = ownerDao.ownerinsertData(ownerDto);
		if(b) {
			return "../templates/owner/ownerlogin";  
		} else {
			return "../templates/owner/ownerjoin";  
		}
		
	}
	
	// 공급자 로그인 페이지에서 요청 처리 (성공)
    @PostMapping("ownerLogSuccess")
    public String processLoginForm(@RequestParam("business_num") String business_num,
            					   @RequestParam("owner_pwd") String owner_pwd,
                                   Model model, HttpSession session){
        // 공급자 로그인 처리
    	OwnerDto owner = ownerDao.ownerloginProcess(business_num, owner_pwd);

        if (owner != null) {
            // 세션 최대 유지 시간을 30분(1800초)으로 설정
            session.setMaxInactiveInterval(1800);
            // 로그인 성공과 동시에 세션에 사용자 정보 설정
            // 여기서도 userController와 같이 가독성을 위해 공급자 세션 키의 이름은 ownerSession으로 변경 하였고 값 이름은 그래도 owner로 유지.
            // owner는 앞서 공급자가 입력한 사업자번호와 비밀번호를 담고 있음 그거를 세션에 담는거라 생각하시면 됩니다.
        	session.setAttribute("ownerSession", owner);
        	session.setAttribute("business_num", owner.getBusiness_num());
        	session.setAttribute("owner_name", owner.getOwner_name());
            return "owner/ownermain"; // 로그인 성공 시 ownermain.html로 이동
        } else {
            // 로그인 실패
            return "owner/ownerlogin"; 
        }
    }
    
    // 공급자 마이페이지 에서 회원수정 클릭할 때 (성공)
    @GetMapping("/ownerupdate")
    public String ownerUpdatePage (Model model, HttpSession session) {
    	OwnerDto owner = (OwnerDto) session.getAttribute("ownerSession");
    	model.addAttribute("ownerSession", owner);
    	
    	return "owner/ownerupdate";
    }
    
    // 공급자 회원수정 페이지에서 회원수정 클릭할 때 (성공)
    @PostMapping("ownerInfoUpdate")
    public String ownerInfoupdate(OwnerDto ownerDto, Model model, HttpSession session) {
    	boolean b = ownerDao.ownerupdate(ownerDto);
		if(b) {
			OwnerDto owner = (OwnerDto) session.getAttribute("ownerSession");
			model.addAttribute("ownerSession", owner);
			return "owner/ownerlogin";  
		} else {
			return "owner/ownerupdate";  
		}
    }
    
    /*** 9/18일 추가 작업 (회원탈퇴) 광진 ***/
    
    // 공급자 마이페이지 에서 회원탈퇴 클릭할 때
    @GetMapping("/ownerdelete")
    public String ownerDeletePage(Model model, HttpSession session) {
		// 세션에서 회원 정보를 가져와서 모델에 추가
		OwnerDto owner = (OwnerDto) session.getAttribute("ownerSession");
		model.addAttribute("ownerSession", owner);
		return "owner/ownerdelete"; // 회원 수정 페이지로 이동  
    }
    
    // 공급자 회원탈퇴 페이지 에서 회원탈퇴 버튼을 클릭할 때
    @PostMapping("ownerInfoDelete")
    public String ownerInfoDelete(OwnerDto ownerDto, Model model, HttpSession session) {
    	boolean b = ownerDao.ownerdelete(ownerDto);
		if(b) {
			// 탈퇴니까 세션 유지 코드가 있을 필요가 없어서 코드 삭제
			return "owner/ownerlogin";  
		} else {
			return "owner/ownerdelete";  
		}
    }
    
	/*** 9/19일 추가 작업 공급자 마이페이지에서 로그아웃 하기 (광진) ***/
	@GetMapping("/ownerlogoutgo")
	public String ownerLogoutProcess(HttpSession session) {
	    session.removeAttribute("ownerSession"); // 세션 유지 종료
	    return "redirect:/"; // 로그아웃 클릭시 메인 홈페이지로 이동 
	}
	
	// 세션값 유지하게 하기
	@GetMapping("/ownersessionkeep")
	public String ownerSessionKeep(HttpSession session) {
	    OwnerDto ownerSession = (OwnerDto) session.getAttribute("ownerSession");
	    if (ownerSession != null) {
	        // 세션에 ownerSession값이 존재할 경우 ownermain.html 페이지로 이동
	        return "owner/ownermain"; 
	    } else {
	    	// 세션값이 없을 경우
	    	return "../templates/index";
	    }
	}      
}
