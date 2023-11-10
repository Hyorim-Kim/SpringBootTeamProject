package pack.model.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.model.DataMapperInter;


@Repository
public class OwnerDao {
	
	@Autowired
	private DataMapperInter dataMapperInter;
	
	
	private boolean isEmpty(String value) {
	    return value == null || value.trim().isEmpty();
	}

	private boolean joinOwnerData(OwnerDto ownerDto) {
	    boolean b = true; 

	    if (
	        isEmpty(ownerDto.getBusiness_num()) ||
	        isEmpty(ownerDto.getOwner_pwd()) ||
	        isEmpty(ownerDto.getOwner_name()) ||
	        isEmpty(ownerDto.getOwner_tel()) ||
	        isEmpty(ownerDto.getEmail()) ||
	        // 정규식 표현 적용
	        !ownerDto.getBusiness_num().matches("^[0-9-]+$") ||
	        !ownerDto.getOwner_tel().matches("^[0-9-]+$") ||
	        !ownerDto.getOwner_pwd().equals(ownerDto.getOwner_repwd()) ||
	        !ownerDto.getOwner_name().matches("^[가-힣]{2,}$") ||
	        !ownerDto.getOwner_pwd().matches("^.{4,}$") ||
	        !ownerDto.getEmail().matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")
	    ) {
	        b = false;
	    } else {
            b = true;
        }

	    return b;
	}

	public boolean ownerInsertData(OwnerDto ownerDto) {
	    boolean b = false;
	    if (joinOwnerData(ownerDto)) {
	        int re = dataMapperInter.ownerInsertData(ownerDto);
	        if (re > 0) {
	            b = true;
	        }
	    }
	    return b;
	}

    public OwnerDto ownerLoginProcess(String business_num, String owner_pwd) {
        return dataMapperInter.ownerLoginProcess(business_num, owner_pwd);
    }

    public boolean ownerUpdate(OwnerDto ownerDto) {
    	boolean b = false;
    	int re = dataMapperInter.ownerUpdate(ownerDto);
		if(re > 0) b = true;
		return b;
    }

    public boolean ownerDelete(OwnerDto ownerDto) {
    	boolean b = false;
    	int re = dataMapperInter.ownerDelete(ownerDto);
		if(re >= 0) b = true;
		return b;
    }
}
