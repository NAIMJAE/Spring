package kr.co.ch07.service;

import kr.co.ch07.dto.User2DTO;
import kr.co.ch07.entity.User2;
import kr.co.ch07.repository.User2Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class User2Service {
    private final User2Repository repository;

    public void insertUser2(User2DTO user2DTO){
        // DTO를 Entity로 변환
        User2 user2 = user2DTO.toEntity();
        // Entity 저장
        repository.save(user2);
    }

    public User2DTO selectUser2(String uid){
        // findById로 쿼리 조회
        Optional<User2> result = repository.findById(uid);
        // user2로 결과값 반환
        User2 user2 = result.get();
        // DTO로 변환해 리턴
        return user2.toDTO();
    }

    public List<User2DTO> selectUser2s(){
        // findAll로 쿼리 조회
        List<User2> user2s = repository.findAll();
        // 반복문으로 DTO로 변환
        List<User2DTO> user2DTOs = new ArrayList<>();
        for(User2 user2 : user2s) {
            user2DTOs.add(user2.toDTO());
        }
        return user2DTOs;
    }

    public void updateUser2(User2DTO user2DTO){
        // DTO를 Entity로 변환
        User2 user2 = user2DTO.toEntity();
        // 쿼리 수행
        repository.save(user2);
    }

    public void deleteUser2(String uid){
        // 쿼리 수행
        repository.deleteById(uid);
    }
}
