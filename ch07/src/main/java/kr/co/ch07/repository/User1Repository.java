package kr.co.ch07.repository;

import kr.co.ch07.entity.User1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository                                         // 엔티티, 엔티티@ID
public interface User1Repository extends JpaRepository<User1, String>{

    // 사용자 정의 쿼리 메서드
    public User1 findUser1ByUid(String uid);                          // SELECT * FROM `User1` WHERE `uid`=?
    public List<User1> findUser1ByName(String name);                  // SELECT * FROM `User1` WHERE `name`=?
    public List<User1> findUser1ByNameNot(String name);               // SELECT * FROM `User1` WHERE `name`!=?

    public User1 findUser1ByUidAndName(String uid, String name);      // SELECT * FROM `User1` WHERE `uid`=? AND `name`=?
    public List<User1> findUser1ByUidOrName(String uid, String name); // SELECT * FROM `User1` WHERE `uid`=? OR `name`=?

    public List<User1> findUser1ByAgeGreaterThan(int age);            // SELECT * FROM `User1` WHERE `age` > ?
    public List<User1> findUser1ByAgeGreaterThanEqual(int age);       // SELECT * FROM `User1` WHERE `age` >= ?
    public List<User1> findUser1ByAgeLessThan(int age);               // SELECT * FROM `User1` WHERE `age` < ?
    public List<User1> findUser1ByAgeLessThanEqual(int age);          // SELECT * FROM `User1` WHERE `age` =< ?
    public List<User1> findUser1ByAgeBetween(int age, int high);      // SELECT * FROM `User1` WHERE ? < `age` < ?

    public List<User1> findUser1ByNameLike(String name);              // SELECT * FROM `User1` WHERE `name` LIKE ?
    public List<User1> findUser1ByNameContains(String name);          // SELECT * FROM `User1` WHERE `name` LIKE %?%
    public List<User1> findUser1ByNameStartsWith(String name);        // SELECT * FROM `User1` WHERE `name` LIKE ?%
    public List<User1> findUser1ByNameEndsWith(String name);          // SELECT * FROM `User1` WHERE `name` LIKE %?

    public List<User1> findUser1ByOrderByName();                      // SELECT * FROM `User1` ORDER BY `name`
    public List<User1> findUser1ByOrderByAgeAsc();                    // SELECT * FROM `User1` ORDER BY `age` ASC
    public List<User1> findUser1ByOrderByAgeDesc();                   // SELECT * FROM `User1` ORDER BY `age` DESC
    public List<User1> findUser1ByAgeGreaterThanOrderByAgeDesc(int age); //  SELECT * FROM `User1` WHERE `age` > ? ORDER BY `age`

    public int countUser1ByUid(String uid);                           // SELECT COUNT(*) FROM `User1` WHERE `uid`=?
    public int countUser1ByName(String name);                         // SELECT COUNT(*) FROM `User1` WHERE `name`=?

    // JPQL 정의
    @Query("select u1 from User1 as u1 where u1.age < 30")            // SELECT * FROM `User1` AS u1 WHERE `age` < 30
    public List<User1> selectUser1UnderAge30();

    @Query("select u1 from User1 as u1 where u1.name = ?1")           // SELECT * FROM `User1` AS u1 WHERE `name`=?
    public List<User1> selectUser1ByName(String name);

    @Query("select u1 from User1 as u1 where u1.name = :name")        // SELECT * FROM `User1` AS u1 WHERE `name`=?
    public List<User1> selectUser1ByNameParam(@Param("name") String name);

    @Query("select u1.uid, u1.name, u1.age from User1 as u1 where u1.uid = :uid")
    public List<Object[]> selectUser1ByUid(@Param("uid") String uid);   // SELECT u1.uid, u1.name, u1.age FROM `User1` AS u1 WHERE `uid`=?

}
