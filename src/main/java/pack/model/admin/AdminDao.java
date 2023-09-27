package pack.model.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pack.model.DataMapperInter;


//*********** 광진 *************//
@Repository
public class AdminDao {
	@Autowired
	private DataMapperInter dataMapperInter;
	@Autowired
	private ApproveMapperInter approveMapperInter;
	
	
	/*******광진  관리자 로그인 여부  *******/
    public AdminDto adminloginProcess(String admin_id, String admin_pwd) {
        return dataMapperInter.adminloginProcess(admin_id, admin_pwd);
    }
    
    
    // 9/27 민혁 관리자 창고 승인 매핑
    @Transactional // 성공하면 커밋 실패하면 롤백
    public boolean approve(AdminBean adminbean) {
        boolean b = false;
        System.out.println("approve 메서드 시작");
        try {
            int re = approveMapperInter.approve(adminbean);
            System.out.println("SQL 실행 결과: " + re);
            if (re > 0)
                b = true;
        } catch (Exception e) {
            // 예외 발생 시 처리
            System.out.println("예외 발생: " + e.getMessage());
            b = false;
        }
        return b;
    }
    
    public boolean deny(AdminBean adminbean) {
        boolean b = false;
        System.out.println("deny 메서드 시작");
        try {
            int re = approveMapperInter.deny(adminbean);
            System.out.println("SQL 실행 결과: " + re);
            if (re > 0)
                b = true;
        } catch (Exception e) {
            // 예외 발생 시 처리
            System.out.println("예외 발생: " + e.getMessage());
            b = false;
        }
        return b;
    }
}
