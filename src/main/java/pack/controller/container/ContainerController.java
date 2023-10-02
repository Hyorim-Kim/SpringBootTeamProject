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

	@GetMapping("/reserve")
	public String cont_book() {
		return "container/container_reserve";
	}

	@GetMapping("/register")
	public String cont_regs() {
		return "container/container_register";
	}

	@GetMapping("/outstanding")
	public String cont_outstd() {
		return "container/container_outstading";
	}

	@GetMapping("/list")
	public String cont_mgr(Model model, HttpSession session) {
		String business_num = (String) session.getAttribute("business_num");
//       if (business_num == null) {
//           // 처리할 내용 추가
//       }

		// 창고관리 페이지로 매핑해주는 메소드 리스트값을 달고 가서 반복문을 통해 테이블에 값들을 밀어넣어줌
		ArrayList<ContainerDto> clist = (ArrayList<ContainerDto>) containDao.getDataAll(business_num);
		// System.out.println(clist);
		session.setAttribute("owner", clist);

		model.addAttribute("datas", clist);
		System.out.println("business_num  : " + business_num);
		return "container/container_list";
	}

   @GetMapping("insert")
   // @RequestMapping(value="insert", method=RequestMethod.GET)
   // 창고관리(목록)페이지에서 창고등록 페이지로 넘어가는 링크 매핑
   // 창고관리 페이지에서 a th:href="@{/insert}" 요거 타고 들어옴
   public String insertContainer(HttpSession session) {
      String owner = (String) session.getAttribute("owner");
      System.out.println(owner);
      return "container/container_register";
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

	@PostMapping("insert")
	   // @RequestMapping(value = "insert", method = RequestMethod.POST)
	   // 인서트 페이지에서 폼-액션태그의 insert인가? post 방식으로 값 전달 받고..?
	   public String insertSubmit(FormBean bean, UploadFile uploadFile, BindingResult result, HttpSession session) {

	      String business_num = (String) session.getAttribute("business_num");
	      // System.out.println(business_num);
	      
	      InputStream inputStream = null;
	      OutputStream outputStream = null;
	      UUID uuid = UUID.randomUUID();

	      // 업로드될 파일 검사
	      // 파일 이름이 비어 있지 않고, 점이 포함되어 있는 경우에만 확장자를 추출하도록 조건을 설정하면 오류를 방지 - 재민
	      MultipartFile file = uploadFile.getFile();

	      String originalFilename = file.getOriginalFilename();
	      String fileExtension = "";

	      if (originalFilename != null && originalFilename.contains(".")) {
	         fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
	      }

	      // 파일명에 랜덤 문자열을 추가하여 새 파일명 생성
	      String randomFilename = uuid.toString() + fileExtension;

	      // 작성자가 업로드한 파일명 > 서버 내부에서 관리하는 파일명
	      if (result.hasErrors()) { // 에러가 있으면 트루야
	         return "err"; // 에러발생 (파일을 선택하지 않은 경우)시 수행
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