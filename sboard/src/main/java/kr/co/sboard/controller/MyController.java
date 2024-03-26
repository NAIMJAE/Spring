package kr.co.sboard.controller;

import kr.co.sboard.dto.UserDTO;
import kr.co.sboard.entity.User;
import kr.co.sboard.service.MyService;
import kr.co.sboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MyController {

    private final MyService myService;

    @GetMapping("/my/setting")
    public String setting(String uid, Model model){
        log.info("/my/setting");
        log.info("uid : " + uid);
        UserDTO user = myService.selectUserForSetting(uid);
        model.addAttribute("user", user);
        return "/my/setting";
    }

    // setting 페이지 사용자 인증
    @PostMapping("/my/checkUser")
    public ResponseEntity<?> checkPass(@RequestBody UserDTO userDTO){
        log.info("/my/checkPass - POST");
        boolean result = myService.checkPass(userDTO.getUid(), userDTO.getPass());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return ResponseEntity.ok().body(resultMap);
    }

    // 비밀번호 변경
    @PostMapping("/my/changPass")
    public ResponseEntity<?> changPass(@RequestBody UserDTO userDTO){
        log.info("/my/changPass - POST");
        UserDTO result = myService.changPass(userDTO.getUid(), userDTO.getPass());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return ResponseEntity.ok().body(resultMap);
    }
    
    // 닉네임 변경
    @PostMapping("/my/changeNick")
    public ResponseEntity<?> changeNick(@RequestBody UserDTO userDTO){
        log.info("/my/changeNick - POST");
        UserDTO result = myService.changeNick(userDTO.getUid(), userDTO.getNick());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return ResponseEntity.ok().body(resultMap);
    }
    // 전화번호 변경 - 닉네임이랑 묶을 수 있음
    @PostMapping("/my/changeHp")
    public ResponseEntity<?> changeHp(@RequestBody UserDTO userDTO){
        log.info("/my/changeHp - POST");
        UserDTO result = myService.changeHp(userDTO.getUid(), userDTO.getHp());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return ResponseEntity.ok().body(resultMap);
    }
    // 이메일 변경
    @PostMapping("/my/changeEmail")
    public ResponseEntity<?> changeEmail(@RequestBody UserDTO userDTO){
        log.info("/my/changeEmail - POST");
        UserDTO result = myService.changeEmail(userDTO.getUid(), userDTO.getEmail());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return ResponseEntity.ok().body(resultMap);
    }
    // 주소 변경
    @PostMapping("/my/changeAddr")
    public ResponseEntity<?> changeAddr(@RequestBody UserDTO userDTO){
        log.info("/my/changeAddr - POST");
        UserDTO result = myService.changeAddr(userDTO);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return ResponseEntity.ok().body(resultMap);
    }

    // 회원 탈퇴
    @ResponseBody
    @GetMapping("/my/deleteUser/{uid}")
    public ResponseEntity<?> deleteUser(@PathVariable("uid") String uid){
        UserDTO user = new UserDTO();
        user.setUid(uid);
        user.setLeaveDate(LocalDateTime.now());
        User deleteUser = myService.deleteUser(user);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", deleteUser);
        return ResponseEntity.ok().body(resultMap);
    }
}
