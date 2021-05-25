package example.board.mapper;

import example.board.dto.BoardDto;
import example.board.dto.BoardFileDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardDto> selectBoardList() throws Exception;

    void insertBoard(BoardDto boardDto) throws Exception;

    void updateHitCount(int boardIdx);

    BoardDto selectBoardDetail(int boardIdx);

    void updateBoard(BoardDto board);

    void deleteBoard(int boardIdx);

    void inertBoardFileList(List<BoardFileDto> list);

    List<BoardFileDto> selectBoardFileList(int boardIdx);

    BoardFileDto selectBoardFileInformation(@Param("idx") int idx, @Param("boardIdx") int boardIdx);
}
