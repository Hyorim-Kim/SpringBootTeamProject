package pack.controller.booking;

import org.springframework.ui.Model;

import pack.model.booking.bookingDTO;

public interface bookingService {
	public void bookingDo(bookingDTO bookingDto );

	//예약정보 지원 헤헤
	public bookingDTO bookingInfo(String userId);

	//삭제
	public int bookDelete(int bookingId);

	//관리자- 예약리스트
	public void memBookList(Model model,int num);
}
