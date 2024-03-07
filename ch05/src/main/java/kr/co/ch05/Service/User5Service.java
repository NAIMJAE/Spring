package kr.co.ch05.Service;

import kr.co.ch05.DTO.User5DTO;
import kr.co.ch05.Mapper.User5Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class User5Service {

    private final User5Mapper mapper;

    public User5Service(User5Mapper mapper) {
        this.mapper = mapper;
    }

    public void insertUser5(User5DTO user5DTO){
        mapper.insertUser5(user5DTO);
    }

    public User5DTO selectUser5(String seq){
        return mapper.selectUser5(seq);
    }

    public List<User5DTO> selectUser5s(){
        return mapper.selectUser5s();
    }

    public void updateUser5(User5DTO user5DTO){
        mapper.updateUser5(user5DTO);
    }

    public void deleteUser5(String seq){
        mapper.deleteUser5(seq);
    }
}
