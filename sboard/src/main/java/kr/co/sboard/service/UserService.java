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

    public void insertUser(UserDTO userDTO){
        log.info("insertUser");
        String encoded = passwordEncoder.encode(userDTO.getPass());
        userDTO.setPass(encoded);
        userMapper.insertUser(userDTO);
    }

    public void selectUser(){}

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
