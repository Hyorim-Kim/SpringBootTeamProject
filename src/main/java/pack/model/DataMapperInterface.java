package pack.model;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
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
	@Select("select * from container")
	List<ContainerDto> selectAll3();
	
	
	@Select("select * from container where cont_no=#{cont_no}")  // 세부정보 보기
	ContainerDto selectOne(String cont_no);
	
	
	@Delete("delete from container where cont_no=#{cont_no}")   // 삭제하기
	int delete(String cont_no);
	
	
	// 마이페이지에서 자신이 사용한 리뷰/평가 누르면 창고의 번호, 주소, 사진 출력-------------------
	@Select("select * from container")
	List<ContainerDto> selectAll4();

	// user가 작성한 별점과 내용이 rv table에 insert 
	@Insert("INSERT INTO rv (rating, content) VALUES (#{rating}, #{content})")
	int insertReview(ReviewDto reviewDto);
	
	// Ajax
	@Insert("INSERT INTO rv (rating, content) VALUES (#{rating}, #{content})")
	void insertReviewAjax(ReviewDto reviewDto);
	
}
