package kr.co.ch07.controller;

import kr.co.ch07.dto.User1DTO;
import kr.co.ch07.service.User1Service;
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
public class User1Controller {

    private User1Service service;

    @GetMapping("/user1/list")
    public String list(Model model){
        log.info("list - get");
        List<User1DTO> users = service.selectUser1s();
        model.addAttribute("users", users);
        return "/user1/list";
    }

    @GetMapping("/user1/register")
    public String register(){
        log.info("register - get");
        return "/user1/register";
    }

    @PostMapping("/user1/register")
    public String register(User1DTO user1DTO){
        log.info("register - post : " + user1DTO.toString());
        service.insertUser1(user1DTO);
        return "redirect:/user1/list";
    }

    @GetMapping("/user1/modify")
    public String modify(String uid, Model model){
        log.info("modify - get");
        User1DTO user1DTO = service.selectUser1(uid);
        model.addAttribute("user1DTO", user1DTO);
        return "/user1/modify";
    }

    @PostMapping("/user1/modify")
    public String modify(User1DTO user1DTO){
        log.info("modify - post : " + user1DTO.toString());
        service.updateUser1(user1DTO);
        return "redirect:/user1/list";
    }

    @GetMapping("/user1/delete")
    public String delete(String uid){
        log.info("delete - get");
        service.deleteUser1(uid);
        return "redirect:/user1/list";
    }
}
