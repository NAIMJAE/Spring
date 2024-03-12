package kr.co.ch07.controller;

import kr.co.ch07.dto.User3DTO;
import kr.co.ch07.service.User3Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
public class User3Controller {
    private final User3Service service;

    @GetMapping("/user3/list")
    public String list(Model model){
        log.info("/user3/list-Get");
        List<User3DTO> users = service.selectUser3s();
        model.addAttribute("users", users);
        return "/user3/list";
    }

    @GetMapping("/user3/register")
    public String register(){
        log.info("/user3/register-Get");
        return "/user3/register";
    }

    @PostMapping("/user3/register")
    public String register(User3DTO user3DTO){
        log.info("/user3/register-Post");
        service.insertUser3(user3DTO);
        return "redirect:/user3/list";
    }

    @GetMapping("/user3/modify")
    public String modify(String uid, Model model){
        log.info("/user3/modify-Get");
        User3DTO user3DTO = service.selectUser3(uid);
        model.addAttribute("user3DTO", user3DTO);
        return "/user3/modify";
    }

    @PostMapping("/user3/modify")
    public String modify(User3DTO user3DTO){
        log.info("/user3/modify-Post");
        service.updateUser3(user3DTO);
        return "redirect:/user3/list";
    }

    @GetMapping("/user3/delete")
    public String delete(String uid){
        log.info("/user3/delete-Get");
        service.deleteUser3(uid);
        return "redirect:/user3/list";
    }
}