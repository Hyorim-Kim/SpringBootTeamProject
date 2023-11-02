package pack.model.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.servlet.http.HttpSession;
import pack.model.DataMapperInter;

// ************ 광진 ************** // 

@Repository 
public class UserDao { 
	
	@Autowired
	private DataMapperInter dataMapperInter;
	

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
	
    // 사용자의 회원가입 데이터 유효성을 검사하고, 필드 값이 비어 있거나 정규식 패턴에 맞지 않는 경우 회원가입을 방지하는 데 사용되는 메서드
    private boolean joinUserData(UserDto userDto) {
        boolean b = false; 
        // 필드 중 하나라도 비어 있거나 정규식에 유효하지 않으면 false를 반환하여 회원 가입을 방지하기 위해 사용
        if (isEmpty(userDto.getUser_id()) ||    
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
    
    // UserDto 객체인 userDto를 파라미터로 받아 사용자가 입력한 회원가입 데이터를 처리하고 성공 여부를 boolean 값으로 반환.
    public boolean userInsertData(UserDto userDto) {
		boolean b = false;
		try {
			if (joinUserData(userDto)) { // // 데이터가 유효할 때 (true 값일 때)
				int re = dataMapperInter.userInsertData(userDto);
				if (re > 0) {
					b = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); 
			// b 변수를 false로 설정하여 회원가입 작업의 실패
		}
		return b;
    }
	    
	
	// 사용자 로그인 가능 여부 판단하는 메서드 (광진) 
    // 데이터베이스에서 사용자 로그인 프로세스를 수행하기 위해 호출되는 메서드이다. (user_id와 user_pwd라는 두 개의 문자열 매개변수를 받는다)
    public UserDto userLoginProcess(String user_id, String user_pwd) {
    	// 반환되는 값은 클라이언트가 입력한 아이디와 비밀번호 값이다.
        return dataMapperInter.userLoginProcess(user_id, user_pwd);
    }
    
    // 사용자 회원수정에 필요한 메서드(광진)
    public boolean userDataUpdate(UserDto userDto) {
    	// boolean 기본 타입이 false지만 가독성을 위해 추가
    	boolean b = false;
    	// 데이터베이스에서의 수정 쿼리문을 수행하고 영향을 받은 행의 수를 re 변수에 저장
		int re = dataMapperInter.userUpdate(userDto); 
		// 필드값이 하나라도 수정이 되면 b가 true로 반환 
		if(re > 0) b = true;
		return b;  	
    }
    
    // 회원삭제 (광진)   
    public boolean userDataDelete(UserDto userDto) {
        boolean b = false; // 초기에 b를 false로 설정      
        // 데이터베이스에서의 삭제 연산을 수행하고 영향을 받은 행의 수를 re 변수에 저장
        int re = dataMapperInter.userDelete(userDto);        
        // re 변수에 저장된 영향을 받은 행의 수를 확인하고 re 값이 0보다 크면, 데이터베이스에서 한 개 이상의 행이 삭제되었다는 의미이므로 true를 반환
        // 반대로, re 값이 0 이하인 경우, 삭제된 행이 없거나 삭제 작업이 실패했다는 의미이므로 false를 반환
        if (re > 0) {
            b = true;
        }      
        // b 값을 반환
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
