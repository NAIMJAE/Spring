package kr.co.sboard.service;

import kr.co.sboard.dto.UserDTO;
import kr.co.sboard.entity.User;
import kr.co.sboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDTO selectUserForSetting(String uid){
        Optional<User> user = userRepository.findById(uid);
        if (user.isPresent()){
            return modelMapper.map(user, UserDTO.class);
        }else {
            return null;
        }
    }

    // setting 페이지 사용자 인증
    public boolean checkPass(String uid, String pass){
        Optional<User> user =  userRepository.findById(uid);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean result = encoder.matches(pass, user.get().getPass());
        return result;
    }

    // 비밀번호 변경
    public UserDTO changPass(String uid, String pass){
        Optional<User> user =  userRepository.findById(uid);
        String changePass = passwordEncoder.encode(pass);
        user.get().setPass(changePass);
        User changeUser = userRepository.save(user.get());
        return modelMapper.map(changeUser, UserDTO.class);
    }
    
    // 닉네임 변경
    public UserDTO changeNick(String uid, String nick){
        Optional<User> user =  userRepository.findById(uid);
        user.get().setNick(nick);
        User changeUser = userRepository.save(user.get());
        return modelMapper.map(changeUser, UserDTO.class);
    }

    // 전화번호 변경
    public UserDTO changeHp(String uid, String Hp){
        Optional<User> user =  userRepository.findById(uid);
        user.get().setHp(Hp);
        User changeUser = userRepository.save(user.get());
        return modelMapper.map(changeUser, UserDTO.class);
    }

    // 이메일 변경
    public UserDTO changeEmail(String uid, String email){
        Optional<User> user =  userRepository.findById(uid);
        user.get().setEmail(email);
        User changeUser = userRepository.save(user.get());
        return modelMapper.map(changeUser, UserDTO.class);
    }

    // 주소 변경
    public UserDTO changeAddr(UserDTO userDTO){
        Optional<User> user =  userRepository.findById(userDTO.getUid());
        user.get().setZip(userDTO.getZip());
        user.get().setAddr1(userDTO.getAddr1());
        user.get().setAddr2(userDTO.getAddr2());
        User changeUser = userRepository.save(user.get());
        return modelMapper.map(changeUser, UserDTO.class);
    }
    // 회원 탈퇴
    public User deleteUser(UserDTO userDTO){
        Optional<User> optUser = userRepository.findById(userDTO.getUid());
        if (optUser.isPresent()){
            String random = optUser.get().getPass().substring(0, 6) + (int)(Math.random()*100000);
            User deleteUser = User.builder()
                            .uid(userDTO.getUid()).pass(random).name(random).nick(random).email(random).hp(random)
                            .regip(random).leaveDate(userDTO.getLeaveDate())
                            .build();
            return userRepository.save(deleteUser);
        }else {
            return null;
        }
    }
}
