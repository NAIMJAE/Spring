package kr.co.ch06.DTO;

import lombok.*;

@Getter // getter
@Setter // setter
@ToString // toString
@AllArgsConstructor // 모든 속성을 가지는 생성자
@NoArgsConstructor // 기본 생성자
@Builder //
public class UserDTO {
    private String uid;
    private String name;
    private int age;
    private String addr;

}
