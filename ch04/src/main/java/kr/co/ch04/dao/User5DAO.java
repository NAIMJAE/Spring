package kr.co.ch04.dao;

import kr.co.ch04.dto.User5DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class User5DAO {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public void insertUser5(User5DTO user5DTO){
        String sql = "INSERT INTO `user5` (name, gender, age, addr) VALUES (?,?,?,?)";
        Object[] params = {
                user5DTO.getName(),
                user5DTO.getGender(),
                user5DTO.getAge(),
                user5DTO.getAddr()
        };
        jdbcTemplate.update(sql, params);
    }

    public User5DTO selectUser5(String seq){
        String sql = "SELECT * FROM `user5` WHERE `seq`=?";
        return jdbcTemplate.queryForObject(sql, new User5RowMapper(), seq);
    }

    public List<User5DTO> selectUser5s(){
        String sql = "SELECT * FROM `user5`";
        return jdbcTemplate.query(sql, new User5RowMapper());
    }

    public void updateUser5(User5DTO user5DTO){
        String sql = "UPDATE `user5` SET `name`=?, `gender`=?, `age`=?, `addr`=? WHERE `seq`=?";
        Object[] params = {
                user5DTO.getName(),
                user5DTO.getGender(),
                user5DTO.getAge(),
                user5DTO.getAddr(),
                user5DTO.getSeq()
        };
        jdbcTemplate.update(sql, params);
    }

    public void deleteUser5(String seq){
        String sql = "DELETE FROM `user5` WHERE `seq`=?";
        jdbcTemplate.update(sql, seq);
    }
}
