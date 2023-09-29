package pack.model.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.servlet.http.HttpSession;
import pack.model.DataMapperInter;

// ************ 광진 ************** // 

@Repository
public class UserDao { // DAO 는 쉽게 말해서 DB 서버에 접근하여 SQL문을 실행할 수 있는 객체
	
	@Autowired
	private DataMapperInter dataMapperInter;
	
	// isEmpty() 정의 문자열이 비였는지 여부 체크.
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
            isEmpty(userDto.getUser_addr()) ||
            
            !userDto.getUser_id().matches("^[a-zA-Z\\d]{4,}$") ||
            !userDto.getUser_tel().matches("^[0-9-]+$") || 
            !userDto.getUser_jumin().matches("^\\d{6}-\\d{7}$") ||
            !userDto.getUser_pwd().equals(userDto.getUser_repwd()) || // 비밀번호 필드값과 비밀번호 확인 필드값의 데이터가 값은지 확인한다
            !userDto.getUser_name().matches("^[가-힣]{2,}$") ||
            !userDto.getUser_pwd().matches("^.{4,}$") ||          
            !userDto.getUser_email().matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")) { 
        	// 비밀번호가 일치하지 않거나 이름, 비밀번호, 이메일이 조건을 만족하지 않으면 유효하지 않음.
            b = false; 
        } else {
        	// 모든 필드와 정규식에 유효하고 비밀번호가 일치하면 true로 설정.
            b = true; 
        }
        // 최종 반환값
        return b;
    }

	// 사용자 회원가입에 사용되는 메서드 (광진)
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
			// 예외 발생 시에도 b는 false로 유지
		}
		return b;
    }
	    
	
	// 사용자 로그인 가능 여부 판단하는 메서드 (광진) 9/15일 추가 작업
    public UserDto userLoginProcess(String user_id, String user_pwd) {
        return dataMapperInter.userLoginProcess(user_id, user_pwd);
    }
    
    // 사용자 회원수정에 필요한 메서드(광진)
    public boolean userDataUpdate(UserDto userDto) {
    	// boolean 기본 타입이 false지만 가독성을 위해 추가
    	boolean b = false;
		int re = dataMapperInter.userUpdate(userDto); 
		// 필드값이 하나라도 수정이 되면 b가 true로 반환 
		if(re > 0) b = true;
		return b;  	
    }
    
    /*** 9/15일 추가 작업 (회원삭제) 광진 ***/   
    public boolean userDataDelete(UserDto userDto) {
    	boolean b = false;
    	// 데이터베이스에서의 삭제 연산을 수행하고 영향을 받은 행의 수를 re 변수에 저장
    	int re = dataMapperInter.userDelete(userDto);
    	// 필드값이 그대로거나, 하나이상의 값이 수정이 되면 b가 true로 반환 
		if(re >= 0) b = true;
		return b; 
    }
    
    // 사용자 회원가입시 중복체크 (광진)
    public int userIdCheck(String user_id) {
    	int result = dataMapperInter.userIdCheck(user_id);
		return result; // 중복되는 경우 1을 반환하고, 중복되지 않으면 0을 반환
    }
    
    // 사용자 아이디 찾기 (광진)
    public UserDto userIdFind(String user_name, String user_email, String user_jumin) {
    	return dataMapperInter.userIdFind(user_name, user_email, user_jumin);
    }
}
