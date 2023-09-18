package pack.model.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.model.DataMapperInter;

// ************ 광진 (코드 건들면 물어요) **************// 
@Repository
public class UserDao {
	
	@Autowired
	private DataMapperInter dataMapperInter;
	
    // 사용자 회원가입시 필드란에 데이터값이 없어도 DB에 들어가는걸 방지 (광진)
    private boolean isValidUserData(UserDto userDto) {
        boolean b = false; 
        // 각 필드의 유효성 검사를 수행
        if (isEmpty(userDto.getUser_id()) ||
            isEmpty(userDto.getUser_pwd()) ||
            isEmpty(userDto.getUser_name()) ||
            isEmpty(userDto.getUser_tel()) ||
            isEmpty(userDto.getUser_email())) {
        	// 어떤 필드라도 비어 있다면 유효하지 않음.
            b = false; 
        } else {
            // 비밀번호와 비밀번호 확인 필드 비교
            if (!userDto.getUser_pwd().equals(userDto.getUser_repwd())) {
                b = false; // 비밀번호가 다르면 유효하지 않음.
            } else {
                b = true; // 모든 필드가 유효하고 비밀번호가 일치하면 true로 설정.
            }
        }
        return b;
    }
    
    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
	
	// 사용자 회원가입에 사용되는 메서드 (광진)
	public boolean userInsertData(UserDto userDto) {
		boolean b = false;

		if (isValidUserData(userDto)) {
			int re = dataMapperInter.userinsertData(userDto);
			if (re > 0) {
				b = true;
			}
		}
		return b;
	}
	    
	
	// 사용자 입장 로그인 가능 여부 판단하는 메서드 (광진) 9/15일 추가 작업
    public UserDto userloginProcess(String user_id, String user_pwd) {
        return dataMapperInter.userloginProcess(user_id, user_pwd);
    }
    
    // 사용자 회원수정에 필요한 메서드(광진)
    public boolean userupdate(UserDto userDto) {
    	boolean b = false;
		int re = dataMapperInter.userupdate(userDto); 
		if(re > 0) b = true;
		return b;  	
    }
    
    /*** 9/15일 추가 작업 (회원삭제) 광진 ***/   
    public boolean userdelete(UserDto userDto) {
    	boolean b = false;
    	int re = dataMapperInter.userdelete(userDto);
		if(re >= 0) b = true;
		return b; 
    }
	

}
