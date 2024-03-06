package kr.co.ch05.Service;

import kr.co.ch05.DTO.User4DTO;
import kr.co.ch05.Mapper.User4Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class User4Service {

    private final User4Mapper mapper;

    public User4Service(User4Mapper mapper) {
        this.mapper = mapper;
    }

    public void insertUser4(User4DTO user4DTO){
        mapper.insertUser4(user4DTO);
    }

    public User4DTO selectUser4(String uid){
        return mapper.selectUser4(uid);
    }

    public List<User4DTO> selectUser4s(){
        return mapper.selectUser4s();
    }

    public void updateUser4(User4DTO user4DTO){
        mapper.updateUser4(user4DTO);
    }

    public void deleteUser4(String uid){
        mapper.deleteUser4(uid);
    }
}
