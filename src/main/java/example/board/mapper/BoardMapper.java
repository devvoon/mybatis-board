package example.board.mapper;

import example.board.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardDto> selectBoardList() throws Exception;
}
