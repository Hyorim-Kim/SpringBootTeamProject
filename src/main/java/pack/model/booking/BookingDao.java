
package pack.model.booking;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pack.model.booking.bookingDTO;
import pack.model.user.UserDto;

@Repository
public class BookingDao {
	@Autowired
	private BookingMapperInter bookingMapperInter;

	// 예약자료 읽기
	public ArrayList<bookingDTO> bookingAll() {
		ArrayList<bookingDTO> blist = (ArrayList<bookingDTO>) bookingMapperInter.bookingList("user_id");
		return blist;
	}
	
	@Transactional
	public boolean bookingInsert(bookingDTO bookingdto) {
		boolean b = false;
		try {
			int re = bookingMapperInter.bookingInsert(bookingdto);
			if (re > 0) {
				b = true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return b;
	}

	
}
