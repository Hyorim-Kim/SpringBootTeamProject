package pack.controller.contact;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import pack.model.contact.ContactDao;

@Controller
public class ContactInsertController {

	@Autowired
	private ContactDao contactDao;
	
	@GetMapping("insertcontact")
	public String insert() {
		return "../templates/contact/insert";
	}
	
	@PostMapping("insertcontact")
	public String insertSubmit(ContactBean bean, Model model) {
		
		LocalDateTime currentDateTime = LocalDateTime.now();
		model.addAttribute("contact_date", currentDateTime);
		
		boolean b = contactDao.insertContact(bean);
			
		if(b) {
			return "../templates/owner/ownermain"; // 추가 후 목록 보기
		} else {
			return "error";
		}
	}
}
