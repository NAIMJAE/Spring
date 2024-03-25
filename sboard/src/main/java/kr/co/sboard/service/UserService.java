package kr.co.sboard.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import kr.co.sboard.dto.TermsDTO;
import kr.co.sboard.dto.UserDTO;
import kr.co.sboard.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // JavaMailSender 주입
    private final JavaMailSender javaMailSender;

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

    public String selectUserForNick(String uid){
        return userMapper.selectUserForNick(uid);
    }

    public void updateUser(){}

    public void deleteUser(){}

    // 중복 체크
    public int checkUser(String type, String value){
        return userMapper.checkUser(type, value);
    }
    /*
        이메일 전송
        - build.gradle 파일에 spring-boot-starter-mail 의존성 추가 할것
        - application.yml 파일 spring email 설정 추가
     */
    @Value("${spring.mail.username}")
    private String sender;

    public ResponseEntity<?> sendEmailCode(HttpSession session, String receiver){

        log.info("sender : " + sender);

        // MimeMessage 생성
        MimeMessage message = javaMailSender.createMimeMessage();

        // 인증코드 생성 후 세션 저장
        int code = ThreadLocalRandom.current().nextInt(100000, 1000000);
        session.setAttribute("code", String.valueOf(code));

        log.info("code : " + code);

        String title = "sboard 인증코드 입니다.";
        String content = "<h1>인증코드는 " + code + "입니다.</h1>";

        try {
            message.setSubject(title);
            message.setFrom(new InternetAddress(sender, "보내는 사람", "UTF-8"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(title);
            message.setContent(content, "text/html;charset=UTF-8");

            javaMailSender.send(message);
            return ResponseEntity.status(HttpStatus.OK).body("success");
        }catch(Exception e){
            log.error("sendEmailConde : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
    }

    // 이메일로 사용자 찾기
    public UserDTO selectUserForNameAndEmail(String name, String email){
        return userMapper.selectUserForNameAndEmail(name, email);
    }
    // 이메일로 사용자 찾기
    public UserDTO selectUserForUidAndEmail(String uid, String email){
        return userMapper.selectUserForUidAndEmail(uid, email);
    }

    // 비밀번호 변경
    public int updateUserPassword(UserDTO userDTO){
        String encoded = passwordEncoder.encode(userDTO.getPass());
        return userMapper.updateUserPassword(encoded, userDTO.getUid());
    }
}
