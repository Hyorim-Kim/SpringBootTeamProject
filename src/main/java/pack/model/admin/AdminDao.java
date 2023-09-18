package pack.model.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.model.DataMapperInter;


//*********** 광진 *************//
@Repository
public class AdminDao {
	@Autowired
	private DataMapperInter dataMapperInter;
	
	
	/*******광진  관리자 로그인 여부  *******/
    public AdminDto adminloginProcess(String admin_id, String admin_pwd) {
        return dataMapperInter.adminloginProcess(admin_id, admin_pwd);
    }

}
