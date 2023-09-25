package pack.controller.booking;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.model.booking.bookingDTO;
import pack.controller.user.userLoginSession;
import pack.model.booking.BookingDao;
import pack.model.booking.BookingMapperInter;


@Controller
@RequestMapping("booking")
public class bookingController implements userLoginSession{
	@Autowired
	private BookingDao dao;

	//예약페이지 연결
	@GetMapping("booking")
	public String booking() {
		return "booking/booking";
	}
	//예약하기
	@PostMapping("bookingDo")
	public String bookingDo(HttpSession session, bookingDTO bookingDto) {
		dao.bookingDo(bookingDto); 
		return "redirect:/bookingInfo";
	}

	

	@GetMapping("bookingList")
	public String bookingCheck(HttpServletRequest request, HttpServletResponse response ,Model model)throws Exception {
		ArrayList<bookingDTO> blist =(ArrayList<bookingDTO>)dao.bookingAll();
		model.addAttribute("list", blist);
		return "/bookingInfo";
	}


//	//예약삭제
//	@GetMapping("bookDelete")
//	public String bookDelete(int bookingId){
//		dao.bookDelete(bookingId);
//		return "redirect:bookingInfo";
//	}

}