package pack.model.container;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
}
