package kr.co.ch10.service;

import kr.co.ch10.dto.UserDTO;
import kr.co.ch10.entity.User;
import kr.co.ch10.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    // user2 사용자 등록
    public ResponseEntity<?> insertUser(UserDTO userDTO){
        /*
            JPA save()는 삽입, 수정을 동시에 할 수 있는 메서드 이기 때문에
            삽입을 수행하고자 할 경우에는 먼저 미리 existsById()로 존재여부를 확인하고
            save()를 수행하면 됨
        */
        if (userRepository.existsById(userDTO.getUid())){
            // 아이디가 이미 존재하는 경우
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(userDTO.getUid() + "already exist");
        }else{
            // 아이디가 존재하지 않는 경우 등록 진행
            User user = userDTO.toEntity();
            userRepository.save(user);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userDTO);
        }
    }

    // user2 사용자 조회
    public ResponseEntity<?> selectUser(String uid){

        try {
            // orElseThrow() 메서드로 존재하는 Entity를 가져오거나 존재하지 않으면
            // 기본 예외 NoSuchElementException 발생
            User user = userRepository.findById(uid).orElseThrow();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(user.toDTO());
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("user not found");
        }
    }

    // user2 목록 조회
    public ResponseEntity<List<UserDTO>> selectUsers(){
        log.info("selectUsers 유저 목록 조회");
        List<UserDTO> userDTOs = userRepository.findAll()
                .stream()
                .map(entity -> UserDTO.builder()       // User2DTO를 생성하고 각각의 entity를 반환
                        .uid(entity.getUid())
                        .pass(entity.getPass())
                        .name(entity.getName())
                        .age(entity.getAge())
                        .hp(entity.getHp())
                        .role(entity.getRole())
                        .build())
                .toList();                              // List로 변환 List<User2DTO>

        return ResponseEntity.ok().body(userDTOs);
    }

    // user2 사용자 수정
    public ResponseEntity<?> updateUser(UserDTO userDTO){

        // 수정하기 전 존재 여부 확인
        if (userRepository.existsById(userDTO.getUid())){
            // 아이디가 존재하는 경우 수정 진행
            userRepository.save(userDTO.toEntity());

            // 수정 후 수정 데이터 반환
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userDTO);
        }else{
            // 사용자가 존재하지 않으면 NOT_FOUND 응답데이터와 user not found 메세지
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("user not found");
        }
    }

    // user2 사용자 삭제
    public ResponseEntity<?> deleteUser(String uid){

        // 삭제 전 삭제할 사용자 조회
        Optional<User> optUser = userRepository.findById(uid);

        if(optUser.isPresent()){
            // 사용자가 존재하면 삭제 후 삭제한 사용자 정보 ResponseEntity로 반환
            userRepository.deleteById(uid);
            return ResponseEntity
                    .ok()
                    .body(optUser.get());
        }else{
            // 사용자가 존재하지 않으면 NOT_FOUND 응답데이터와 user not found 메세지
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("user not found");
        }

    }

    /////
    public void logUserAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            System.out.println("권한: " + authority.getAuthority());
        }
    }
}
