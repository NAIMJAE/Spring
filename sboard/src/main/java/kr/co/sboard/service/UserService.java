package kr.co.sboard.service;

import jdk.swing.interop.LightweightContentWrapper;
import kr.co.sboard.dto.TermsDTO;
import kr.co.sboard.dto.UserDTO;
import kr.co.sboard.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // 이용 약관 조회
    public TermsDTO selectTerms(){
        log.info("selectTerms");
        return userMapper.selectTerms();
    }
    // 회원 가입
    public void insertUser(UserDTO userDTO){
        log.info("insertUser");
        String encoded = passwordEncoder.encode(userDTO.getPass());
        userDTO.setPass(encoded);
        userMapper.insertUser(userDTO);
    }
    // 로그인 회원 조회
    public int selectUser(String uid, String pass){
        log.info("selectUser");
        String encoded = passwordEncoder.encode(pass);
        return userMapper.selectUser(uid, encoded);
    }

    public void selectUsers(){}

    public void updateUser(){}

    public void deleteUser(){}

    // 중복 체크
    public int selectCheckUid(String uid){
        return userMapper.selectCheckUid(uid);
    }
    public int checkUser(String type, String value){
        return userMapper.checkUser(type, value);
    }
}
