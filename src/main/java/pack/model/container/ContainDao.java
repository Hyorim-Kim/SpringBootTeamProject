package pack.model.container;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pack.controller.FormBean;

//재민
@Repository
public class ContainDao {
	@Autowired
	private kakaoMapperInterface kakaoMapperInterface; // 히카리
	
	// 전체자료 읽기
	public List<ContainDtoMap> getcontainAll() {
		List<ContainDtoMap> slist = kakaoMapperInterface.MapAll();

		return slist;
	}
	
	// 민혁
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ContainerMapperInterface containerMapper;
	// HikariCP는 스프링부트에 기본으로 내장되어 있는 JDBC 데이터베이스 커넥션 풀링 프레임워크이다.

	public List<ContainerDto> getDataAll() { // 창고 전체보기
		List<ContainerDto> list = containerMapper.selectAll();
		logger.info("datas : " + list.size() + "개");
		return list;

	}

	public ContainerDto conDetail(String cont_no) { // 상세보기 용
		System.out.println("cont_no : " + cont_no);
		ContainerDto conDto = containerMapper.selectOne(cont_no);
		System.out.println(conDto);
		return conDto;
	}

	@Transactional
	public boolean insertContainer(FormBean bean) {
		// 요거슨 매퍼를 통해 bean값을 전달받아 데이터를 블라블라해서
		// 리턴값을 컨트롤러로 전달하는 거같음
		// 컨트롤러로 이동해봐 insertSubmit() 메소드로
		boolean b = false;
		int re = containerMapper.insertContainer(bean);
		if (re > 0)
			b = true;
		return b;
	}
	
	@Transactional // 성공하면 커밋 실패하면 롤백
	public boolean update(FormBean bean) {
		boolean b = false;
		int re = containerMapper.update(bean);
		if (re > 0)
			b = true;
		return b;
	}


	@Transactional // 성공하면 커밋 실패하면 롤백
	public boolean delete(String cont_no) {
		boolean b = false;
		int re = containerMapper.delete(cont_no);
		if (re > 0)
			b = true;
		return b;
	}
}
