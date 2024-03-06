package kr.co.ch05.Mapper;

import kr.co.ch05.DTO.User4DTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface User4Mapper {
    public void insertUser4(User4DTO user4DTO);
    public User4DTO selectUser4(String uid);
    public List<User4DTO> selectUser4s();
    public void updateUser4(User4DTO user4DTO);
    public void deleteUser4(String uid);
}
