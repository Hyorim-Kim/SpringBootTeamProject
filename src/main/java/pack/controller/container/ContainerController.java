package pack.controller.container;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import pack.model.container.ContainDao;
import pack.model.container.ContainerDto;

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

      return "container/container_list";
   }

   @GetMapping("/detail")
   public String conDetail(@RequestParam("cont_no") String cont_no, Model model) {
      ContainerDto conDto = containDao.conDetail(cont_no);
      model.addAttribute("conDto", conDto);

      return "container/container_detail";

   }

//   @GetMapping("insert")
//   // @RequestMapping(value="insert", method=RequestMethod.GET)
//   // 창고관리(목록)페이지에서 창고등록 페이지로 넘어가는 링크 매핑
//   // 창고관리 페이지에서 a th:href="@{/insert}" 요거 타고 들어옴
//   public String insertContainer() {
//      return "container/container_register";
//   }
//
//   @PostMapping("insert")
//   // @RequestMapping(value = "insert", method = RequestMethod.POST)
//   // 인서트 페이지에서 폼-액션태그의 insert인가? post 방식으로 값 전달 받고..?
//   public String insertSubmit(FormBean bean) {
//      // 폼-액션태그에서 입력된 값을 bean에 밀어넣어주고
//      boolean b = containDao.insertContainer(bean);
//      // dao에서 bean값을 처리해 boolean b값을 받아서
//      // 아래 if문을 통해 b가 true일때(bean에 값이 제대로 들어갔을때)
//      // insert 쿼리문을 수행하고 manage 페이지로 돌아감
//      // b가 false라면(bean에 값이 제대로 안들어갔을때)
//      // insert 쿼리문을 수행하지 않고 error 페이지로 이동
//      if (b) {
//         return "redirect:/owner/list";
//         // 추가 후 목록보기
//         // forwarding 하게 되면 서버에서 서버를 그냥 불러버려서 select를 만날 수 가 없대
//      } else {
//         return "error"; // 이거슨 포워딩
//      }
//   }
   
   
   @GetMapping("/insert")
   public String insertContainer(Model model) {
      model.addAttribute("formBean", new FormBean());
      return "container/container_register";
   }

   @PostMapping("/insert")
   public String insertContainer(@ModelAttribute("bean") FormBean bean, BindingResult result) {
       MultipartFile file = bean.getFile();
       
       if (file != null && !file.isEmpty()) { // 파일이 null이 아니고 비어있지 않을 때
           try {
               // 파일을 저장할 경로 설정
               String fileName = file.getOriginalFilename();
               String filePath = "C:/Users/cmh17/git/Team/src/main/resources/static/upload/" + fileName;
               File newFile = new File(filePath);
               
               // 디렉토리를 만들 때 getParentFile().mkdirs()를 사용하여 하위 디렉토리도 생성합니다.
               if (!newFile.getParentFile().exists()) {
                   newFile.getParentFile().mkdirs();
               }
               
               // 파일을 서버에 저장
               try (OutputStream outputStream = new FileOutputStream(newFile)) {
                   outputStream.write(file.getBytes());
               }

               // 데이터베이스에 저장할 파일 경로를 웹 경로로 설정합니다.
               bean.setCont_image("/upload/" + fileName);
               containDao.insertContainer(bean);
           } catch (IOException e) {
               e.printStackTrace();
               // 오류 처리
           }
       }
       
       return "redirect:/owner/list";
   }

   
//   
//    @GetMapping("/insert")
//       public String insertContainer(Model model) {
//           model.addAttribute("bean", new FormBean());
//           return "container/container_register";
//       }
//
//       @PostMapping("/insert")
//       public String submitForm(@ModelAttribute FormBean bean, @RequestParam("cont_image") MultipartFile file) throws IOException {
//           // 이미지 파일 업로드 및 파일명 저장
//           String imageFileName = containDao.uploadImage(file);
//           bean.setCont_image(imageFileName);
//
//           // 사용자 정보 저장
//           containDao.insertContainer(bean);
//
//           return "redirect:/owner/list";
//       }

   @RequestMapping("update")
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