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



@Controller
public class OwnerController {
	
	@Autowired
	private OwnerDao ownerDao;

	@GetMapping("ownerJoinGo")
	public String ownerChoice(HttpSession session) {
	    if (session.getAttribute("ownerSession") != null) {    	
	        return "redirect:/ownersessionkeep";	   
	    }
		return "owner/ownerjoin";
	}

	@GetMapping("userlogingo")
	public String userLoginGo(HttpSession session) {
	    if (session.getAttribute("userSession") != null) {    	
	        return "redirect:/usersessionkeep";	   
	    }
		return "user-login";
	}

	@PostMapping("ownerJoinClick")
	public String ownerloginOK(OwnerDto ownerDto) {
		boolean b = ownerDao.ownerInsertData(ownerDto);
		if(b) {
			return "../templates/owner/ownerlogin";  
		} else {
			return "../templates/owner/ownerjoin";  
		}
		
	}

    @PostMapping("ownerLogSuccess")
    public String processLoginForm(@RequestParam("business_num") String business_num,
            					   @RequestParam("owner_pwd") String owner_pwd,
                                   Model model, HttpSession session){

    	OwnerDto owner = ownerDao.ownerLoginProcess(business_num, owner_pwd);

        if (owner != null) {
            session.setMaxInactiveInterval(1800);
        	session.setAttribute("ownerSession", owner);
        	session.setAttribute("business_num", owner.getBusiness_num());
        	session.setAttribute("owner_name", owner.getOwner_name());
            return "owner/ownermain";
        } else {
            // 로그인 실패
            return "owner/ownerlogin"; 
        }
    }

    @GetMapping("/ownerupdate")
    public String ownerUpdatePage (Model model, HttpSession session) {
    	OwnerDto owner = (OwnerDto) session.getAttribute("ownerSession");
    	model.addAttribute("ownerSession", owner);
    	
    	return "owner/ownerupdate";
    }

    @PostMapping("ownerInfoUpdate")
    public String ownerInfoupdate(OwnerDto ownerDto, Model model, HttpSession session) {
    	boolean b = ownerDao.ownerUpdate(ownerDto);
		if(b) {
			OwnerDto owner = (OwnerDto) session.getAttribute("ownerSession");
			model.addAttribute("ownerSession", owner);
			return "owner/ownerlogin";  
		} else {
			return "owner/ownerupdate";  
		}
    }

    @GetMapping("/ownerdelete")
    public String ownerDeletePage(Model model, HttpSession session) {
		OwnerDto owner = (OwnerDto) session.getAttribute("ownerSession");
		model.addAttribute("ownerSession", owner);
		return "owner/ownerdelete";
    }

    @PostMapping("/ownerInfoDelete")
    public String ownerInfoDelete(OwnerDto ownerDto, Model model) {
        try {
            boolean b = ownerDao.ownerDelete(ownerDto);
            if (b) {
                return "owner/ownerlogin";
            } else {
                return "/";
            }
        } catch (Exception e) {
            return "owner/ownererror";
        }
    }


	@GetMapping("/ownerlogoutgo")
	public String ownerLogoutProcess(HttpSession session) {
	    session.removeAttribute("ownerSession");
	    return "redirect:/";
	}

	@GetMapping("/ownersessionkeep")
	public String ownerSessionKeep(HttpSession session) {
	    OwnerDto ownerSession = (OwnerDto) session.getAttribute("ownerSession");
	    if (ownerSession != null) {
	        return "owner/ownermain"; 
	    } else {
	    	return "../templates/index";
	    }
	}      
}
