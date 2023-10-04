package pack.controller.booking;



import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import pack.model.booking.bookingDTO;
import pack.model.user.UserDto;
import pack.controller.FormBean;
import pack.model.admin.AdminBean;
import pack.model.booking.BookingDao;
import pack.model.booking.BookingMapperInter;


@Controller
@RequestMapping("booking")
public class bookingController {
	@Autowired
	private BookingDao dao;
	
	//예약 페이지 연결
	@GetMapping("booking")
	public String booking() {
		return "booking/booking";
	}
	


	
	// 10/3 민혁 예약에 따른 상태변경 메소드 추가 예정
	//예약하기
		@PostMapping("/bookingDo")
		public String bookingDo(bookingDTO bookingdto, AdminBean bean) {
			boolean b = dao.bookingInsert(bookingdto);
			boolean a = dao.contStatusUpdate(bean);
			if(b && a) {
				return "booking/bookingInfo";			
			} else {
				return "/booking/booking";
			}	
		}
	
	@GetMapping("/bookingInfo")
	public String bookingProcess(HttpSession session, Model model) {
		
		System.out.println("리스트 메소드 시작");
		
		String user_id = (String)session.getAttribute("user_id");
		
		System.out.println(user_id);
		
		ArrayList<bookingDTO> bookingdto = dao.bookingListAll(user_id);
		System.out.println(bookingdto);
		
		session.setAttribute("bookList", bookingdto);
		
		model.addAttribute("bList", bookingdto);
		return "booking/bookingInfo";
		
		
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