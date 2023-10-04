package pack.model.booking;

import java.sql.Date;

import lombok.Data;

@Data
public class bookingDTO {
	private int booking_id;
	private String user_id;
	private String booking_date_start;
	private String booking_date_end;
	private String user_name;
	private String cont_no;
	private String cont_size;
	private String booking_price;

}
