package kr.co.ch04.controller;

import kr.co.ch04.dto.User2DTO;
import kr.co.ch04.service.User2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class User2Controller {

    @Autowired
    private User2Service service;

    @GetMapping("/user2/register")
    public String register(){
        return "/user2/register";
    }

    @PostMapping("/user2/register")
    public String register(User2DTO user2DTO){
        service.insertUser2(user2DTO);
        return "redirect:/user2/list";
    }

    @GetMapping("/user2/list")
    public String list(Model model){
        List<User2DTO> users = service.selectUser2s();
        model.addAttribute("users",users);
        return "/user2/list";
    }

    @GetMapping("/user2/modify")
    public String modify(@RequestParam("uid") String uid, Model model){
        User2DTO user2DTO = service.selectUser2(uid);
        model.addAttribute(user2DTO);
        return "/user2/modify";
    }

    @PostMapping("/user2/modify")
    public String modify(User2DTO user2DTO){
        service.updateUser2(user2DTO);
        return "redirect:/user2/list";
    }

    @GetMapping("/user2/delete")
    public String delete(@RequestParam("uid") String uid){
        service.deleteUser2(uid);
        return "redirect:/user2/list";
    }
}
