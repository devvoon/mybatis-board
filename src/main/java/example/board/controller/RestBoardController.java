package example.board.controller;

import example.board.dto.BoardDto;
import example.board.dto.BoardFileDto;
import example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RestBoardController {

    private final BoardService boardService;

    @RequestMapping(value="/board", method = RequestMethod.GET)
    public ModelAndView openBoardList() throws Exception{
        ModelAndView mv = new ModelAndView("board/restBoardList");
        List<BoardDto> list=  boardService.selectBoardList();
        System.out.println(">>>>>>>>>>>>>>>>>>>>list:" + list.size());
        mv.addObject("list", list);
        return mv;
    }

    @RequestMapping(value="/board/write", method=RequestMethod.GET)
    public String openBoardWrite() throws Exception{
        return "/board/restBoardWrite";
    }

    @RequestMapping(value="/board/write", method=RequestMethod.POST)
    public String insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpRequest) throws Exception{
        boardService.insertBoard(board, multipartHttpRequest);
        return "redirect:/board";
    }

    @RequestMapping(value="/board/{boardIdx}", method=RequestMethod.GET)
    public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception{
        ModelAndView mv = new ModelAndView("board/restBoardDetail");

        BoardDto board = boardService.selectBoardDetail(boardIdx);
        mv.addObject("board", board);

        return mv;
    }

    @RequestMapping(value="/board/{boardIdx}", method=RequestMethod.PUT)
    public String updateBoard(BoardDto board) throws Exception{
        boardService.updateBoard(board);
        return "redirect:/board";
    }

    @RequestMapping(value="/board/{boardIdx}", method=RequestMethod.DELETE)
    public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
        boardService.deleteBoard(boardIdx);
        return " redirect:/board";
    }

    @RequestMapping(value="/board/file", method = RequestMethod.GET)
    public void downloadBoardFileRes(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception{
        BoardFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
        if(false == ObjectUtils.isEmpty(boardFile)){
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
