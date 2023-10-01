package pack.controller.board;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.model.board.BoardDaoImpl;
import pack.model.board.BoardDto;


@Controller
@RequestMapping("/board")
public class ListControllerBoard {
	@Autowired // 의존성주입, BoardDaoImpl 타입의 빈 주입 받음
	private BoardDaoImpl daoImpl;
	
	private int tot;        // 전체 레코드 수 저장
	private int plist = 15;  // ﻿한 페이지 당 표시할 글의 개수
	private int pagesu;    // 전체 페이지 수

	// 페이지 당 목록 데이터 추출
	public ArrayList<BoardDto> getListdata(ArrayList<BoardDto> list, int page){
		ArrayList<BoardDto> result = new ArrayList<BoardDto>();
		
		int start = (page - 1) * plist;   // 목록의 인덱스 계산
		System.out.println("start:" + start);
		
		int size = plist <= list.size() - start?plist : list.size() - start;   // 삼항 연산, 한 페이지에 표시할 글의 개수 계산
		
		for (int i = 0; i < size; i++) { // 시작 인덱스부터 페이지당 글의 개수만큼 데이터 추출, result 리스트에 추가
			result.add(i, list.get(start + i));
			System.out.println("i:" + i + ", start + i : " + (start + i));
		}
		return result;
	}
	
	// 총 페이지 수 계산
	public int getPageSu() { // 총 페이지 수 얻기
		tot = daoImpl.totalCnt(); // daoImpl.totalCnt() : 데이터베이스에 저장된 총 레코드 수 조회
		pagesu = tot / plist;
		if(tot % plist > 0) pagesu += 1;
		return pagesu;
	}
	
	@GetMapping("/list")
	public String listProcess(@RequestParam("page")int page, Model model) {
		int spage = 0;
		try {
			spage = page;
		} catch (Exception e) {
			spage = 1;
		}
		if(page <= 0) spage = 1; // 페이지가 1보다 작으면 1로 설정
		
		// 전체 목록 데이터를 가져옴
		// paging 처리
		ArrayList<BoardDto> list = (ArrayList<BoardDto>)daoImpl.listAll();
		ArrayList<BoardDto> result = getListdata(list, spage); // 현재 페이지에 해당하는 부분 추출
		
		model.addAttribute("data", result);
		model.addAttribute("pagesu", getPageSu());
		model.addAttribute("page", spage);
		
		return "board/list";
	}
	
	// listAdmin, listUser, listOwner는 list 메서드와 비슷한 동작을 수행
	@GetMapping("/listAdmin")
	public String listAdminProcess(@RequestParam("page")int page, Model model) {
		int spage = 0;
		try {
			spage = page;
		} catch (Exception e) {
			spage = 1;
		}
		if(page <= 0) spage = 1;
		
		ArrayList<BoardDto> list = (ArrayList<BoardDto>)daoImpl.listAll();
		ArrayList<BoardDto> result = getListdata(list, spage);
		
		model.addAttribute("data", result);
		model.addAttribute("pagesu", getPageSu());
		model.addAttribute("page", spage);
		
		return "board/listAdmin";
	}
	
	@GetMapping("/listUser")
	public String listUserProcess(@RequestParam("page")int page, Model model) {
		int spage = 0;
		try {
			spage = page;
		} catch (Exception e) {
			spage = 1;
		}
		if(page <= 0) spage = 1;
		
		ArrayList<BoardDto> list = (ArrayList<BoardDto>)daoImpl.listAll();
		ArrayList<BoardDto> result = getListdata(list, spage);
		
		model.addAttribute("data", result);
		model.addAttribute("pagesu", getPageSu());
		model.addAttribute("page", spage);
		
		return "board/listUser";
	}
	
	@GetMapping("/listOwner")
	public String listOwnerProcess(@RequestParam("page")int page, Model model) {
		int spage = 0;
		try {
			spage = page;
		} catch (Exception e) {
			spage = 1;
		}
		if(page <= 0) spage = 1;
		
		ArrayList<BoardDto> list = (ArrayList<BoardDto>)daoImpl.listAll();
		ArrayList<BoardDto> result = getListdata(list, spage);
		
		model.addAttribute("data", result);
		model.addAttribute("pagesu", getPageSu());
		model.addAttribute("page", spage);
		
		return "board/listOwner";
	}
	
	// 검색 결과를 가져옴
	@PostMapping("board/search")
	public String searchProcess(BoardBean bean, Model model) { // 검색관련 데이터 처리
		System.out.println(bean.getSearchName() + " " + bean.getSearchValue()); // 디버깅
		ArrayList<BoardDto> list = (ArrayList<BoardDto>)daoImpl.search(bean); // 결과 가져옴
		
		model.addAttribute("data", list);
		model.addAttribute("pagesu", getPageSu());
		model.addAttribute("page", "1");  // 검색 결과는 1페이지로 설정
		return "board/list";
	}

}
