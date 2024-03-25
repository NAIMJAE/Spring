package kr.co.sboard.service;

import kr.co.sboard.dto.UserDTO;
import kr.co.sboard.entity.User;
import kr.co.sboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserDTO selectUserForSetting(String uid){
        Optional<User> user = userRepository.findById(uid);
        if (user.isPresent()){
            return modelMapper.map(user, UserDTO.class);
        }else {
            return null;
        }
    }


}
