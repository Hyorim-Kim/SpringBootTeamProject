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
		// cont_no와 rating 값을 사용하여 getreview 메서드 호출
		ReviewDto reviewDto = dataDao.getreview(cont_no);

		// reviewDto를 모델에 추가
		model.addAttribute("review", reviewDto);
		//System.out.println("cont_no-upcot : " + cont_no);
		return "../templates/review/updatereview";
	}
}
