package pack.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.model.DataDao;


@Controller
public class DeleteController {
   @Autowired
   private DataDao dataDao;
   
   @RequestMapping("delete")
   public String delete(@RequestParam("cont_no")String cont_no) {
      boolean b = dataDao.condelete(cont_no);

      if(b)
    	 return "redirect:/registered"; // 삭제 후 목록보기 (이떄 forward 방식으로 하면 DB상으론 삭제가 되지만 삭제 후 목록으로 돌아가지 않고 에러발생)
      else
         return "error";
   }
}