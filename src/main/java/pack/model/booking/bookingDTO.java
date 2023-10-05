package pack.model.booking;


import lombok.Data;

@Data
public class bookingDTO {
	private int booking_id;

	private String user_id, user_name, user_tel, user_email;
	private String booking_date_start;
	private String booking_date_end;
	private String cont_no;
	private String cont_size;
	private String booking_price;
}
