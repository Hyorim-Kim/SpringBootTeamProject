package pack.model.owner;

import lombok.Data;

//*********** 서호, 광진 *************//
@Data
public class OwnerDto {
   private String business_num, owner_pwd, owner_repwd, owner_name, owner_tel, email, cont_num;
}