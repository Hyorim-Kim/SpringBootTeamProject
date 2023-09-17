package pack.model.container;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
//재민
@Mapper
public interface kakaoMapperInterface {
	@Select("select * from contain_1")
	List<ContainDtoMap> MapAll();
}
