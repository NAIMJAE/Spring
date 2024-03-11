package kr.co.ch06.Mapper;

import kr.co.ch06.DTO.User3DTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class User3MapperTest {
    @Autowired
    private User3Mapper mapper;

    @DisplayName("User3 등록")
    void insertUser3() {
        log.info("Test - insertUser3()..");
        // given
        User3DTO user3DTO = User3DTO.builder()
                .uid("B101").name("손오공").birth("0000-00-00").hp("010-0000-0000").addr("서울 강남").build();
        // when
        mapper.insertUser3(user3DTO);
        // then
        User3DTO resultUser3 = mapper.selectUser3(user3DTO.getUid());
        assertEquals(user3DTO.getUid(), resultUser3.getUid());
    }

    @DisplayName("User3 조회")
    void selectUser3() {
        log.info("Test - selectUser3()..");
        // given
        String uid = "B101";
        // when
        User3DTO user3DTO = mapper.selectUser3(uid);
        // then
        assertEquals(uid, user3DTO.getUid());
    }

    @DisplayName("User3 목록")
    void selectUser3s() {
        log.info("Test - selectUser3s()..");
        // given
        List<User3DTO> users = null;
        // when
        users = mapper.selectUser3s();
        for(User3DTO user : users){
            log.info(user.toString());
        }
        // then
        assertFalse(users.isEmpty());
    }

    @DisplayName("User3 수정")
    void updateUser3() {
        log.info("Test - updateUser3()..");
        // given
        User3DTO user3DTO = User3DTO.builder()
                .uid("B101").name("손오공").birth("0000-00-00").hp("010-0000-0000").addr("부산 진구").build();
        // when
        mapper.updateUser3(user3DTO);
        // then
        User3DTO resultUser = mapper.selectUser3(user3DTO.getUid());
        assertEquals(user3DTO.getAddr(), resultUser.getAddr());
    }

    @DisplayName("User3 삭제")
    void deleteUser3() {
        log.info("Test - deleteUser3()..");
        // given
        String uid = "B101";
        // when
        mapper.deleteUser3(uid);
        // then
        User3DTO user3DTO = mapper.selectUser3(uid);
        assertNull(user3DTO);
    }
}