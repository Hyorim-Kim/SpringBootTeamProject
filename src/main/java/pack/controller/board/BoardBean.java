package pack.controller.board;

import java.time.LocalDate;
import lombok.Data;

@Data
public class BoardBean {
	private int num, readcnt, gnum, onum, nested;
	private String admin_id, admin_pwd, mail, title, cont, bdate;
	private String searchName, searchValue;
	
	public void setBdate() {
		LocalDate localDate = LocalDate.now();
		int year = localDate.getYear();
		int month = localDate.getMonthValue();
		int day = localDate.getDayOfMonth();
		this.bdate = year + "-" + month + "-" + day;	
	}
}
