package example.board.controller;

import example.board.dto.BoardDto;
import example.board.dto.BoardFileDto;
import example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @RequestMapping("/board/openBoardList")
    public ModelAndView boardList() throws Exception{
        ModelAndView mv = new ModelAndView("board/boardList");//호출되는 html 화면
        List<BoardDto> list = boardService.selectBoardList();
        mv.addObject("list", list);
        return mv;
    }

    @RequestMapping("/board/openBoardWrite")
    public String boardWrite() throws Exception{
        return "/board/boardWrite";
    }

    @RequestMapping("/board/insertBoard")
    public String insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
        boardService.insertBoard(board, multipartHttpServletRequest);
        return "redirect:/board/openBoardList";
    }

    @RequestMapping("/board/openBoardDetail")
    public ModelAndView boardDetail(@RequestParam int boardIdx) throws Exception{
        ModelAndView mv =new ModelAndView("board/boardDetail");

        BoardDto board = boardService.selectBoardDetail(boardIdx);
        mv.addObject("board", board);
        return mv;
    }

    @RequestMapping("/board/updateBoard")
    public String boardUpdate(BoardDto board) throws Exception{
        boardService.updateBoard(board);
        return "redirect:/board/openBoardList";
    }

    @RequestMapping("/board/deleteBoard")
    public String boardDelete(int boardIdx) throws Exception{
        boardService.deleteBoard(boardIdx);
        return "redirect:/board/openBoardList";
    }

    @RequestMapping("/board/downloadBoardFile")
    public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception{
        BoardFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
        if(ObjectUtils.isEmpty(boardFile) == false){
            String fileName = boardFile.getOriginalFileName();

            byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));

            response.setContentType("application/octet-stream");
            response.setContentLength(files.length);
            response.setHeader("Content-Disposition", "attachment; fileName=\""+ URLEncoder.encode(fileName, "UTF-8")+"\";");
            response.setHeader("Content-Transfer-Encoding", "binary");

            response.getOutputStream().write(files);
            response.getOutputStream().flush();
            response.getOutputStream().close();

        }
    }

}
