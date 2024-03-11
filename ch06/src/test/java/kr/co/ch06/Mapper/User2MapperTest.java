package kr.co.ch06.Mapper;

import kr.co.ch06.DTO.User2DTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class User2MapperTest {
    @Autowired
    private User2Mapper mapper;


    @DisplayName("User2 등록")
    void insertUser2() {
        log.info("insertUser2()...");
        // given
        User2DTO user2DTO = User2DTO.builder()
                .uid("F102").name("손").birth("0000-00-00").addr("서울").build();
        // when
        mapper.insertUser2(user2DTO);
        // then
        User2DTO resultUser2 = mapper.selectUser2(user2DTO.getUid());
        assertEquals(user2DTO.getUid(), resultUser2.getUid());
    }

    @DisplayName("User2 조회")
    void selectUser2() {
        log.info("selectUser2()...");
        // given
        String uid = "F102";
        // when
        User2DTO user2DTO = mapper.selectUser2(uid);
        log.info(user2DTO.toString());
        // then
        assertEquals(uid, user2DTO.getUid());
    }

    @DisplayName("User2 목록")
    void selectUser2s() {
        log.info("selectUser2s()...");
        // given
        List<User2DTO> users = null;
        // when
        users = mapper.selectUser2s();
        for(User2DTO user : users){
            log.info(user.toString());
        }
        // then
        assertFalse(users.isEmpty());
    }

    @DisplayName("User2 수정")
    void updateUser2() {
        log.info("updateUser2()...");
        // given
        User2DTO user2DTO = User2DTO.builder()
                .uid("F102").name("손").birth("0000-00-00").addr("부산").build();
        // when
        mapper.updateUser2(user2DTO);
        // then
        User2DTO resultUser = mapper.selectUser2(user2DTO.getUid());
        assertEquals(user2DTO.getAddr(), resultUser.getAddr());
    }

    @DisplayName("User2 삭제")
    void deleteUser2() {
        log.info("deleteUser2()...");
        // given
        String uid = "F102";
        // when
        mapper.deleteUser2(uid);
        // then
        User2DTO user2DTO = mapper.selectUser2(uid);
        assertNull(user2DTO);
    }
}