package pack.controller.faq;

import org.springframework.stereotype.Component;

@Component
public class ModelAjax {
	private String detail;
	
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
