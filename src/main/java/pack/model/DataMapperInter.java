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

@Mapper
public interface DataMapperInter {
	
	/**** 회원가입, 회원수정, 회원탈퇴, 로그인에 필효한 SQL 쿼리문 (광진) ****/
	
	// User 회원가입 SQL 쿼리 (광진)
	@Insert("INSERT INTO user (user_id, user_pwd, user_name, user_tel, user_email, user_addr, user_jumin) \r\n"
			+ "VALUES (#{user_id}, #{user_pwd}, #{user_name}, #{user_tel}, #{user_email}, #{user_addr}, #{user_jumin})")
	int userinsertData(UserDto userDto);
	
	// Owner 회원가입 SQL 쿼리 (광진)
	@Insert("INSERT INTO owner (business_num, owner_pwd, owner_name, owner_tel, email) \r\n"
			+ "VALUES (#{business_num}, #{owner_pwd}, #{owner_name}, #{owner_tel}, #{email})")
	int ownerinsertData(OwnerDto ownerDto);
	
	// User 로그인 (광진)
	@Select("select * from user where user_id=#{user_id} and user_pwd=#{user_pwd}")
	UserDto userloginProcess(@Param("user_id") String user_id, @Param("user_pwd") String user_pwd);
	
	// Owner 로그인 (광진)
	@Select("select * from owner where business_num=#{business_num} and owner_pwd=#{owner_pwd}")
	OwnerDto ownerloginProcess(@Param("business_num") String business_num, @Param("owner_pwd") String owner_pwd);
	 
	// Admin 로그인 (광진)
	@Select("select * from admin where admin_id=#{admin_id} and admin_pwd=#{admin_pwd}")
	AdminDto adminloginProcess(@Param("admin_id") String admin_id, @Param("admin_pwd") String admin_pwd);
	
	// User 회원수정 (광진)
	@Update("update user set user_pwd=#{user_pwd}, user_name=#{user_name}, user_tel=#{user_tel}, user_email=#{user_email}, user_addr=#{user_addr}, user_jumin=#{user_jumin} where user_id=#{user_id}")
	int userupdate(UserDto userDto);
	
	// Owner 회원수정 (광진)
	@Update("update owner set owner_pwd=#{owner_pwd}, owner_name=#{owner_name}, owner_tel=#{owner_tel}, email=#{email} where business_num=#{business_num}")
	int ownerupdate(OwnerDto ownerDto);
	
	// User 회원탈퇴 (광진)
	@Delete("delete from user WHERE user_id = #{user_id}")
	int userdelete(UserDto userDto);
	
	// Owner 회원탈퇴 (광진)
	
}
