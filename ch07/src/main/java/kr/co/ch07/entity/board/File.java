package kr.co.ch07.entity.board;

import jakarta.persistence.*;
import kr.co.ch07.entity.board.Article;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "article")
@Builder
@Entity
@Table(name="board_file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fno;
    private String sName;
    private String oName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ano")
    private Article article;
}
