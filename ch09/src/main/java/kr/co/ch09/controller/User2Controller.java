package kr.co.ch09.controller;

import kr.co.ch09.dto.User2DTO;
import kr.co.ch09.service.User2Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class User2Controller {

    private final User2Service user2Service;

    @GetMapping("/user2")
    public ResponseEntity<List<User2DTO>> list(){
        return user2Service.selectUser2s();
    }

    @GetMapping("/user2/{uid}")
    public ResponseEntity<?> user2(@PathVariable("uid") String uid){
        return user2Service.selectUser2(uid);
    }

    @PostMapping("/user2")
    public ResponseEntity<?> register(@RequestBody @Validated User2DTO user2DTO){
        return user2Service.insertUser2(user2DTO);
    }

    @PutMapping("/user2")
    public ResponseEntity<?> modify(@RequestBody User2DTO user2DTO){
        return user2Service.updateUser2(user2DTO);
    }

    @DeleteMapping("/user2/{uid}")
    public ResponseEntity<?> delete(@PathVariable("uid") String uid){
        return user2Service.deleteUser2(uid);
    }
}
