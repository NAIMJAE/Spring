package kr.co.ch06.Mapper;

import kr.co.ch06.DTO.User3DTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/*
    @Mapper
     - MapperScan을 통한 해당 Mapper 등록
     - Mapper xml 파일과 연결 및 데이터베이스와 상호작용
 */
@Mapper
public interface User3Mapper {
    public void insertUser3(User3DTO user3DTO);
    public User3DTO selectUser3(String uid);
    public List<User3DTO> selectUser3s();
    public void updateUser3(User3DTO user3DTO);
    public void deleteUser3(String uid);
}
