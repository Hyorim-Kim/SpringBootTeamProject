package pack.controller.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pack.model.DataDao;
import pack.model.ReviewDto;

@Controller
public class RvInsertController {
    @Autowired
    private DataDao dataDao;
    
    @ResponseBody
    @PostMapping("/rvinsert")
    public String insertReview(@RequestParam("rating") int rating,
                               @RequestParam("content") String content,
                               Model model) {
        // reviewDto 객체 생성 후 별점(rating), 내용(content)를 받아옴
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setRating(rating);
        reviewDto.setContent(content);

        // 데이터베이스에 후기 저장
        dataDao.saveReview(reviewDto);

        // 리뷰 저장 후 리뷰 페이지로 리다이렉트
        return "redirect:/review";
    }
    
    // 후기 목록을 가져오는 메서드
    @GetMapping("/rvinsertAjax")
    @ResponseBody
    public String insertReviewAjax(@RequestParam("rating") int rating,
                                   @RequestParam("content") String content) {
        // reviewDto 객체 생성 후 별점(rating)과 내용(content) 설정
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setRating(rating);
        reviewDto.setContent(content);

        // 후기 저장
        dataDao.saveReviewAjax(reviewDto);

        // 클라이언트에 성공 메시지 전송
        return "후기가 저장되었습니다.";
    }
    
    
}