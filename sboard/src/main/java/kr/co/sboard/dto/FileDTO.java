package kr.co.sboard.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDTO {
    private int fno;
    private int ano;
    private String oName;
    private String sName;
    private int download;
    private LocalDateTime rdate;
}
