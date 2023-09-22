package pack.model.booking;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import pack.model.booking.bookingDTO;

@Mapper
public interface BookingMapperInter {
	@Select("select * from booking_board where user_id=#{user_id}")
	List<bookingDTO> bookingList();
	
	@Insert("insert into booking_board(\r\n"
			+ "		user_id,booking_date_start, booking_date_end,\r\n"
			+ "		cont_no,cont_size,booking_price)\r\n"
			+ "		values(#{user_id},#{booking_date_start}, #{booking_date_end},\r\n"
			+ "		#{cont_no},#{cont_size},#{booking_price})")
	List<bookingDTO> bookingDo();
	
	@Delete("delete from booking_board where booking_id=#{booking_id}")
	List<bookingDTO> bookDelete(int booking_id);
	
}
