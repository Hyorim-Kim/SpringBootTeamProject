package pack.controller.contact;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;


import pack.model.contact.ContactDao;
import pack.model.contact.ContactDto;

@Controller
public class ContactController {

	@Autowired
	private ContactDao contactDao;
	
	@RequestMapping("contactadmin")
	public String adminList(Model model) {
		ArrayList<ContactDto> list = (ArrayList<ContactDto>)contactDao.listContact();
		model.addAttribute("list", list);
		return "../templates/contact/contactadmin";
	}
	
	@RequestMapping("contactmain")
	public String mainList(Model model) {
		ArrayList<ContactDto> list = (ArrayList<ContactDto>)contactDao.listContact();
		model.addAttribute("list", list);
		return "../templates/contact/contactmain";
	}

}
