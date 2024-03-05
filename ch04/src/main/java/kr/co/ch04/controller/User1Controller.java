package kr.co.ch04.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.ch04.dto.User1DTO;
import kr.co.ch04.service.User1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class User1Controller {

    @Autowired
    private User1Service service;


    @GetMapping("/user1/register")
    public String register(){
        return "/user1/register";
    }
    @PostMapping("/user1/register")
    public String register(User1DTO user1DTO){
        System.out.println(user1DTO);
        // 등록
        service.insertUser1(user1DTO);
        // 리다이렉트
        return "redirect:/user1/list";
    }

    @GetMapping("/user1/list")
    public String list(Model model){
        // 조회
        List<User1DTO> users =  service.selectUser1s();
        // Model 참조(view에서 데이터 출력)
        model.addAttribute("users",users);
        return "/user1/list";
    }

    @GetMapping("/user1/delete")
    public String delete(@RequestParam("uid") String uid){
        System.out.println("uid : " + uid);
        service.deleteUser1(uid);

        return "redirect:/user1/list";
    }

    @GetMapping("/user1/modify")
    public String modify(@RequestParam("uid") String uid, Model model){
        System.out.println("uid : " + uid);
        User1DTO user1DTO = service.selectUser1(uid);
        model.addAttribute(user1DTO);
        return "/user1/modify";
    }

    @PostMapping("/user1/modify")
    public String modify(User1DTO user1DTO){
        System.out.println("user1DTO : " + user1DTO);
        service.updateUser1(user1DTO);
        return "redirect:/user1/list";
    }
}
