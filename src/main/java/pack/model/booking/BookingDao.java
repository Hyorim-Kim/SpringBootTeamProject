
package pack.model.booking;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class BookingDao {
	@Autowired
	private BookingMapperInter bookingMapperInter;

	// 예약자료 읽기
	//public ArrayList<bookingDTO> bookingAll() {
		//ArrayList<bookingDTO> blist = (ArrayList<bookingDTO>) bookingMapperInter.bookingList("user_id");
		//return blist;
	//}
	
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
	public ArrayList<bookingDTO> bookingListAll(){
		ArrayList<bookingDTO> blist = (ArrayList<bookingDTO>)bookingMapperInter.bookingList();
		return blist;
	}
	/*
	public boolean bookingList(bookingDTO dto) {
		boolean b = false;
		int re = bookingMapperInter.bookingList(dto);  
		if(re >= 0) b = true;
		return b;
	}
*/
	
}
