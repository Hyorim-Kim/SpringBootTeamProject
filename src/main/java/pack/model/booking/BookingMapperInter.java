package pack.model.booking;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import pack.model.booking.bookingDTO;

@Mapper
public interface BookingMapperInter {
	@Select("select * from booking_board where user_id=#{userId}")
	List<bookingDTO> bookingList();
	
	@Insert("INSERT INTO booking_board (booking_id, user_id, booking_date_start, booking_date_end, cont_no, cont_size, booking_price) " +
            "VALUES (#{booking_id}, #{userId}, #{booking_date_start}, #{booking_date_end}, #{cont_no}, #{cont_size}, #{booking_price})")
	List<bookingDTO> bookingDo();
	
	@Delete("delete from booking_board where booking_id=#{bookingId}")
	List<bookingDTO> bookDelete(int booking_id);
	
}
