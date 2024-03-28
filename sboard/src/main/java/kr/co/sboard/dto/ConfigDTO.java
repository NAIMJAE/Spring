package kr.co.sboard.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfigDTO {
    private String cate;
    private String boardName;
    private String admin;
    private int total;
    private LocalDateTime createDate;
}
