package pack.model.container;

import lombok.Data;

@Data
public class ContainerDto {
	private String con_no,con_name,con_addr,we,kyung,con_area;
	
	// 민혁
	
	private int cont_no, cont_price;
	private String cont_addr, cont_size, cont_image, owner_name, cont_status, business_num;
}
