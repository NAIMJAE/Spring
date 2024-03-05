package kr.co.ch04.dao;

import kr.co.ch04.dto.User3DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class User3DAO {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public void insertUser3(User3DTO user3DTO){
        String sql = "INSERT INTO `user3` VALUES (?,?,?,?,?)";
        Object[] params = {
                user3DTO.getUid(),
                user3DTO.getName(),
                user3DTO.getBirth(),
                user3DTO.getHp(),
                user3DTO.getAddr()
        };
        jdbcTemplate.update(sql, params);
    }
    public User3DTO selectUser3(String uid){
        String sql = "SELECT * FROM `user3` WHERE `uid`=?";
        return jdbcTemplate.queryForObject(sql, new User3RowMapper(), uid);
    }
    public List<User3DTO> selectUser3s(){
        String sql = "SELECT * FROM `user3`";
        return jdbcTemplate.query(sql, new User3RowMapper());
    }
    public void updateUser3(User3DTO user3DTO){
        String sql = "UPDATE `user3` SET `name`=?, `birth`=?, `hp`=?, `addr`=? WHERE `uid`=?";
        Object[] params = {
                user3DTO.getName(),
                user3DTO.getBirth(),
                user3DTO.getHp(),
                user3DTO.getAddr(),
                user3DTO.getUid(),
        };
        jdbcTemplate.update(sql, params);
    }
    public void deleteUser3(String uid){
        String sql = "DELETE FROM `user3` WHERE `uid`=?";
        jdbcTemplate.update(sql, uid);
    }
}
