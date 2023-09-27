package pack.controller.booking;



import jakarta.servlet.http.HttpSession;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import pack.model.booking.bookingDTO;
import pack.model.user.UserDto;
import pack.controller.FormBean;
import pack.model.booking.BookingDao;
import pack.model.booking.BookingMapperInter;


@Controller
@RequestMapping("booking")
public class bookingController {
	@Autowired
	private BookingDao dao;

	//예약페이지 연결/
	@GetMapping("booking")
	public String booking() {
		return "booking/booking";
	}
	//예약하기
	@PostMapping("bookingDo")
	public String bookingDo(bookingDTO bookingdto) {
		boolean b = dao.bookingInsert(bookingdto);
		if(b) {
			return "../templates/booking/bookingInfo";			
		} else {
			return "../templates/booking/booking";
		}	
	}
	
	 
	

//	@GetMapping("bookingList")
//	public String bookingCheck(HttpSession session, Model model){
	//	bookingDTO bookingList = (bookingDTO)session.getAttribute("booking");
		//model.addAttribute("booking", bookingList);
		
		//return "bookingInfo";
	//}

	  @GetMapping("/bookingList")
	    @ResponseBody
	    public ModelAndView bookingCheck(HttpSession session) {
	        ModelAndView modelAndView = new ModelAndView();

	        try {
	            // 세션에서 예약 정보를 가져오는 로직
	            bookingDTO bookingList = (bookingDTO) session.getAttribute("booking");

	            if (bookingList != null) {
	                modelAndView.addObject("booking", bookingList);
	            } else {
	                // 세션에 예약 정보가 없는 경우
	                modelAndView.addObject("booking", null);
	            }
	            modelAndView.setViewName("bookingInfo");
	        } catch (Exception e) {
	            // 예외 처리: 세션에서 예약 정보를 가져오는 동안 오류 발생
	            e.printStackTrace(); // 에러 로그 출력 (실제로는 로그 파일에 기록해야 함)
	            modelAndView.addObject("error", "예약 정보를 가져오는 동안 오류가 발생했습니다.");
	            modelAndView.setViewName("errorPage"); // 오류 페이지로 이동
	        }

	        return modelAndView;
	    }

//	//예약삭제
//	@GetMapping("bookDelete")
//	public String bookDelete(int bookingId){
//		dao.bookDelete(bookingId);
//		return "redirect:bookingInfo";
//	}

}