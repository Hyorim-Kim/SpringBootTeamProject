package pack.controller;



import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FormBean {
	private String user_id,user_pwd,user_name,user_tel,user_email,user_addr,user_jumin,
	business_num,owner_pwd,owner_name,owner_tel,email,cont_num,
	cont_no,cont_addr,cont_we,cont_kyung,cont_size,cont_name,owner_phone,cont_status,cont_image,owner_num;
	private String searchValue, selectSearch, svalue;
	// 지원 booking 
	private int booking_id;
	private Date booking_date_start;
	private Date booking_date_end;
	private String booking_price;
}