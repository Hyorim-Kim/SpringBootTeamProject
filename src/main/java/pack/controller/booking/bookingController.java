package pack.controller.booking;



import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
			return "booking/bookingInfo";			
		} else {
			return "/booking/booking";
		}	
	}
	


	@GetMapping("/bookingList")
	public String bookingProcess(HttpSession session, Model model) {
		ArrayList<bookingDTO> bookingdto = dao.bookingListAll();
		session.setAttribute("bookList", bookingdto);
		model.addAttribute("bList", bookingdto);

		return "bookingInfo";
	}

	//예약삭제
	@GetMapping("bookDelete")
	public String bookDelete(bookingDTO bookingDto, Model model, HttpSession session){
		boolean b = dao.bookingDelete(bookingDto);
		if(b) {
			return "booking/booking";
		}else
		return "redirect:bookingInfo";
	}

}