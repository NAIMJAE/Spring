package kr.co.sboard.security;

import kr.co.sboard.entity.User;
import kr.co.sboard.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class SecurityUserService implements UserDetailsService {
    // 주입
    private final UserRepository userRepository;

    // 인증 수행
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("g2");
        Optional<User> result = userRepository.findById(username);

        UserDetails userDetails = null;

        if(!result.isEmpty()){
            // 해당하는 사용자가 존재하면 인증 객체 생성
            User user = result.get();
            userDetails = MyUserDetails.builder().user(user).build();
            log.info(userDetails.toString());
        }
        // Security ContextHolder에 저장
        return userDetails;
    }
}
