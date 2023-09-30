package pack.controller.board;

import java.time.LocalDate;
import lombok.Data;

@Data
public class BoardBean {
	private int num, readcnt, gnum, onum, nested;
<<<<<<< HEAD
	private String admin_id, title, cont, bdate;
=======
	private String admin_id, admin_pwd, mail, title, cont, bdate;
>>>>>>> branch 'master' of https://github.com/Hyorim-Kim/Team.git
	private String searchName, searchValue;
	
	public void setBdate() {
		LocalDate localDate = LocalDate.now();
		int year = localDate.getYear();
		int month = localDate.getMonthValue();
		int day = localDate.getDayOfMonth();
		this.bdate = year + "-" + month + "-" + day;	
	}
}
