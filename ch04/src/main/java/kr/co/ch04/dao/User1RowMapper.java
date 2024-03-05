package kr.co.ch04.dao;


import kr.co.ch04.dto.User1DTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User1RowMapper implements RowMapper<User1DTO> {
    /*
        - SELECT 결과 처리 메서드
        - SELECT 결과가 1개 이상이면 List
        - SELECT 결과가 1개이면 DTO 생성
    */

    @Override
    public User1DTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        User1DTO user1DTO = new User1DTO();
        user1DTO.setUid(rs.getString(1));
        user1DTO.setName(rs.getString(2));
        user1DTO.setBirth(rs.getString(3));
        user1DTO.setHp(rs.getString(4));
        user1DTO.setAge(rs.getInt(5));
        return user1DTO;
    }
}
