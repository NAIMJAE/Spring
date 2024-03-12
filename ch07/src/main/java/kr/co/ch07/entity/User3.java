package kr.co.ch07.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.ch07.dto.User3DTO;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "user3")
public class User3 {
    @Id
    private String uid;

    @Column(name = "name")
    private String name;

    @Column(name = "birth")
    private String birth;

    @Column(name = "hp")
    private String hp;

    @Column(name = "addr")
    private String addr;

    // DTO 변환 메서드
    public User3DTO toDTO(){
        return User3DTO.builder()
                .uid(uid).name(name).birth(birth).hp(hp).addr(addr).build();
    }
}
