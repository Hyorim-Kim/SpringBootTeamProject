package pack.model.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.model.DataMapperInter;

// ************ 광진 **************// 
@Repository
public class UserDao {
	
	@Autowired
	private DataMapperInter dataMapperInter;
	
	//  isEmpty()은 문자열의 길이가 0인 경우에, true를 리턴	
    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
	
    // 사용자 회원가입시 필드란에 데이터값이 없어도 DB에 들어가는걸 방지 (광진)
    private boolean joinUserData(UserDto userDto) {
        boolean b = false; 
        // 각 필드의 유효성 검사를 수행
        if (isEmpty(userDto.getUser_id()) ||    //  isEmpty()은 문자열의 길이가 0인 경우에, true를 리턴
            isEmpty(userDto.getUser_pwd()) ||
            isEmpty(userDto.getUser_name()) ||
            isEmpty(userDto.getUser_tel()) ||
            isEmpty(userDto.getUser_email()) ||
            // 숫자와 하이픈만 허용하는 정규식
            !userDto.getUser_tel().matches("^[0-9-]+$") || 
            // 주민번호 패턴을 검사하는 정규식
            !userDto.getUser_jumin().matches("^\\d{6}-\\d{7}$") ||
            // 비밀번호와 비밀번호 확인 데이터값이 같은지 확인 
            !userDto.getUser_pwd().equals(userDto.getUser_repwd()) ||
            // 한글 2글자 이상인지 검사
            !userDto.getUser_name().matches("^[가-힣]{2,}$") ||
            // 비밀번호가 4글자 이상인지 검사
            !userDto.getUser_pwd().matches("^.{4,}$") ||
            // 이메일 주소 유효성 검사
            !userDto.getUser_email().matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")) { 
            // 어떤 필드라도 비어 있거나 정규식에 맞지 않거나 비밀번호가 일치하지 않거나 이름, 비밀번호, 이메일이 조건을 만족하지 않으면 유효하지 않음.
            b = false; 
        } else {
            b = true; // 모든 필드와 정규식에 유효하고 비밀번호가 일치하면 true로 설정.
        }
        return b;
    }

	// 사용자 회원가입에 사용되는 메서드 (광진)
	public boolean userInsertData(UserDto userDto) {
		boolean b = false;

		if (joinUserData(userDto)) {
			int re = dataMapperInter.userinsertData(userDto);
			if (re > 0) {
				b = true;
			}
		}
		return b;
	}
	    
	
	// 사용자 로그인 가능 여부 판단하는 메서드 (광진) 9/15일 추가 작업
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
