package kr.co.ch07.entity.board;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name="board_article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private int no;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)  // 연관 Entity가 로드 될때 필요 시점에 로딩되는 지연전략
    @JoinColumn(name = "writer")        // Entity가 Table로 생성될때 컬럼명, 해당 Entity의 @Id 컬럼타입으로 생성
    private User user;                  // 외래키로 참조되기때문에 타입 맞춰주기

    @OneToOne(mappedBy = "article")     // 양방향 연관관계에서 외래키를 갖는 Entity의 속성을 mappedBy 속성으로 연결 주인 설정
    private File file;

    @OneToMany(mappedBy = "article")
    private List<Comment> comment;      // 1:N 관계라서 List<>

    @CreationTimestamp
    private LocalDateTime rdate;        // 테이블로 바로 생성되기 때문에 (MySQL = datetime)
}
