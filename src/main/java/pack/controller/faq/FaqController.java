package pack.controller.faq;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.model.faq.FaqDao;
import pack.model.faq.FaqDto;

@Controller
public class FaqController {

	@Autowired 
	public FaqDao faqDao;
	
	private int tot;        // 전체 레코드 수
	private int plist = 10;  // 페이지 당 행 수
	
	public ArrayList<FaqDto> getListData(ArrayList<FaqDto> list, int page){
		ArrayList<FaqDto> result = new ArrayList<FaqDto>();
		
		int start = (page - 1) * plist;   // 0, 10, 20, ...
		int end = Math.min(start + plist, list.size());  // 계산된 end 값이 리스트 크기를 초과하지 않도록 조정
		
		for (int i = start; i < end; i++) {
			result.add(list.get(i));
		}
		return result;
	}
	
	public int getPageSu() { // 총 페이지 수 얻기
		tot = faqDao.totalFaq();
		int pagesu = tot / plist;
		if(tot % plist > 0) pagesu += 1;

		return pagesu;
	}
	
	public int getSearchPageSu(FaqBean bean) { // 총 검색 페이지 수 얻기
		ArrayList<FaqDto> searchtot = (ArrayList<FaqDto>)faqDao.searchFaq(bean);
		tot = searchtot.size();
		int pagesu = tot / plist;
		if(tot % plist > 0) pagesu += 1;

		return pagesu;
	}

	@PostMapping("searchfaq")
	public String search(@RequestParam("searchpage")int searchpage, FaqBean bean, Model model) {
		int spage = searchpage;
		if (searchpage <= 0) spage = 1;
		ArrayList<FaqDto> slist = (ArrayList<FaqDto>)faqDao.searchFaq(bean);
		ArrayList<FaqDto> sresult = getListData(slist, spage);
		model.addAttribute("faq", sresult);
		model.addAttribute("pagesu", getSearchPageSu(bean));
		model.addAttribute("page", spage);
		return "../templates/faq/faq";
	}
	
	@PostMapping("searchadmin")
	public String searchadmin(@RequestParam("searchpage")int searchpage, FaqBean bean, Model model) {
		int spage = searchpage;
		if (searchpage <= 0) spage = 1;
		ArrayList<FaqDto> slist = (ArrayList<FaqDto>)faqDao.searchFaq(bean);
		ArrayList<FaqDto> sresult = getListData(slist, spage);
		model.addAttribute("faqadmin", sresult);
		model.addAttribute("pagesu", getSearchPageSu(bean));
		model.addAttribute("page", spage);
		return "../templates/faq/faqadmin";
	}
	
	@GetMapping("faq")
	public String listProcess(@RequestParam("page")int page, Model model) {
		int spage = page;
		if (page <= 0) spage = 1;
		
		ArrayList<FaqDto> list = (ArrayList<FaqDto>)faqDao.listFaq();
		ArrayList<FaqDto> result = getListData(list, spage);
		
		model.addAttribute("faq", result);
		model.addAttribute("pagesu", getPageSu());
		model.addAttribute("page", spage);
		
		return "../templates/faq/faq";
	}
	
	@GetMapping("faqadmin")
	public String listProcessadmin(@RequestParam("page")int page, Model model) {
		int spage = page;
		if (page <= 0) spage = 1;
		
		ArrayList<FaqDto> list = (ArrayList<FaqDto>)faqDao.listFaq();
		ArrayList<FaqDto> result = getListData(list, spage);
		
		model.addAttribute("faqadmin", result);
		model.addAttribute("pagesu", getPageSu());
		model.addAttribute("page", spage);
		
		return "../templates/faq/faqadmin";
	}
	
	@GetMapping("faqinsert")
	public String faqinsert() {
		return "../templates/faq/faqinsert";
	}
	
	@PostMapping("faqinsert")
	public String faqinsertSubmit(FaqBean bean) {
		boolean b = faqDao.insertFaq(bean);
		
		if(b) {
			return "redirect:/faqadmin?page=1"; // 추가 후 목록 보기
		} else {
			return "error";
		}
	}
	
	@PostMapping("faqupdate")
	public String faqupdate(FaqBean bean) {
		boolean b = faqDao.updateFaq(bean);
		
		if(b) {
			return "redirect:/faqadmin?page=1"; // 수정 후 목록 보기
		} else {
			return "error";
		}
	}
	
	@PostMapping("faqdelete")
	public String faqdelete(@RequestParam("faq_no")String faq_no) {
		boolean b = faqDao.deleteFaq(faq_no);
		if(b) {
			return "redirect:/faqadmin?page=1"; // 삭제 후 목록 보기
		} else {
			return "error";
		}
	}
	
	@GetMapping("faqdetail")
	public String faqdetail(@RequestParam("faq_no")int faq_no, Model model) {
		FaqDto detail = faqDao.detailFaq(faq_no);
		model.addAttribute("detail", detail);
		return "../templates/faq/faqdetail";
	}
}