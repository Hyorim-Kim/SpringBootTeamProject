
package pack.model.booking;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Select;


@Mapper
public interface BookingMapperInter {
	
	@Select("select * from booking_board where user_id=#{userId}")
	List<bookingDTO> bookingList();

	@Insert("INSERT INTO booking_board (user_id, booking_date_start, booking_date_end, cont_no, cont_size, booking_price) "
+ "VALUES (#{userId}, #{start-day}, #{end-day}, #{stringCity}, #{stringsize}, #{intmoney})")
	List<bookingDTO> bookingDo();

	@Delete("delete from booking_board where booking_id=#{bookingid}")
	List<bookingDTO> bookDelete(int booking_id);

}
