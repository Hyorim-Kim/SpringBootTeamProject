package pack.model.user;

import lombok.Data;

@Data
public class UserDto {
	private String user_id;
	private String user_pwd;
	private String user_repwd;
	private String user_name;
	private String user_tel;
	private String user_email;
	private String user_addr;
	private String user_jumin;
}
