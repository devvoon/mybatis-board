package example.board.service;

import example.board.entity.JpaBoard;
import example.board.entity.JpaBoardFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface JpaBoardService {
    List<JpaBoard> selectBoardList() throws Exception;
    void saveBoard(JpaBoard boardDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;
    JpaBoard selectBoardDetail(int boardIdx) throws Exception;
    void deleteBoard(int boardIdx);
    JpaBoardFile selectBoardFileInformation(int boardIdx, int idx) throws Exception;
}
