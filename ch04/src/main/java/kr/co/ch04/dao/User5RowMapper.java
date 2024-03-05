package kr.co.ch04.dao;

import kr.co.ch04.dto.User5DTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User5RowMapper implements RowMapper<User5DTO> {
    @Override
    public User5DTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        User5DTO user5DTO = new User5DTO();
        user5DTO.setSeq(rs.getString(1));
        user5DTO.setName(rs.getString(2));
        user5DTO.setGender(rs.getString(3));
        user5DTO.setAge(rs.getInt(4));
        user5DTO.setAddr(rs.getString(5));
        return user5DTO;
    }
}
