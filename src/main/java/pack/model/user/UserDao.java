package pack.model.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.servlet.http.HttpSession;
import pack.model.DataMapperInter;

@Repository 
public class UserDao { 
	
	@Autowired
	private DataMapperInter dataMapperInter;
	

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean joinUserData(UserDto userDto) {
        boolean b;
        if (isEmpty(userDto.getUser_id()) ||    
            isEmpty(userDto.getUser_pwd()) ||
            isEmpty(userDto.getUser_name()) ||
            isEmpty(userDto.getUser_tel()) ||
            isEmpty(userDto.getUser_email()) ||
            isEmpty(userDto.getUser_addr()) ||
            !userDto.getUser_id().matches("^[a-zA-Z\\d]{4,}$") ||
            !userDto.getUser_tel().matches("^[0-9-]+$") || 
            !userDto.getUser_jumin().matches("^\\d{6}-\\d{7}$") ||
            !userDto.getUser_pwd().equals(userDto.getUser_repwd()) ||
            !userDto.getUser_name().matches("^[가-힣]{2,}$") ||
            !userDto.getUser_pwd().matches("^.{4,}$") ||          
            !userDto.getUser_email().matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")) {
            b = false; 
        } else {
            b = true;
        }
        return b;
    }

    public boolean userInsertData(UserDto userDto) {
		boolean b = false;
		try {
			if (joinUserData(userDto)) {
				int re = dataMapperInter.userInsertData(userDto);
				if (re > 0) {
					b = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
    }

    public UserDto userLoginProcess(String user_id, String user_pwd) {
        return dataMapperInter.userLoginProcess(user_id, user_pwd);
    }

    public boolean userDataUpdate(UserDto userDto) {
    	boolean b = false;
		int re = dataMapperInter.userUpdate(userDto);
		if(re > 0) b = true;
		return b;  	
    }

    public boolean userDataDelete(UserDto userDto) {
        boolean b = false;
        int re = dataMapperInter.userDelete(userDto);
        if (re > 0) {
            b = true;
        }
        return b;
    }

    public int userIdCheck(String user_id) {
    	int result = dataMapperInter.userIdCheck(user_id);
		return result;
    }

    public UserDto userIdFind(String user_name, String user_email, String user_jumin) {
    	return dataMapperInter.userIdFind(user_name, user_email, user_jumin);
    }
}
