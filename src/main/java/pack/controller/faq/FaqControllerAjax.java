package pack.controller.faq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // Spring MVC에서 컨트롤러 클래스를 나타내는 어노테이션으로, 이 클래스가 웹 요청을 처리하는 컨트롤러임을 나타낸다
public class FaqControllerAjax {
	@Autowired // 의존성 주입(Dependency Injection)을 위한 어노테이션으로, Spring 컨테이너에서 관리하는 빈(Bean)을 주입받을 때 사용
	private ModelAjax modelAjax; // FaqControllerAjax 클래스 내에 ModelAjax 타입의 model 필드가 선언, 이 필드는 Spring의 의존성 주입을 통해 자동으로 초기화
	
	@GetMapping("detailfaq") // HTTP GET 요청을 처리하는 핸들러 메서드를 지정하는 어노테이션, "/detailfaq" 경로로의 GET 요청을 처리할 메서드를 정의할 때 사용
	@ResponseBody // 컨트롤러 메서드가 반환하는 객체를 HTTP 응답 본문으로 직접 반환하도록 지정하는 어노테이션으로, 주로 RESTful API에서 JSON 또는 XML과 같은 데이터를 반환할 때 사용
	public ModelAjax getFqa(@RequestParam("detail")String detail) { // HTTP 요청 파라미터를 메서드 매개변수로 전달받을 때 사용되는 어노테이션으로, "detail" 파라미터의 값을 추출하여 메서드에 전달
		modelAjax.setDetail(detail);
		return modelAjax;
	}
}

// 이 코드는 "/detailfaq" 경로로 들어오는 GET 요청을 처리하며, 요청 파라미터 "detail"을 받아서 이를 ModelAjax 객체에 설정하고, 
// 이 객체를 JSON 또는 XML 형식으로 응답으로 보내는 역할을 수행하는 Spring 웹 컨트롤러를 정의하고 있다.





