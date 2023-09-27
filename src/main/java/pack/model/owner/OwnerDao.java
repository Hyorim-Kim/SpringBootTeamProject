package pack.model.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.model.DataMapperInter;


//************ 광진 **************// 
@Repository
public class OwnerDao {
	
	@Autowired
	private DataMapperInter dataMapperInter;
	
	
	private boolean isEmpty(String value) {
	    return value == null || value.trim().isEmpty();
	}

	private boolean joinOwnerData(OwnerDto ownerDto) {
	    boolean b = true; 
	    
	    // 회원 가입 페이지 필드란에 입력 데이터가 없어도 DB에 들어가는걸 방지
	    // 각 필드의 유효성 검사를 수행
	    if (
	        isEmpty(ownerDto.getBusiness_num()) ||
	        isEmpty(ownerDto.getOwner_pwd()) ||
	        isEmpty(ownerDto.getOwner_name()) ||
	        isEmpty(ownerDto.getOwner_tel()) ||
	        isEmpty(ownerDto.getEmail()) ||
	        // 정규식 표현 적용
	        !ownerDto.getBusiness_num().matches("^[0-9-]+$") || // 숫자와 하이픈만 허용
	        !ownerDto.getOwner_tel().matches("^[0-9-]+$") || // 숫자와 하이픈만 허용
	        !ownerDto.getOwner_pwd().equals(ownerDto.getOwner_repwd()) || // 비밀번호 확인
	        !ownerDto.getOwner_name().matches("^[가-힣]{2,}$") || // 한글 2글자 이상
	        !ownerDto.getOwner_pwd().matches("^.{4,}$") || // 비밀번호 4글자 이상
	        !ownerDto.getEmail().matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")  // 이메일 주소 패턴
	    ) {
	        b = false; // 어떤 필드라도 비어 있거나 비밀번호가 일치하지 않으면 유효하지 않음.
	    } else {
            b = true; // 모든 필드와 정규식에 유효하고 비밀번호가 일치하면 true로 설정.
        }

	    return b;
	}

	// 공급자 회원가입에 필요한 메서드
	public boolean ownerinsertData(OwnerDto ownerDto) {
	    boolean b = false;
	    if (joinOwnerData(ownerDto)) {
	        int re = dataMapperInter.ownerInsertData(ownerDto);
	        if (re > 0) {
	            b = true;
	        }
	    }
	    return b;
	}
	
	// 공급자 로그인 사용 여부 메서드
    public OwnerDto ownerloginProcess(String business_num, String owner_pwd) {
        return dataMapperInter.ownerLoginProcess(business_num, owner_pwd);
    }
  
    // 공급자 회원수정 여부 메서드
    public boolean ownerupdate(OwnerDto ownerDto) {
    	boolean b = false;
    	int re = dataMapperInter.ownerUpdate(ownerDto);
		if(re > 0) b = true;
		return b;
    }
    
    // 공급자 회원탈퇴 여부 메서드
    public boolean ownerdelete(OwnerDto ownerDto) {
    	boolean b = false;
    	int re = dataMapperInter.ownerDelete(ownerDto);
		if(re >= 0) b = true;
		return b;
    }
    
    // 추후 공급자가 창고를 등록을 한 상태라면 회원탈퇴가 되지 않도록 로직 구성
}
