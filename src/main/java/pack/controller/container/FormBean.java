package pack.controller.container;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Component
@Data
public class FormBean {
    private int cont_price;
    private String cont_addr, cont_size, cont_image, cont_status, owner_name, owner_num, business_num, cont_no;
    
    private double cont_we, cont_kyung; // 재민 추가

}
