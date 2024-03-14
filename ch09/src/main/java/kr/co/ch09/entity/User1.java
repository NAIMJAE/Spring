package kr.co.ch09.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.ch09.dto.User1DTO;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "user1")
public class User1 {
    @Id
    private String uid;
    private String name;
    private String birth;
    private String hp;
    private int age;
    // DTO 변환 메서드
    public User1DTO toDTO(){
        return User1DTO.builder()
                .uid(uid).name(name).birth(birth).hp(hp).age(age).build();
    }
}
