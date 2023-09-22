package pack.model.booking;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.model.booking.bookingDTO;

@Repository
public class BookingDao {
	@Autowired
	private BookingMapperInter bookingMapperInter;
	
	// 예약자료 읽기
	public ArrayList<bookingDTO> bookingAll(){
		ArrayList<bookingDTO> blist = (ArrayList<bookingDTO>)bookingMapperInter.bookingList();
		return blist;
	}
	

	public ArrayList<bookingDTO> bookingDo(bookingDTO bookingDto) {
		ArrayList<bookingDTO> olist = (ArrayList<bookingDTO>)bookingMapperInter.bookingDo();
		return olist;
	}
}
