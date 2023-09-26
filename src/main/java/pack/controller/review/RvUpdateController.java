package pack.controller.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.model.DataDao;
import pack.model.ReviewDto;

@Controller
public class RvUpdateController {

	@Autowired
	private DataDao dataDao;
	
	@GetMapping("updatereview")
	public String updatereview(@RequestParam("cont_no") int cont_no, Model model) {
		
		ReviewDto reviewDto = new ReviewDto();
		
		
		
		model.addAttribute("cont_no", cont_no);
		System.out.println("cont_no컨트롤러 : " + cont_no);
		
		
		
		dataDao.getreview(reviewDto, cont_no);
		
		return "../templates/review/updatereview";
	}
}
