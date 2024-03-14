package kr.co.ch07.service;

import kr.co.ch07.dto.User3DTO;
import kr.co.ch07.entity.User3;
import kr.co.ch07.repository.User3Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class User3Service {
    private final User3Repository repository;

    public void insertUser3(User3DTO user3DTO){
        // Entity로 변환
        User3 user3 = user3DTO.toEntity();
        // 쿼리 실행
        repository.save(user3);
    }

    public User3DTO selectUser3(String uid){
        // 쿼리 실행
        Optional<User3> user3s = repository.findById(uid);
        // Entity로 반환
        User3 user3 = user3s.get();
        // DTO로 변환 후 리턴
        return user3.toDTO();
    }

    public List<User3DTO> selectUser3s(){
        // 쿼리 실행
        List<User3> user3s = repository.findAll();
        // 반복문으로 DTO로 변환
        List<User3DTO> user3DTOs = new ArrayList<>();
        for(User3 user3 : user3s){
            user3DTOs.add(user3.toDTO());
        }
        return user3DTOs;
    }

    public void updateUser3(User3DTO user3DTO){
        // Entity로 변환
        User3 user3 = user3DTO.toEntity();
        // 쿼리 실행
        repository.save(user3);
    }

    public void deleteUser3(String uid){
        // 쿼리 실행
        repository.deleteById(uid);
    }
}
