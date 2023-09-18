package pack.controller.owner;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.model.owner.OwnerDao;
import pack.model.owner.OwnerDto;


//**** 광진 ****//
@Controller
public class OwnerController {
	
	@Autowired
	private OwnerDao ownerDao;
	
	// 회원가입 선택란에서 공급자를 선택할 때
	@GetMapping("ownerjoin")
	public String ownerchoice() {
		return "ownerjoin";
	}
	
	// 공급자 회원가입에서 가입 버튼을 클릭하고 성공 했을 때
	@PostMapping("ownerjoin")
	public String ownerloginOK(OwnerDto ownerDto) {
		boolean b = ownerDao.ownerinsertData(ownerDto);
		if(b) {
			return "testok";  
		} else {
			return "testfail";  
		}
		
	}
	
	// 로그인 요청 처리
    @PostMapping("/owner/login")
    public String processLoginForm(@RequestParam("business_num") String business_num,
            					   @RequestParam("owner_pwd") String owner_pwd,
                                   Model model){
        // 사용자 로그인 처리
    	OwnerDto owner = ownerDao.ownerloginProcess(business_num, owner_pwd);

        if (owner != null) {
            return "testok"; // 로그인 성공 시 testlogin.html로 이동
            
        } else {
            // 로그인 실패
            return "testfail"; 
        }
    }
    
    
}
