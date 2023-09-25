
package pack.model.booking;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pack.controller.FormBean;
import pack.model.booking.bookingDTO;

@Repository
public class BookingDao {
	@Autowired
	private BookingMapperInter bookingMapperInter;
	
	// 예약자료 읽기
	public ArrayList<bookingDTO> bookingAll(){
		ArrayList<bookingDTO> blist = (ArrayList<bookingDTO>)bookingMapperInter.bookingList("user_id");
		return blist;
	}
	
	@Transactional
	public boolean bookingDo(FormBean bean) {
		boolean b = false;
		String re = bookingMapperInter.bookingDo(bean);
		
		return b;
	}
}

