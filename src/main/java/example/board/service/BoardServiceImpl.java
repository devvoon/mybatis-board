package example.board.service;

import example.board.dto.BoardDto;
import example.board.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public List<BoardDto> selectBoardList() throws Exception {
        return boardMapper.selectBoardList();
    }

    @Override
    public void insertBoard(BoardDto boardDto) throws Exception {
        boardMapper.insertBoard(boardDto);
    }

    @Override
    public BoardDto selectBoardDetail(int boardIdx) throws Exception {
        boardMapper.updateHitCount(boardIdx);
        BoardDto board = boardMapper.selectBoardDetail(boardIdx);
        return board;
    }

    @Override
    public void updateBoard(BoardDto board) {
        boardMapper.updateBoard(board);
    }

    @Override
    public void deleteBoard(int boardIdx) {
        boardMapper.deleteBoard(boardIdx);
    }
}
