package kr.co.sboard.entity;

import jakarta.persistence.*;
import kr.co.sboard.dto.UserDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="user")
public class User {
    @Id
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
    @Lob
    private String profile;
    private String regip;
    private String sms;

    @CreationTimestamp
    private LocalDateTime rdate;
    private LocalDateTime leaveDate;


}
