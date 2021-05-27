package example.board.controller;

import example.board.dto.BoardDto;
import example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestBoardApiController {

    private final BoardService boardService;

    @RequestMapping(value="/api/board", method = RequestMethod.GET)
    public List<BoardDto> openBoardList() throws Exception{
        return boardService.selectBoardList();
    }

    @RequestMapping(value = "/api/board/write", method = RequestMethod.POST)
    public void insertBoard(@RequestBody BoardDto board) throws Exception{
        boardService.insertBoard(board, null);
    }

    @RequestMapping(value = "/api/board/{boardIdx}", method = RequestMethod.GET)
    public BoardDto openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception{
        return boardService.selectBoardDetail(boardIdx);
    }

    @RequestMapping(value = "/api/board/{boardIdx}", method = RequestMethod.PUT)
    public String updateBoard(@RequestBody BoardDto board) throws Exception{
        boardService.updateBoard(board);
        return "redirect:/board";
    }

    @RequestMapping(value = "/api/board/{boardIdx}", method = RequestMethod.DELETE)
    public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
        boardService.deleteBoard(boardIdx);
        return "redirect:/board";
    }

}
