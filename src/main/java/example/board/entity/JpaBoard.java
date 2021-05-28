package example.board.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="jpa_board")
@NoArgsConstructor
@Data
public class JpaBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int boardIdx;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private int hitCount = 0;

    @Column(nullable = false)
    private String creatorId;

    @Column(nullable = false)
    private LocalDateTime createdDateTime  = LocalDateTime.now();

    private String updaterId;
    private String updatedDateTime;

    private String deletedYn;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_idx")
    private List<JpaBoardFile> fileList;
}
