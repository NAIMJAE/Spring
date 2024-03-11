package kr.co.ch06.DTO;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User2DTO {
    private String uid;
    private String name;
    private String birth;
    private String addr;
}
