package kr.co.ch04.dao;

import kr.co.ch04.dto.User2DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class User2DAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertUser2(User2DTO user2DTO){
        String sql = "INSERT INTO `user2` VALUES (?,?,?,?)";
        Object[] params = {
                user2DTO.getUid(),
                user2DTO.getName(),
                user2DTO.getBirth(),
                user2DTO.getAddr()
        };
        jdbcTemplate.update(sql, params);
    }

    public User2DTO selectUser2(String uid){
        String sql = "SELECT * FROM `user2` WHERE `uid`=?";
        return jdbcTemplate.queryForObject(sql, new User2RowMapper(), uid);
    }

    public List<User2DTO> selectUser2s(){
        String sql = "SELECT * FROM `user2`";
        return jdbcTemplate.query(sql, new User2RowMapper());
    }

    public void updateUser2(User2DTO user2DTO){
        String sql = "UPDATE `user2` SET `name`=?, `birth`=?, `addr`=? WHERE `uid`=?";
        Object[] params = {
                user2DTO.getName(),
                user2DTO.getBirth(),
                user2DTO.getAddr(),
                user2DTO.getUid()
        };
        jdbcTemplate.update(sql, params);
    }

    public void deleteUser2(String uid){
        String sql = "DELETE FROM `user2` WHERE `uid`=?";
        jdbcTemplate.update(sql, uid);
    }
}
