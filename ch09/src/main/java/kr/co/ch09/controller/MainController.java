package kr.co.ch09.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@AllArgsConstructor
@Controller
public class MainController {

    @GetMapping("/")
    public String index(){

        return "/index";
    }

    @GetMapping("/user1/list")
    public String user1list(){
        log.info("user1/list - GET");

        return "/user1/list";
    }

    @GetMapping("/user1/register")
    public String user1register(){
        log.info("user1/register - GET");

        return "/user1/register";
    }

    @GetMapping("/user1/modify")
    public String user1modify(){
        log.info("user1/modify - GET");

        return "/user1/modify";
    }
}
