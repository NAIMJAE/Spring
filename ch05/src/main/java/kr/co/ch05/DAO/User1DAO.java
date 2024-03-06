package kr.co.ch05.DAO;

import kr.co.ch05.DTO.User1DTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class User1DAO {

    // MyBatis 객체 - 생성자 주입
    private final SqlSessionTemplate mybatis;

    @Autowired
    public User1DAO(SqlSessionTemplate mybatis){
        this.mybatis = mybatis;
    }

    public void insertUser1(User1DTO user1DTO){
        mybatis.insert("user1.insertUser1", user1DTO);
    }
    public User1DTO selectUser1(String uid){
        return mybatis.selectOne("user1.selectUser1", uid);
    }
    public List<User1DTO> selectUser1s(){
        return mybatis.selectList("user1.selectUser1s");
    }
    public void updateUser1(User1DTO user1DTO){
        mybatis.update("user1.updateUser1", user1DTO);
    }
    public void deleteUser1(String uid){
        mybatis.delete("user1.deleteUser1", uid);
    }
}
