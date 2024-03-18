package kr.co.sboard.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.sboard.dto.TermsDTO;
import kr.co.sboard.dto.UserDTO;
import kr.co.sboard.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/user/login")
    public String login(){
        log.info("/user/login - GET");

        return "/user/login";
    }

    @GetMapping("/user/terms")
    public String terms(Model model){
        log.info("/user/terms - GET");
        TermsDTO termsDTO = userService.selectTerms();
        model.addAttribute("termsDTO", termsDTO);
        return "/user/terms";
    }

    @GetMapping("/user/register")
    public String register(){
        log.info("/user/register - GET");

        return "/user/register";
    }

    // 로그인 폼 전송
    @PostMapping("/user/register")
    public String register(HttpServletRequest req, UserDTO userDTO){
        log.info("/user/register - POST");
        String regip = req.getRemoteAddr();
        userDTO.setRegip(regip);
        log.info(userDTO.toString());
        userService.insertUser(userDTO);
        return "redirect:/user/register?success=200";
    }

    // 중복 검사
    @ResponseBody
    @GetMapping("/userCheck/{type}/{value}")
    public ResponseEntity<?> usercheck(@PathVariable("type") String type, @PathVariable("value") String value){

        int result = userService.checkUser(type, value);

        log.info("type : " + type + " | value : " + value + " | result : " + result);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return ResponseEntity.ok().body(resultMap);
    }
}
