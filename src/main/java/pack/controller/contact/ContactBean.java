package pack.controller.contact;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ContactBean {
private String contact_no, contact_name, contact_email, contact_title, contact_message, contact_date, contact_status;
    
    public void setContact_date() {
		LocalDate localDate = LocalDate.now();
		int year = localDate.getYear();
		int month = localDate.getMonthValue();
		int day = localDate.getDayOfMonth();
		this.contact_date = year + "-" + month + "-" + day;	
	}
}
