package pack.model.contact;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.controller.contact.ContactBean;

@Mapper
public interface ContactMapperInterface {
	
	@Select(" select * from contact order by contact_date desc")
	List<ContactDto> selectContact();
	
	@Select("select count(*) from contact")
	int totalContact();
	
	@Insert("insert into contact values((select max(contact_no)+1 from contact ali),#{contact_name},#{contact_email},#{contact_title},#{contact_message},now(),#{contact_status})")
	int insertContact(ContactBean bean);
	
	@Select("select * from contact where contact_no=#{contact_no} order by contact_date desc")
	ContactDto detailContact(String contact_no);
	
	@Update("update contact set contact_status=#{contact_status} where contact_no=#{contact_no}")
	int updateContact(ContactBean bean);
	
	@Delete("delete from contact where contact_no=#{contact_no}")
	int deleteContact(String contact_no);
}
