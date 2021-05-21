package example.board.controller;

import example.board.dto.BoardDto;
import example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @RequestMapping("/board/boardList")
    public ModelAndView boardList() throws Exception{
        ModelAndView mv = new ModelAndView("board/boardList");//호출되는 html 화면
        List<BoardDto> list = boardService.selectBoardList();
        mv.addObject("list", list);
        return mv;
    }
}
