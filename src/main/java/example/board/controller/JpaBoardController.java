package example.board.controller;

import example.board.dto.BoardDto;
import example.board.entity.JpaBoard;
import example.board.entity.JpaBoardFile;
import example.board.service.JpaBoardService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class JpaBoardController {

    private final JpaBoardService jpaBoardService;

    @RequestMapping(value="/jpa/board", method = RequestMethod.GET)
    public ModelAndView openBoardList(ModelMap model) throws Exception{
        ModelAndView mv = new ModelAndView("board/jpaBoardList");//호출되는 html 화면
        List<JpaBoard> list = jpaBoardService.selectBoardList();
        mv.addObject("list", list);
        return mv;
    }

    @RequestMapping(value="/jpa/board/write", method=RequestMethod.GET)
    public String openBoardWrite() throws Exception{
        return "board/jpaBoardWrite";
    }

    @RequestMapping(value = "/jpa/board/write", method = RequestMethod.POST)
    public String writeBoard(JpaBoard board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
        jpaBoardService.saveBoard(board, multipartHttpServletRequest);
        return "redirect:/jpa/board";
    }

    @RequestMapping(value = "/jpa/board/{boardIdx}", method = RequestMethod.GET)
    public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception{
        ModelAndView mv = new ModelAndView("board/jpaBoardDetail");

        JpaBoard board = jpaBoardService.selectBoardDetail(boardIdx);
        mv.addObject("board", board);
        return mv;
    }

    @RequestMapping(value="/jpa/board/{boardIdx}", method=RequestMethod.POST)
    public String updateBoard(JpaBoard board) throws Exception{
        jpaBoardService.saveBoard(board, null);
        return "redirect:/jpa/board";
    }

    @RequestMapping(value = "/jpa/board/{boardIdx}/delete", method = RequestMethod.POST)
    public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
        jpaBoardService.deleteBoard(boardIdx);
        return "redirect:/jpa/board";
    }

    @RequestMapping(value="/jpa/board/file", method = RequestMethod.GET)
    public void downloadBoardFileRes(int boardIdx, int idx, HttpServletResponse response) throws Exception{
        JpaBoardFile boardFile = jpaBoardService.selectBoardFileInformation(boardIdx, idx);
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
