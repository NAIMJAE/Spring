package kr.co.ch06.Service;

import kr.co.ch06.DTO.User3DTO;
import kr.co.ch06.Mapper.User3Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class User3Service {
    private final User3Mapper mapper;

    public void insertUser3(User3DTO user3DTO){
        mapper.insertUser3(user3DTO);
    }
    public User3DTO selectUser3(String uid){
        return mapper.selectUser3(uid);
    }
    public List<User3DTO> selectUser3s(){
        return mapper.selectUser3s();
    }
    public void updateUser3(User3DTO user3DTO){
        mapper.updateUser3(user3DTO);
    }
    public void deleteUser3(String uid){
        mapper.deleteUser3(uid);
    }
}
