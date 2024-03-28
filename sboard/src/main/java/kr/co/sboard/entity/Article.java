package kr.co.sboard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;
    private int parent;
    private int comment;
    private String cate;
    private String title;
    private String content;
    private int file;
    private int hit;
    private String writer;

    private String regip;

    @CreationTimestamp
    private LocalDateTime rdate;

    @OneToMany(mappedBy = "ano")
    private List<File> fileList;

    private String nick;
}
