package example.board.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class BoardFileDto {
    private int idx;
    private int boardIdx;
    private String originalFileName;
    private String storedFilePath;
    private long fileSize;
    private String creatorId;
    private String createdDateTime;
    private String updaterId;
    private String updatedDateTime;
    private String deletedYn;
}
