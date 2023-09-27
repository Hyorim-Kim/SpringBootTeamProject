package pack.model.faq;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pack.controller.faq.FaqBean;

@Repository
public class FaqDao {

	@Autowired
	private FaqMapperInterface mapperInterface;
	
	public List<FaqDto> listFaq(){
		List<FaqDto> list = mapperInterface.selectFaq();
		return list;
	}
	
	public List<FaqDto> searchFaq(FaqBean bean){
		List<FaqDto> slist = mapperInterface.searchFaq(bean);
		return slist;
	}
	
	public int totalFaq() {
		return mapperInterface.totalFaq();
	}
	
	@Transactional
	public boolean insertFaq(FaqBean bean) {
		boolean b = false;
		int re = mapperInterface.insertFaq(bean);
		if(re>0) b = true;
		return b;
	}
	
	@Transactional
	public boolean updateFaq(FaqBean bean) {
		boolean b = false;
		int re = mapperInterface.updateFaq(bean);
		if(re>0) b = true;
		return b;
	}
	
	@Transactional
	public boolean deleteFaq(String faq_no) {
		//System.out.println(faq_no);
		boolean b = false;
		int re = mapperInterface.deleteFaq(faq_no);
		if(re>0) b = true;
		return b;
	}
	
	public FaqDto detailFaq(int faq_no) {
		FaqDto detail = mapperInterface.selectno(faq_no);
		return detail;
	}
}
