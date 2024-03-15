package kr.co.ch10.controller;

import kr.co.ch10.dto.UserDTO;
import kr.co.ch10.entity.User;
import kr.co.ch10.jwt.JwtProvider;
import kr.co.ch10.security.MyUserDetails;
import kr.co.ch10.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO){
        log.info("login - POST");

        try {
            log.info("ControllerLogin-1 인증 처리");

            // security 인증 처리 
            UsernamePasswordAuthenticationToken authToken
                    = new UsernamePasswordAuthenticationToken(userDTO.getUid(), userDTO.getPass());

            log.info("ControllerLogin-2 DB 조회");

            // 사용자 DB 조회
            Authentication authentication = authenticationManager.authenticate(authToken);

            log.info("ControllerLogin-3 인증된 사용자 가져오기");

            // 인증된 사용자 가져오기
            MyUserDetails UserDetails = (MyUserDetails) authentication.getPrincipal();
            User user = UserDetails.getUser();

            log.info("ControllerLogin-4 user : " + user);

            log.info("ControllerLogin-5 토큰 발급");

            // 토큰 발급(access, refresh)
            String access = jwtProvider.createToken(user, 1);   // 1일

            log.info("ControllerLogin-6 access : " + access);

            String refresh = jwtProvider.createToken(user, 7);  // 7일

            log.info("ControllerLogin-6 refresh : " + refresh);

            // refresh 토큰 DB 저장
            log.info("ControllerLogin-7 refresh 토큰 전달");

            // access 토큰 클라이언트에 전달
            log.info("ControllerLogin-8 access 토큰 전달");

            Map<String, Object> map = new HashMap<>();
            map.put("grantType", "Bearer");
            map.put("access", access);

            log.info("ControllerLogin-9 controller 정상 종료");
            userService.logUserAuthorities();//
            return ResponseEntity.ok().body(map);

        }catch (Exception e){
            log.info("login - Exception : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
    }



    @GetMapping("/user")
    public ResponseEntity<List<UserDTO>> list(){
        log.info("/User - GET (service로 목록 호출) ");
        return userService.selectUsers();
    }
}