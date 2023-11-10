package pack.model.owner;

import lombok.Data;

@Data
public class OwnerDto {
   private String business_num;
   private String owner_pwd;
   private String owner_repwd;
   private String owner_name;
   private String owner_tel;
   private String email;
   private String cont_num;
}