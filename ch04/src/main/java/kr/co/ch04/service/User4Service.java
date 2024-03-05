package kr.co.ch04.service;

import kr.co.ch04.dao.User4DAO;
import kr.co.ch04.dto.User4DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class User4Service {

    @Autowired
    private User4DAO dao;

    public void insertUser4(User4DTO user4DTO){
        dao.insertUser4(user4DTO);
    }

    public User4DTO selectUser4(String uid){
        return dao.selectUser4(uid);
    }

    public List<User4DTO> selectUser4s(){
        return dao.selectUser4s();
    }

    public void updateUser4(User4DTO user4DTO){
        dao.updateUser4(user4DTO);
    }

    public void deleteUser4(String uid){
        dao.deleteUser4(uid);
    }
}
