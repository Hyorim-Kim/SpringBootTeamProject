package pack.model.admin;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import pack.controller.container.FormBean;

@Mapper
public interface ApproveMapperInter {
	
	@Update("update container set cont_status='1' where cont_no=#{cont_no}")
	int approve(AdminBean adminbean);
	
	@Update("update container set cont_status='4' where cont_no=#{cont_no}")
	int deny(AdminBean adminbean);
}
