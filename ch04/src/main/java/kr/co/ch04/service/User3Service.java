package kr.co.ch04.service;

import kr.co.ch04.dao.User3DAO;
import kr.co.ch04.dto.User3DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class User3Service {

    @Autowired
    private User3DAO dao;

    public void insertUser3(User3DTO user3DTO){
        dao.insertUser3(user3DTO);
    }
    public User3DTO selectUser3(String uid){
        return dao.selectUser3(uid);
    }
    public List<User3DTO> selectUser3s(){
        return dao.selectUser3s();
    }
    public void updateUser3(User3DTO user3DTO){
        dao.updateUser3(user3DTO);
    }
    public void deleteUser3(String uid){
        dao.deleteUser3(uid);
    }
}
