package example.board.repository;

import example.board.entity.JpaBoard;
import example.board.entity.JpaBoardFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaBoardRepository extends CrudRepository<JpaBoard, Integer> {

    List<JpaBoard> findAllByOrderByBoardIdxDesc();

    @Query("SELECT file FROM JpaBoardFile file WHERE board_idx = :boardIdx AND idx = :idx")
    JpaBoardFile findBoardFile(@Param("boardIdx") int boardIdx, @Param("idx") int idx);
}
