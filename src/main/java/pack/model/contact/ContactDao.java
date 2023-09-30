package pack.model.contact;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pack.controller.contact.ContactBean;

@Repository
public class ContactDao {

	@Autowired
	private ContactMapperInterface mapperInterface;
	
	public List<ContactDto> listContact(){
		List<ContactDto> list = mapperInterface.selectContact();
		return list;
	}
	
	public int totalContact() {
		return mapperInterface.totalContact();
	}
	
	@Transactional
	public boolean insertContact(ContactBean bean) {
		boolean b = false;
		int re = mapperInterface.insertContact(bean);
		if(re>0) b = true;
		return b;
	}
	
	public ContactDto detailContact(String contact_no) {
		ContactDto detail = mapperInterface.detailContact(contact_no);
        return detail;
	}
	
	@Transactional
	public boolean updateContact(ContactBean bean) {
		boolean b = false;
		int re = mapperInterface.updateContact(bean);
		if(re>0) b = true;
		return b;
	}
	
	@Transactional
	public boolean deleteContact(String contact_no) {
		boolean b = false;
		int re = mapperInterface.deleteContact(contact_no);
		if(re>0) b = true;
		return b;
	}
}
