package pack.model.board;

import lombok.Data;

@Data
public class BoardDto {
	private int num, readcnt, gnum, onum, nested;
	private String admin_id, mail, title, cont, bdate;
}
