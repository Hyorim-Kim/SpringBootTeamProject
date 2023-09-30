package pack.controller.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.model.contact.ContactDao;

@Controller
public class ContactDeleteController {  // contact에서 질문 삭제
	
	@Autowired
	ContactDao contactDao;
	
	@GetMapping("contactdelete")
	public String delete(@RequestParam("contact_no")String contact_no) {
		boolean b = contactDao.deleteContact(contact_no);
	      if(b)
	    	 return "redirect:/contactadmin?page=1";
	      else
	         return "error";
	   }
}
