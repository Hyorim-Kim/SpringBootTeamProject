package pack.model.faq;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.controller.faq.FaqBean;

@Mapper
public interface FaqMapperInterface {

	@Select("select * from faq order by faq_no desc")
	List<FaqDto> selectFaq();
	
	@Select("select * from faq where ${searchName} like concat('%',#{searchValue},'%') order by faq_no desc")
	List<FaqDto> searchFaq(FaqBean bean);
	
	@Select("select count(*) from faq ")
	int totalFaq();
	
	@Insert("insert into faq values((select max(faq_no)+1 from faq ali),#{faq_category},#{faq_question},#{faq_answer})")
	int insertFaq(FaqBean bean);
	
	@Update("update faq set faq_category=#{faq_category},faq_question=#{faq_question},faq_answer=#{faq_answer} where faq_no=#{faq_no}")
	int updateFaq(FaqBean bean);
	
	@Delete("delete from faq where faq_no=#{faq_no}")
	int deleteFaq(String faq_no);
	
	@Select("select * from faq where faq_no=#{faq_no}")
	FaqDto selectno(int faq_no);
}
