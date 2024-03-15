package kr.co.ch10.dto;

import kr.co.ch10.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String uid;
    private String pass;
    private String name;
    private int age;
    private String hp;
    private String role;
    private LocalDateTime regDate;
    // Entity 변환 메서드
    public User toEntity(){
        return User.builder()
                .uid(uid).pass(pass).name(name).age(age)
                .hp(hp).role(role).regDate(regDate).build();
    }
}