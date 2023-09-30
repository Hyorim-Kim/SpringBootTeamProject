package pack.model.container;

import lombok.Data;

@Data
public class ContainerDto {
	// 민혁
	private int cont_no, cont_price;
	private String cont_addr, cont_size, cont_image, owner_name, cont_status, business_num;
	private String  cont_name,  owner_phone, owner_num, cont_area;
	private double cont_we, cont_kyung; //재민 추가

}
