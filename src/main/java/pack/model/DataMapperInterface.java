package pack.model;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pack.controller.FormBean;
import pack.model.container.ContainerDto;
import pack.model.owner.OwnerDto;
import pack.model.user.UserDto;

@Mapper
public interface DataMapperInterface {
	// User sql문들-------------------------------------------------서호
	@Select("select * from user")
	List<UserDto> selectAll();

	@Select("SELECT * FROM user WHERE user_name LIKE CONCAT('%', #{searchValue}, '%') OR user_id LIKE CONCAT('%', #{searchValue}, '%')")
	List<UserDto> selectSearch(FormBean bean);
	
	// Supplier sql문들-------------------------------------------------
	@Select("select * from owner")
	List<OwnerDto> selectAll2();
	
	@Select("SELECT * FROM owner WHERE business_num LIKE CONCAT('%', #{searchValue}, '%')")
	List<OwnerDto> selectSearch2(FormBean bean);
	
	// Container sql문들-------------------------------------------------
	@Select("select * from contain")
	List<ContainerDto> selectAll3();
	
	
	@Select("select * from contain where con_no=#{con_no}")  // 세부정보 보기
	ContainerDto selectOne(String con_no);
	
	
	@Delete("delete from contain where con_no=#{con_no}")   // 삭제하기
	int delete(String con_no);
}
