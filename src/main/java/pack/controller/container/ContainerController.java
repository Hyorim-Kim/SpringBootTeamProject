package pack.controller.container;

import java.io.File;
import java.io.FileOutputStream;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import pack.model.booking.bookingDTO;
import pack.model.container.ContainDao;
import pack.model.container.ContainerDto;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper; // Jackson 라이브러리 추가

@Controller
@RequestMapping(value = "owner")
public class ContainerController {

// ******* 민혁 ************

	@Autowired
	private ContainDao containDao;

	@GetMapping("/ownerMain")
	public String main() {
		return "container/ownerMain";
	}

	@GetMapping("/paid")
	public String cont_pay() {
		return "container/container_paid";
	}

	@GetMapping("/register")
	public String cont_regs() {
		return "container/container_register";
	}

	@GetMapping("/list")
	public String cont_mgr(Model model, HttpSession session) {
		// 창고관리 페이지로 매핑해주는 메소드, 리스트값을 달고 가서 반복문을 통해 테이블에 값들을 밀어넣어줌
		String business_num = (String) session.getAttribute("business_num");
		// 로그인한 사업자가 등록한 창고정보만 출력될 수 있도록 세션을 통해 사업자번호를 받아옴
		ArrayList<ContainerDto> clist = (ArrayList<ContainerDto>) containDao.getDataAll(business_num);
		// containerdto에 선언된 멤버들로 이루어진 리스트생성
		// dto에 선언된 멤버들에 값을 주기 위해 dao로부터 값을 요청함 이때 business_num을 인자로 달고 요청하게 됨
		// dao에서 받아온 값들은 arraylist 타입으로 형변환해준 후 clist에 담아줌
		
		// List는 메모리가 허용하는 한 계속해서 추가 할 수있도록 만든 자료형 인터페이스
		// ArrayList는 배열처럼 고정된 크기를 가지는 것이 아닌 메모리가 허용하는 한 자동으로 크기가 동적으로 변경되는 클래스
		// 다형성 개념 : List는 도형 인터페이스, ArrayList는 정사각형과같은 도형 인터페이스를 구현한 클래스라고 예로 들 수 있음
		// generic 개념 : 클래스 내부에서 지정하지 않고 외부 사용자에의해 타입 이 정해짐 > 미리 특정 타입을 정해주는 것이 아니라 필요에 의해 지정하도록 함
		// 이러한 형변환을 통해 내부 디테일, 메모리 함축에서의 이점과 성능 개선 가능
		// 그래서 arraylist로 형변환

		model.addAttribute("datas", clist);
		// datas라는 키값에 clist담아 키값을 통해 clist에 담긴 데이터를 불러올수 있게 해줌
		return "container/container_list";
		// 창고목록 페이지로 리턴해줌.
	}
	
	@GetMapping("/reserve")
	public String cont_reserve(Model model, HttpSession session) {
		// 창고관리 페이지로 매핑해주는 메소드, 리스트값을 달고 가서 반복문을 통해 테이블에 값들을 밀어넣어줌
		String business_num = (String) session.getAttribute("business_num");
		// 로그인한 사업자가 등록한 창고정보만 출력될 수 있도록 세션을 통해 사업자번호를 받아옴
		ArrayList<ContainerDto> rlist = (ArrayList<ContainerDto>) containDao.getDataReserve(business_num);
		model.addAttribute("datas", rlist);
		return "container/container_reserve";
	}

	// ---------------------------
	private double[] getCoordinatesFromAddress(String address) {
		double[] coordinates = new double[2]; // 배열 초기화

		try {
			// Google Geocoding API를 호출하여 주소를 위도와 경도로 변환
			String apiKey = "AIzaSyDzGKmDfbyNTWo-0WqNSdQlQSlxc6Wjna4";
			String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + apiKey;

			URL url = new URL(apiUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();

			// JSON 응답에서 위도와 경도 추출
			Map<String, Object> data = new ObjectMapper().readValue(response.toString(), Map.class);
			if (data.containsKey("results")) {
				Map<String, Object> result = ((List<Map<String, Object>>) data.get("results")).get(0);
				if (result.containsKey("geometry")) {
					Map<String, Object> geometry = (Map<String, Object>) result.get("geometry");
					if (geometry.containsKey("location")) {
						Map<String, Double> location = (Map<String, Double>) geometry.get("location");
						coordinates[0] = location.get("lat");
						coordinates[1] = location.get("lng");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return coordinates; // 배열 반환
	}
	// ---------------------------
	private boolean isAllowedExtension(String filename) {
        String[] allowedExtensions = {".jpg", ".jpeg", ".png"};
        for (String extension : allowedExtensions) {
            if (filename.toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
	
	@PostMapping("insert")
	   // @RequestMapping(value = "insert", method = RequestMethod.POST)
	   // 인서트 페이지에서 폼-액션태그의 insert인가? post 방식으로 값 전달 받고..?
	   public String insertSubmit(FormBean bean, UploadFile uploadFile, BindingResult result, HttpSession session) {

	      String business_num = (String) session.getAttribute("business_num");
	      // 창고등록 시 자동으로 사업자번호를 입력해주게 하기위해 세션으로 값 받아옴
	      
	      // stream개념 : 데이터가 출발지에서 도착지로 단일 방향으로 흘러가는 개념
	      // input/outputstream > 추상클래스 > 추상 메소드를 오버라이딩 해서 다양한 역할 수행
	      InputStream inputStream = null;
	      // 파일데이터, 네트워크 소켓을 통한 데이터, 키보드 입력 데이터 등을 읽을 때 사용
	      OutputStream outputStream = null;
	      // 입력 받은 데이터를 출력함
	      
	      // 난수 생성을 통해 중복된 파일을 업로드 했을때 충돌을 피할 수 있도록 해줌

	      // 업로드될 파일 검사
	      // 파일 이름이 비어 있지 않고, 점이 포함되어 있는 경우에만 확장자를 추출하도록 조건을 설정하면 오류를 방지 - 재민
	      MultipartFile file = uploadFile.getFile();
	      
	      String originalFilename = file.getOriginalFilename();
	      String randomFilename = UUID.randomUUID().toString();;
	      
	      if (!isAllowedExtension(originalFilename)) {
	          return "errorExtension"; // 확장자가 허용되지 않으면 오류 페이지로 리턴합니다.
	      }
	      
	      String fileExtension = "";
	      
	      if (originalFilename != null && originalFilename.contains(".")) {
	         fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
	      }

	      // 파일명에 랜덤 문자열을 추가하여 새 파일명 생성
	      if (originalFilename != null && !originalFilename.isEmpty()) {
	    	    // 파일명에 기존 파일명과 랜덤 값을 결합합니다.
	    	    randomFilename = originalFilename.substring(0, originalFilename.lastIndexOf('.')) + "_" + randomFilename + fileExtension;
	    	} else {
	    	    randomFilename += fileExtension;
	    	}

	      // 작성자가 업로드한 파일명 > 서버 내부에서 관리하는 파일명
	      if (result.hasErrors()) { // 에러가 있으면 트루야
	         return "errorFile"; // 에러발생 (파일을 선택하지 않은 경우)시 수행
	      }

	      try {
	         inputStream = file.getInputStream();


	         String fileSavePath = "C:/Users/kwang/git/Team/src/main/resources/static/upload/" + randomFilename;

	         File newFile = new File(fileSavePath);
	         if (!newFile.exists()) {
	            newFile.createNewFile();
	         }

	         outputStream = new FileOutputStream(newFile);
	         int read = 0;
	         byte[] bytes = new byte[1024];
	         while ((read = inputStream.read(bytes)) != -1) {
	            outputStream.write(bytes, 0, read);
	         }
	         bean.setCont_image(randomFilename);
	         
	      } catch (Exception e) {
	         System.out.println("file submit err : " + e);
	         return "err";
	      } finally {
	         try {
	            inputStream.close();
	            outputStream.close();
	         } catch (Exception e2) {
	            // TODO: handle exception
	         }
	      }

	      // 폼-액션태그에서 입력된 값을 bean에 밀어넣어주고
	      boolean b = containDao.insertContainer(bean);

	      // 주소를 위도와 경도로 변환하여 설정
	      String address = bean.getCont_addr();
	      double[] coordinates = getCoordinatesFromAddress(address);
	      bean.setCont_we(coordinates[0]);
	      bean.setCont_kyung(coordinates[1]);
	      // --------
	      // dao에서 bean값을 처리해 boolean b값을 받아서
	      // 아래 if문을 통해 b가 true일때(bean에 값이 제대로 들어갔을때)
	      // insert 쿼리문을 수행하고 manage 페이지로 돌아감
	      // b가 false라면(bean에 값이 제대로 안들어갔을때)
	      // insert 쿼리문을 수행하지 않고 error 페이지로 이동
	      if (b) {
	         return "redirect:/owner/list";
	         // 추가 후 목록보기
	         // forwarding 하게 되면 서버에서 서버를 그냥 불러버려서 select를 만날 수 가 없대
	      } else {
	         return "error"; // 이거슨 포워딩
	      }
	   }

	   
//	@GetMapping("/booked")
//	public String conBooked(@RequestParam("cont_no") String cont_no, Model model) {
//		bookingDTO bookDto = containDao.selectbookcont(cont_no);
//		System.out.println(cont_no);
//		System.out.println(bookDto);
//		model.addAttribute("bookDto", bookDto);
//		return "container/bookCont";
//	} 구현 실패


	@GetMapping("/detail")
	public String conDetail(@RequestParam("cont_no") String cont_no, Model model) {
		ContainerDto conDto = containDao.conDetail(cont_no);
		model.addAttribute("conDto", conDto);

		return "container/container_detail";
	}
	
	

	@GetMapping("/goUpdate")
	public String cont_update(@RequestParam("cont_no") String cont_no, Model model) {
		ContainerDto conDto = containDao.conDetail(cont_no);
		model.addAttribute("conDto", conDto);
		return "container/container_update";
	}

	@PostMapping("update")
	public String update(FormBean bean) {	
		boolean b = containDao.update(bean);
		if (b)
			return "redirect:/owner/list"; // 수정 후 목록보기
		else
			return "error";
	}
	

	@GetMapping("delete")
	public String delete(@RequestParam("cont_no") String cont_no) {
		boolean b = containDao.delete(cont_no);
		if (b)
			return "redirect:/owner/list"; // 수정 후 목록보기
		else
			return "error";
	}
}