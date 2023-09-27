package pack.controller.faq;

import org.springframework.stereotype.Component;

@Component
public class ModelAjax { // ModelAjax라는 클래스를 선언
	private String detail; // detail이라는 private 멤버 변수를 선언
	
	public String getDetail() { // getDetail 메서드는 detail 멤버 변수의 값을 반환하는 게터(Getter) 메서드, 이 메서드를 통해 detail 값을 읽을 수 있다
		return detail;
	}

	public void setDetail(String detail) { // setDetail 메서드는 detail 멤버 변수에 값을 설정하는 세터(Setter) 메서드, 이 메서드를 통해 detail 값을 변경할 수 있다
		this.detail = detail;
	}

}
