package pack.controller.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.model.board.BoardDaoImpl;
import pack.model.board.BoardDto;


@Controller
@RequestMapping("/board")
public class UpdateController {
    @Autowired
    private BoardDaoImpl daoImpl;
    
    @GetMapping("update")
    public String edit(@RequestParam("num") String num,
                       @RequestParam("page") String page,
                       Model model) {
        // 수정 대상 자료 읽기
        BoardDto dto = daoImpl.detail(num);
        model.addAttribute("data", dto);
        model.addAttribute("page", page);
        return "board/update";
    }
    
    @PostMapping("update")
    public String editProcess(BoardBean bean, 
            @RequestParam("page") String page, 
            Model model) {
        // 수정을 위한 코드
        
        boolean b = daoImpl.update(bean);
        if (b) {
            // 수정 성공 시 상세보기로 이동
            return "redirect:detailAdmin?num=" + bean.getNum() + "&page=" + page;
            
        } else {
            // 수정 실패 시
            return "redirect:error";
        }
    }
}