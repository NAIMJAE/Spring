package kr.co.ch06.Mapper;

import kr.co.ch06.DTO.User1DTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
/*
    @Slf4j(lombok.extern.slf4j) import가 안되면	build.gradle 아래 내용 추가
     - testCompileOnly 'org.projectlombok:lombok'
	 - testAnnotationProcessor 'org.projectlombok:lombok'
*/

// Test 에서 성공하면 Mapper / MyBatis / xml 설정 문제 없는 것
@Slf4j
@SpringBootTest // 어노테이션 생략가능
class User1MapperTest {

    @Autowired
    private User1Mapper mapper;

    @DisplayName("user1 등록")
    void insertUser1() {
        log.info("insertUser1()...");

        // 테스트 코드 패턴 : given - when - then

        // given : 테스트를 실행할 준비 단계
        User1DTO user1DTO = User1DTO.builder()
                                    .uid("D102")
                                    .name("홍길동")
                                    .birth("1001-50-45")
                                    .hp("010-1342-4566")
                                    .age(22)
                                    .build();

        // when : 테스트를 진행하는 단계
        mapper.insertUser1(user1DTO);

        // then : 테스트 결과를 검증하는 단계 (Assertion 단정문 이용)
        // (insert한 쿼리문을 다시 select해서 일치하는지 비교)
        User1DTO resultUser1 = mapper.selectUser1(user1DTO.getUid());
        Assertions.assertEquals(user1DTO.getUid(), resultUser1.getUid());
    }

    @DisplayName("user1 조회")
    void selectUser1() {
        log.info("selectUser1()...");
        // given
        String uid = "D101";

        // when
        User1DTO user1DTO = mapper.selectUser1(uid);
        log.info(user1DTO.toString());

        // then
        assertEquals(uid, user1DTO.getUid());
    }

    @DisplayName("user1 목록")
    void selectUser1s() {
        log.info("selectUser1s()...");

        // given
        List<User1DTO> users = null;
        // when
        users = mapper.selectUser1s();
        for(User1DTO user:users){
            log.info(user.toString());
        }
        // then
        assertFalse(users.isEmpty());
    }

    @DisplayName("user1 수정")
    void updateUser1() {
        log.info("updateUser1()...");
        // given
        User1DTO user1DTO = User1DTO.builder()
                                    .uid("D102")
                                    .name("홍길동")
                                    .birth("1001-00-00")
                                    .hp("010-0000-0000")
                                    .age(52)
                                    .build();
        // when
        mapper.updateUser1(user1DTO);
        // then
        User1DTO resultUser = mapper.selectUser1(user1DTO.getUid());
        assertEquals(user1DTO.getUid(), resultUser.getUid());
    }
    @Test
    @DisplayName("user1 삭제")
    void deleteUser1() {
        log.info("deleteUser1()...");
        // given
        String uid = "D102";
        // when
        mapper.deleteUser1(uid);
        // then
        User1DTO resultUser = mapper.selectUser1(uid);
        assertNull(resultUser);
    }
}