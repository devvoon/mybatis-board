package example.board.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class BoardDto {
    private int boardIdx;
    private String title;
    private String contents;
    private int hitCount;
    private String creatorId;
    private String createdDateTime;
    private String updaterId;
    private String updatedDateTime;
    private String deletedYn;
}
