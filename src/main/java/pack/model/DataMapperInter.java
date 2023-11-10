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


	@Insert("INSERT INTO user (user_id, user_pwd, user_name, user_tel, user_email, user_addr, user_jumin) \r\n"
			+ "VALUES (#{user_id}, #{user_pwd}, #{user_name}, #{user_tel}, #{user_email}, #{user_addr}, #{user_jumin})")
	int userInsertData(UserDto userDto);

	@Insert("INSERT INTO owner (business_num, owner_pwd, owner_name, owner_tel, email) \r\n"
			+ "VALUES (#{business_num}, #{owner_pwd}, #{owner_name}, #{owner_tel}, #{email})")
	int ownerInsertData(OwnerDto ownerDto);

	@Select("select * from user where user_id=#{user_id} and user_pwd=#{user_pwd}")
	UserDto userLoginProcess(@Param("user_id") String user_id, @Param("user_pwd") String user_pwd);

	@Select("select * from owner where business_num=#{business_num} and owner_pwd=#{owner_pwd}")
	OwnerDto ownerLoginProcess(@Param("business_num") String business_num, @Param("owner_pwd") String owner_pwd);

	@Select("select * from admin where admin_id=#{admin_id} and admin_pwd=#{admin_pwd}")
	AdminDto adminLoginProcess(@Param("admin_id") String admin_id, @Param("admin_pwd") String admin_pwd);

	@Update("update user set user_pwd=#{user_pwd}, user_name=#{user_name}, user_tel=#{user_tel}, user_email=#{user_email}, user_addr=#{user_addr}, user_jumin=#{user_jumin} where user_id=#{user_id}")
	int userUpdate(UserDto userDto);

	@Update("update owner set owner_pwd=#{owner_pwd}, owner_name=#{owner_name}, owner_tel=#{owner_tel}, email=#{email} where business_num=#{business_num}")
	int ownerUpdate(OwnerDto ownerDto);

	@Delete("delete from user WHERE user_id = #{user_id}")
	int userDelete(UserDto userDto);

	@Delete("delete from owner WHERE business_num = #{business_num}")
	int ownerDelete(OwnerDto ownerDto);

	@Select("select count(*) from user where user_id=#{user_id}")
	int userIdCheck(@Param("user_id") String user_id); 

	@Select("select user_id from user where user_name = #{user_name} and user_email = #{user_email} and user_jumin = #{user_jumin}")
	UserDto userIdFind(@Param("user_name") String user_name, @Param("user_email") String user_email, @Param("user_jumin") String user_jumin);

}
