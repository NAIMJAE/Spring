package kr.co.ch04.dao;


import kr.co.ch04.dto.User1DTO;
import kr.co.ch04.dto.User2DTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User2RowMapper implements RowMapper<User2DTO> {
    /*
        - SELECT 결과 처리 메서드
        - SELECT 결과가 1개 이상이면 List
        - SELECT 결과가 1개이면 DTO 생성
    */

    @Override
    public User2DTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        User2DTO user2DTO = new User2DTO();
        user2DTO.setUid(rs.getString(1));
        user2DTO.setName(rs.getString(2));
        user2DTO.setBirth(rs.getString(3));
        user2DTO.setAddr(rs.getString(4));
        return user2DTO;
    }
}
