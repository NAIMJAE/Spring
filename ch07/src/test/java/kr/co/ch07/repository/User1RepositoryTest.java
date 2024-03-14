package kr.co.ch07.repository;

import kr.co.ch07.entity.User1;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class User1RepositoryTest {
    @Autowired
    private  User1Repository repository;

    @DisplayName("User1 등록")
    public void insertUser1(){
        log.info("insertUser1()...");
        // given
        User1 user1 = User1.builder()
                .uid("H101").name("손오공").birth("0000-00-00").hp("010-0000-0000").age(00).build();

        // when - entity 저장
        repository.save(user1);
    }
    @Test
    @DisplayName("User1 조회")
    public void selectUser1(){
        log.info("selectUser1()...");
        // given
        String uid = "H101";
        // when - 조회 하기
        Optional<User1> result = repository.findById(uid);
        User1 user1 = result.get();
        log.info(user1.toString());
    }
    @DisplayName("User1 목록")
    public void selectUser1s(){

    }
    @DisplayName("User1 수정")
    public void updateUser1(){

    }
    @DisplayName("User1 삭제")
    public void deleteUser1(){

    }

    // 사용자 정의 쿼리 메서드
    @Test
    public void findUser1ByUid() {
        User1 user1 = repository.findUser1ByUid("A101");
        log.info(user1.toString());
    }
    @Test
    public void findUser1ByName(){
        List<User1> user1s = repository.findUser1ByName("홍길동");
        log.info(user1s.toString());
    }
    @Test
    public void findUser1ByNameNot(){
        List<User1> user1s = repository.findUser1ByNameNot("홍길동");
        log.info(user1s.toString());
    }
    @Test
    public void findUser1ByUidAndName() {
        User1 user1 = repository.findUser1ByUidAndName("A101", "홍길동");
        log.info(user1.toString());
    }
    @Test
    public void findUser1ByUidOrName() {
        List<User1> user1s = repository.findUser1ByUidOrName("A101", "홍길동");
        log.info(user1s.toString());
    }

    @Test
    public void findUser1ByAgeGreaterThan(){
        List<User1> user1s = repository.findUser1ByAgeGreaterThan(20);
        log.info(user1s.toString());
    }
    @Test
    public void findUser1ByAgeGreaterThanEqual() {
        List<User1> user1s = repository.findUser1ByAgeGreaterThanEqual(22);
        log.info(user1s.toString());
    }
    @Test
    public void findUser1ByAgeLessThan() {
        List<User1> user1s = repository.findUser1ByAgeLessThan(30);
        log.info(user1s.toString());
    }
    @Test
    public void findUser1ByAgeLessThanEqual() {
        List<User1> user1s = repository.findUser1ByAgeLessThanEqual(22);
        log.info(user1s.toString());
    }
    @Test
    public void findUser1ByAgeBetween() {
        List<User1> user1s = repository.findUser1ByAgeBetween(10, 40);
        log.info(user1s.toString());
    }

    @Test
    public void findUser1ByNameLike() {
        List<User1> user1s = repository.findUser1ByNameLike("강감찬");
        log.info(user1s.toString());
    }
    @Test
    public void findUser1ByNameContains() {
        List<User1> user1s = repository.findUser1ByNameContains("홍길동");
        log.info(user1s.toString());
    }
    @Test
    public void findUser1ByNameStartsWith() {
        List<User1> user1s = repository.findUser1ByNameStartsWith("김");
        log.info(user1s.toString());
    }
    @Test
    public void findUser1ByNameEndsWith() {
        List<User1> user1s = repository.findUser1ByNameEndsWith("동");
        log.info(user1s.toString());
    }

    @Test
    public void findUser1ByOrderByName() {
        List<User1> user1s = repository.findUser1ByOrderByName();
        log.info(user1s.toString());
    }
    @Test
    public void findUser1ByOrderByAgeAsc() {
        List<User1> user1s = repository.findUser1ByOrderByAgeAsc();
        log.info(user1s.toString());
    }
    @Test
    public void findUser1ByOrderByAgeDesc() {
        List<User1> user1s = repository.findUser1ByOrderByAgeDesc();
        log.info(user1s.toString());
    }

    @Test
    public void findUser1ByAgeGreaterThanOrderByAgeDesc() {
        List<User1> user1s = repository.findUser1ByAgeGreaterThanOrderByAgeDesc(10);
        log.info(user1s.toString());
    }

    @Test
    public void countUser1ByUid(){
        int result = repository.countUser1ByUid("a101");
        log.info("countUser1ByUid : " + result);
    }
    @Test
    public void countUser1ByName(){
        int result = repository.countUser1ByName("홍길동");
        log.info("countUser1ByName : " + result);
    }


    // JPQL 정의
    @Test
    public void selectUser1UnderAge30(){
        List<User1> user1s = repository.selectUser1UnderAge30();
        log.info(user1s.toString());
    }
    @Test
    public void selectUser1ByName(){
        List<User1> user1s = repository.selectUser1ByName("장보고");
        log.info(user1s.toString());
    }
    @Test
    public void selectUser1ByNameParam(){
        List<User1> user1s = repository.selectUser1ByNameParam("김춘추");
        log.info(user1s.toString());
    }
    @Test
    public void selectUser1ByUid(){
        List<Object[]> user1s = repository.selectUser1ByUid("a101");
        for(Object[] user1 : user1s){
            log.info(user1[0].toString());
            log.info(user1[1].toString());
            log.info(user1[2].toString());
        }
    }
}