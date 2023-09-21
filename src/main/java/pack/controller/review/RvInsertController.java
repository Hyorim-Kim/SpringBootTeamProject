package pack.controller.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pack.model.DataDao;
import pack.model.ReviewDto;

@Controller
public class RvInsertController {
	@Autowired
	private DataDao dataDao;

//	 별점 선택 및 코멘트 작성 후 작성완료 누르면,, 여기로!!

	@PostMapping("/rvinsert")
	public String insertReview(@RequestParam("rating") int rating,
	                           @RequestParam("content") String content,
	                           Model model) {// Model을 사용해서 데이터를 review.html로 전달
	    // reviewDto 객체 생성 후 별점(rating), 내용(content)를 받아옴
	    ReviewDto reviewDto = new ReviewDto();
	    reviewDto.setRating(rating);
	    reviewDto.setContent(content); // @RequestParam 하는이유 : reviewDto에 넣으려고
	    
	    // 위에서 받아온 것들을 rv에 저장
	    dataDao.saveReview(reviewDto); // ReviewDto를 사용하여 db에 저장
	    
	    return "redirect:/review";  // 저장 후 review로 돌아감
	}
}