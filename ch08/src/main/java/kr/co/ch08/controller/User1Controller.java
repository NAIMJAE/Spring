package kr.co.ch08.controller;

import kr.co.ch08.dto.UserDTO;
import kr.co.ch08.sevice.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@AllArgsConstructor
public class User1Controller {

    private final UserService service;

    @GetMapping("/user1/login")
    public String login(){
        log.info("login-Get");

        return "/user1/login";
    }

    @GetMapping("/user1/register")
    public String register(){
        log.info("register-Get");

        return "/user1/register";
    }

    @PostMapping("/user1/register")
    public String register(UserDTO userDTO){
        log.info("register-Post");
        service.insertUser(userDTO);
        return "redirect:/user/login";
    }

    @GetMapping("/user1/success")
    public String success(){
        log.info("success-Get");

        return "/user1/success";
    }



}
