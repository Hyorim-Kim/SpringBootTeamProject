package pack.controller.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.model.contact.ContactDao;
import pack.model.contact.ContactDto;

@Controller
public class ContactDetailController {

	@Autowired
	private ContactDao contactDao;
	
	@GetMapping("contactdetail")
	public String detail(@RequestParam("contact_no")String contact_no, Model model) {
		ContactDto detail = contactDao.detailContact(contact_no);
		model.addAttribute("detail", detail);
		return "../templates/contact/contactdetail";
	}
}
