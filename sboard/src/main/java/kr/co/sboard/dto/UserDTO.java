package kr.co.sboard.dto;

import kr.co.sboard.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDTO {
    private String uid;
    private String pass;
    private String name;
    private String nick;
    private String email;
    private String hp;
    private String role;
    private String zip;
    private String addr1;
    private String addr2;
    private String profile;
    private String regip;
    private String sms;
    private LocalDateTime rdate;
    private LocalDateTime leaveDate;
}
