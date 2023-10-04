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
   
   @GetMapping("insertcontactuser")
   public String insertUser() {
      return "../templates/contact/insert";
   }
   
   @PostMapping("insertcontactuser")
   public String insertSubmitUser(ContactBean bean, Model model) {
      
      LocalDateTime currentDateTime = LocalDateTime.now();
      model.addAttribute("contact_date", currentDateTime);
      
      boolean b = contactDao.insertContact(bean);
         
      if(b) {
         return "../templates/user/usermypage"; // 추가 후 목록 보기
      } else {
         return "error";
      }
   }
   
   @GetMapping("insertcontactowner")
   public String insertOwner() {
      return "../templates/contact/insert";
   }
   
   @PostMapping("insertcontactowner")
   public String insertSubmitOwner(ContactBean bean, Model model) {
      
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