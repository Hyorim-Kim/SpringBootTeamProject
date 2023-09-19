package pack.model.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.model.DataMapperInter;


//************ 광진 **************// 
@Repository
public class OwnerDao {
	
	@Autowired
	private DataMapperInter dataMapperInter;
	
	// 회원 가입 페이지 필드란에 입력 데이터가 없어도 DB에 들어가는걸 방지
	private boolean isValidOwnerData(OwnerDto ownerDto) {
	    boolean b = false; // 초기값을 false로 설정합니다.

	    // 각 필드의 유효성 검사를 수행합니다.
	    if (isEmpty(ownerDto.getBusiness_num()) ||
	        isEmpty(ownerDto.getOwner_pwd()) ||
	        isEmpty(ownerDto.getOwner_name()) ||
	        isEmpty(ownerDto.getOwner_tel()) ||
	        isEmpty(ownerDto.getEmail())) {
	        b = false; // 어떤 필드라도 비어 있다면 유효하지 않음.
	    } else {
	    	// 추후에 사업자 번호 정규식등 정규식에 필요한 코드 로직 구현 예정
	        // 비밀번호와 비밀번호 확인 필드 비교
	        if (!ownerDto.getOwner_pwd().equals(ownerDto.getOwner_repwd())) {
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
	
	public boolean ownerinsertData(OwnerDto ownerDto) {
	    boolean b = false;
	    if (isValidOwnerData(ownerDto)) {
	        int re = dataMapperInter.ownerinsertData(ownerDto);
	        if (re > 0) {
	            b = true;
	        }
	    }
	    return b;
	}
	
	// 공급자 로그인 사용 여부 메서드
    public OwnerDto ownerloginProcess(String business_num, String owner_pwd) {
        return dataMapperInter.ownerloginProcess(business_num, owner_pwd);
    }
  
    // 공급자 회원수정 여부 메서드
    public boolean ownerupdate(OwnerDto ownerDto) {
    	boolean b = false;
    	int re = dataMapperInter.ownerupdate(ownerDto);
		if(re > 0) b = true;
		return b;
    }
    
    // 공급자 회원탈퇴 여부 메서드
    public boolean ownerdelete(OwnerDto ownerDto) {
    	boolean b = false;
    	int re = dataMapperInter.ownerdelete(ownerDto);
		if(re >= 0) b = true;
		return b;
    }
}
