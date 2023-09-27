package pack.controller.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import pack.model.contact.ContactDao;

@Controller
public class ContactUpdateController {

	@Autowired
	private ContactDao contactDao;
	
	@PostMapping("contactupdate")
	public String update(ContactBean bean) {
	boolean b = contactDao.updateContact(bean);
	if(b)
		return "redirect:/contactadmin?page=1"; // 수정 후 목록 보기
	else
		return "error";
}
}
