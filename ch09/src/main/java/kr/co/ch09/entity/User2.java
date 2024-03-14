package kr.co.ch09.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.ch09.dto.User1DTO;
import kr.co.ch09.dto.User2DTO;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "user2")
public class User2 {
    @Id
    private String uid;
    private String name;
    private String birth;
    private String email;
    private int age;
    private String addr;

    // DTO 변환 메서드
    public User2DTO toDTO(){
        return User2DTO.builder()
                .uid(uid).name(name).birth(birth).email(email).age(age).addr(addr).build();
    }
}
