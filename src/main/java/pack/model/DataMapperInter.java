package pack.model;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.model.admin.AdminDto;
import pack.model.owner.OwnerDto;
import pack.model.user.UserDto;

// @Mapper 어노테이션이 붙은 DataMapperInter 인터페이스는 데이터베이스와 Java 객체 간의 매핑 작업을 정의하는 인터페이스이다.
@Mapper
public interface DataMapperInter {
	
	/*** 회원가입, 회원수정, 회원탈퇴, 로그인, 아이디/비밀번호 찾기 에 필요한 SQL 쿼리문 (광진) ***/
	
	// User 회원가입 SQL 쿼리 (광진)
	@Insert("INSERT INTO user (user_id, user_pwd, user_name, user_tel, user_email, user_addr, user_jumin) \r\n"
			+ "VALUES (#{user_id}, #{user_pwd}, #{user_name}, #{user_tel}, #{user_email}, #{user_addr}, #{user_jumin})")
	int userInsertData(UserDto userDto);
	
	// Owner 회원가입 SQL 쿼리 (광진)
	@Insert("INSERT INTO owner (business_num, owner_pwd, owner_name, owner_tel, email) \r\n"
			+ "VALUES (#{business_num}, #{owner_pwd}, #{owner_name}, #{owner_tel}, #{email})")
	int ownerInsertData(OwnerDto ownerDto);
	
	// User 로그인 (광진)
	@Select("select * from user where user_id=#{user_id} and user_pwd=#{user_pwd}")
	UserDto userLoginProcess(@Param("user_id") String user_id, @Param("user_pwd") String user_pwd);
	
	// Owner 로그인 (광진)
	@Select("select * from owner where business_num=#{business_num} and owner_pwd=#{owner_pwd}")
	OwnerDto ownerLoginProcess(@Param("business_num") String business_num, @Param("owner_pwd") String owner_pwd);
	 
	// Admin 로그인 (광진)
	@Select("select * from admin where admin_id=#{admin_id} and admin_pwd=#{admin_pwd}")
	AdminDto adminLoginProcess(@Param("admin_id") String admin_id, @Param("admin_pwd") String admin_pwd);
	
	// User 회원수정 (광진)
	@Update("update user set user_pwd=#{user_pwd}, user_name=#{user_name}, user_tel=#{user_tel}, user_email=#{user_email}, user_addr=#{user_addr}, user_jumin=#{user_jumin} where user_id=#{user_id}")
	int userUpdate(UserDto userDto);
	
	// Owner 회원수정 (광진)
	@Update("update owner set owner_pwd=#{owner_pwd}, owner_name=#{owner_name}, owner_tel=#{owner_tel}, email=#{email} where business_num=#{business_num}")
	int ownerUpdate(OwnerDto ownerDto);
	
	// User 회원탈퇴 (광진)
	@Delete("delete from user WHERE user_id = #{user_id}")
	int userDelete(UserDto userDto);
	
	// Owner 회원탈퇴 (광진)
	@Delete("delete from owner WHERE business_num = #{business_num}")
	int ownerDelete(OwnerDto ownerDto);
	
	// User id 중복체크 (광진)
	@Select("select count(*) from user where user_id=#{user_id}")
	// 주어진 사용자 ID가 데이터베이스에 이미 존재하는 경우, 그 중복된 사용자 ID의 개수를 나타낸다
	// 예를 들어, 1이 반환되면 해당 ID가 한 번 중복됐음을 의미하기 위해서 반환 타입을 정수형으로 설정 
	int userIdCheck(@Param("user_id") String user_id); 
	
	// User 아이디 찾기
	@Select("select user_id from user where user_name = #{user_name} and user_email = #{user_email} and user_jumin = #{user_jumin}")
	UserDto userIdFind(@Param("user_name") String user_name, @Param("user_email") String user_email, @Param("user_jumin") String user_jumin);

}
