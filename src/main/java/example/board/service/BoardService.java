package example.board.service;

import example.board.dto.BoardDto;

import java.util.List;

public interface BoardService {
    List<BoardDto> selectBoardList() throws Exception;
    void insertBoard(BoardDto boardDto) throws Exception;
    BoardDto selectBoardDetail(int boardIdx) throws Exception;
    void updateBoard(BoardDto board);
    void deleteBoard(int boardIdx);
}
