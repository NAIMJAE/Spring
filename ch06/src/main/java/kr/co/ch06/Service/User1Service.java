package kr.co.ch06.Service;

import kr.co.ch06.DTO.User1DTO;
import kr.co.ch06.Mapper.User1Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class User1Service {
    private final User1Mapper mapper;
    public User1Service(User1Mapper mapper) {
        this.mapper = mapper;
    }

    public void insertUser1(User1DTO user1DTO){
        mapper.insertUser1(user1DTO);
    }
    public User1DTO selectUser1(String uid){
        return mapper.selectUser1(uid);
    }
    public List<User1DTO> selectUser1s(){
        return mapper.selectUser1s();
    }
    public void updateUser1(User1DTO user1DTO){
        mapper.updateUser1(user1DTO);
    }
    public void deleteUser1(String uid){
        mapper.deleteUser1(uid);
    }
}
