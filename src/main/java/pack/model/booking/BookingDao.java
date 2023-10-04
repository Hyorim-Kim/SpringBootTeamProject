
package pack.model.booking;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pack.model.admin.AdminBean;


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
	// 10/3 민혁 창고예약에 따른 상태변경 
	
	@Transactional // 성공하면 커밋 실패하면 롤백
	public boolean contStatusUpdate(AdminBean adminbean) {
		boolean a = false;
		System.out.println("상태 업데이트 메서드 시작");
		try {
			int re = bookingMapperInter.contStatusUpdate(adminbean);
			System.out.println("SQL 실행 결과: " + re);
			if (re > 0)
				a = true;
		} catch (Exception e) {
			// 예외 발생 시 처리
			System.out.println("예외 발생: " + e.getMessage());
			a = false;
		}
		return a;
	}
	
	
	
	public ArrayList<bookingDTO> bookingListAll(String user_id){
		ArrayList<bookingDTO> blist = (ArrayList<bookingDTO>)bookingMapperInter.bookingList(user_id);
		System.out.println("user_id : " + user_id);
		return blist;
	}
	
    // 예약 삭제 bookingInfo 취소하기
	public boolean bookingDelete(bookingDTO bookingdto) {
		boolean b = false;
		int re = bookingMapperInter.bookingDelete(bookingdto);
		if(re >= 0 ) b = true;
		return b;
	}
	
	
	
}