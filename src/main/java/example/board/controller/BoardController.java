package example.board.controller;

import example.board.dto.BoardDto;
import example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/board/boardWrite")
    public String boardWrite() throws Exception{
        return "/board/boardWrite";
    }

    @RequestMapping("/board/insertBoard")
    public String insertBoard(BoardDto board) throws Exception{
        boardService.insertBoard(board);
        return "redirect:/board/boardList";
    }

    @RequestMapping("/board/boardDetail")
    public ModelAndView boardDetail(@RequestParam int boardIdx) throws Exception{
        ModelAndView mv =new ModelAndView("board/boardDetail");

        BoardDto board = boardService.selectBoardDetail(boardIdx);
        mv.addObject("board", board);
        return mv;
    }

    @RequestMapping("/board/boardUpdate")
    public String boardUpdate(BoardDto board) throws Exception{
        boardService.updateBoard(board);
        return "redirect:/board/boardList";
    }

    @RequestMapping("/board/boardDelete")
    public String boardDelete(int boardIdx) throws Exception{
        boardService.deleteBoard(boardIdx);
        return "redirect:/board/boardList";
    }


}
