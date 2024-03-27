package kr.co.sboard.dto;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDTO {
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
    private LocalDateTime rdate;
    // 파일 수신 (파일 여러개 수신이라 list)
    private List<MultipartFile> files;

    private UserDTO user;
    private List<FileDTO> fileList;
    //@Transient
    private String nick;
}
