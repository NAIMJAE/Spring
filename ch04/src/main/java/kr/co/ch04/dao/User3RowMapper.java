package kr.co.ch04.dao;

import kr.co.ch04.dto.User3DTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User3RowMapper implements RowMapper<User3DTO> {

    @Override
    public User3DTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        User3DTO user3DTO = new User3DTO();
        user3DTO.setUid(rs.getString(1));
        user3DTO.setName(rs.getString(2));
        user3DTO.setBirth(rs.getString(3));
        user3DTO.setHp(rs.getString(4));
        user3DTO.setAddr(rs.getString(5));
        return user3DTO;
    }
}
