
package pack.model.booking;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;



@Mapper
public interface BookingMapperInter {
	
	@Insert("INSERT INTO booking_board (user_id, user_name, booking_date_start, booking_date_end, cont_no, cont_size, booking_price) VALUES (#{userId},#{userName}, #{start-day}, #{end-day}, #{stringCity}, #{stringsize}, #{intmoney})")
	int bookingInsert(bookingDTO bookingdto);
	
	
	@Select("select * from booking_board INNER JOIN user ON booking_board.user_id = user.user_id where user_name =#{user_name}")
	List<bookingDTO> bookingList(@Param("user_id") String userId);
	
	
	@Delete("delete from booking_board where booking_id=#{bookingid}")
	List<bookingDTO> bookDelete(int booking_id);




}
