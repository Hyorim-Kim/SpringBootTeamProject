package pack.controller.container;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Component
@Data
public class FormBean {
    private int cont_no, cont_price;
    private String cont_addr, cont_size, cont_image, cont_status, owner_name, owner_num, business_num;
    private MultipartFile file;
    
 // cont_image 필드에 파일 경로를 저장하기 위한 setter 추가
    public void setCont_image(String cont_image) {
        this.cont_image = cont_image;
    }
}
