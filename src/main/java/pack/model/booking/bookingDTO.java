package pack.model.booking;

import java.sql.Date;

import lombok.Data;

@Data
public class bookingDTO {
	private int booking_id;
	private String user_id ;
	private Date booking_date_start;
	private Date booking_date_end;
	private String cont_no;
	private String cont_size;
	private String booking_price;
	

}
