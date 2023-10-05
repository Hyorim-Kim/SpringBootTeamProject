package pack.model.container;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.controller.container.FormBean;
import pack.model.booking.bookingDTO;




// 자신이 소유한 창고 상태만 확인 할 수 있도록 출력 해야함
// 회원가입, 로그인 기능 완성되면 연결 할 수 있을 듯 owner_no 또는 id 로 조건 주면 될 듯?
// 서브쿼리 써야 할수도 있음
@Mapper
public interface ContainerMapperInterface {
	
	@Select("select * from container inner join owner on owner.business_num=container.owner_num where business_num = #{business_num}")
	List<ContainerDto> selectAll(@Param("business_num") String business_num); 
	// 로그인한 임대인이 가지고 있는 창고정보를 볼 수 있는 쿼리문
	// 쿼리문을 통해 데이터베이스에 있는 값들을 불러와 dao로 전달하고, 조건절에 로그인한 사업자번호를 입력하기위해 파라미터를 작성해줌
	

	@Insert("insert into container(cont_no, cont_addr, cont_we, cont_kyung, cont_size, cont_image, owner_num, cont_explain, cont_name, owner_phone)\r\n"
			+ "values ((select max(cont_no) + 1 from container num), #{cont_addr}, #{cont_we}, #{cont_kyung}, #{cont_size}, #{cont_image}, #{owner_num}, #{cont_explain}, #{cont_name}, #{owner_phone})\r\n"
			+ "")
	int insertContainer(FormBean bean);

	// 빈이랑 매핑됨
	// 공급자가 새로 등록하고자하는 창고정보 입력 쿼리문
	// 쿼리문을 통해 bean에 값을 밀어넣어주고 있음
	// int로 선언 한 이유는 dao에서 조건문에서 int 타입으로 판단을 하기때문에;;;?
	// 암튼 dao로 이동해봐
	
//	@Insert("insert into container(cont_no, cont_addr, cont_size, cont_image, owner_num) values ((select max(cont_no) + 1 from container num), #{cont_addr}, #{cont_size}, #{cont_image}, #{owner_num})")
//	void insertContainer(FormBean bean); 
	
	@Select("SELECT * FROM container INNER JOIN owner ON owner.business_num = container.owner_num WHERE cont_no = #{cont_no}")
	ContainerDto selectOne(String cont_no);
	
	@Select("SELECT * FROM container inner join booking_board on container.cont_no = booking_board.cont_no inner join user on user.user_id = booking_board.user_id where container.cont_no = #{cont_no}")
	bookingDTO selectbookcont(String cont_no);
	
	@Update("update container inner join owner on owner.business_num=container.owner_num set owner_name=#{owner_name}, cont_addr=#{cont_addr}, cont_we=#{cont_we}, cont_kyung=#{cont_kyung}, cont_size=#{cont_size}, cont_explain=#{cont_explain} where cont_no=#{cont_no}")
	int update(FormBean bean);
	
	@Delete("delete from container where cont_no = #{cont_no}")
	int delete(String cont_no);
	
	@Select("select * from container inner join owner on owner.business_num = container.owner_num where cont_status=2 and business_num = #{business_num}")
	List<ContainerDto> selectReserve(@Param("business_num") String business_num);
	
	@Select("select * from container inner join owner on owner.owner_no=container.owner_num where cont_status=using")
	List<ContainerDto> selectAll3();
	
	
	
}
