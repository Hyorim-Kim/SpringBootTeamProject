package pack.model.user;

import lombok.Data;

//************ 서호, 광진 **************// 
@Data
public class UserDto {
	private String user_id, user_pwd, user_name, user_tel, user_email, user_addr, user_jumin, user_repwd;
}
