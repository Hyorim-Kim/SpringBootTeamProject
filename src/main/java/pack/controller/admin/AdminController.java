package pack.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pack.model.admin.AdminDao;
import pack.model.admin.AdminDto;
import pack.model.owner.OwnerDto;

@Controller
public class AdminController {
	// 광진
	@Autowired
	private AdminDao adminDao;
	
	// 메인 페이지에서 노란점 클릭했을 때 (성공)
	@GetMapping("adminLoginGo") 
	public String adminlogingo() {
		return "../templates/admin/adminlogin";
	}
	
	
	// 로그인 요청 처리 (성공)
    @PostMapping("adminloginok")
    public String processLoginForm(@RequestParam("admin_id") String admin_id,
            					   @RequestParam("admin_pwd") String admin_pwd,
                                   Model model, HttpSession session){
        // 사용자 로그인 처리
    	AdminDto admin = adminDao.adminloginProcess(admin_id, admin_pwd);

        if (admin != null) {
        	session.setAttribute("adminSession", admin); // 세션에 사용자 정보 저장
        	System.out.println("관리자 Id : " + admin.getAdmin_id() + " " + "관리자 비번 : " + admin.getAdmin_pwd());
        	return "admin/adminloginok";  // 로그인 성공 시 adminloginok.html로 이동
            
        } else {
            // 로그인 실패
            return "admin/adminlogin"; 
        }
    }
    
	/*** 9/19일 추가 작업 사용자 마이페이지에서 로그아웃 하기 (광진) ***/
	@GetMapping("/adminlogout")
	public String userLogoutProcess(HttpSession session) {
	    session.removeAttribute("adminSession"); // 세션 유지 종료
	    return "redirect:/"; // 로그아웃 클릭시 메인 홈페이지로 이동 
	}
	

	// 세션값 유지하게 하기
	@GetMapping("/adminsessionkeep")
	public String adminSessionKeep(HttpSession session) {
		AdminDto adminSession = (AdminDto) session.getAttribute("adminSession");
	    if (adminSession != null) {
	        // 세션에 ownerSession값이 존재할 경우 ownermain.html 페이지로 이동
	    	System.out.println("관리자 Id : " + adminSession.getAdmin_id() + " " + "관리자 비번 : " + adminSession.getAdmin_pwd());
	        // 이동 경로를 상대 경로로 지정
	        return "admin/adminloginok"; 
	    } else {
	    	// 세션값이 없을 경우
	    	return "../templates/index";
	    }
	}
	

	
}
