package kr.co.ch07.dto;

import kr.co.ch07.entity.User3;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User3DTO {
    private String uid;
    private String name;
    private String birth;
    private String hp;
    private String addr;

    // Entity 변환 메서드
    public User3 toEntity(){
        return User3.builder()
                .uid(uid).name(name).birth(birth).hp(hp).addr(addr).build();
    }
}
