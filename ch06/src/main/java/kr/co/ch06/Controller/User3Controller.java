package kr.co.ch06.Controller;

import kr.co.ch06.DTO.User3DTO;
import kr.co.ch06.Service.User3Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class User3Controller {
    // @AllArgsConstructor 선언으로 생성자 주입 -> 따로 생성자 정의 필요 없음
    private final User3Service service;

    @GetMapping("/user3/list")
    public String list(Model model){
        log.info("GetMapping - list");
        List<User3DTO> users = service.selectUser3s();
        model.addAttribute("users", users);
        return "/user3/list";
    }

    @GetMapping("/user3/register")
    public String register(){
        log.info("GetMapping - register");
        return "/user3/register";
    }

    @PostMapping("/user3/register")
    public String register(User3DTO user3DTO){
        log.info("PostMapping - register");
        service.insertUser3(user3DTO);
        return "redirect:/user3/list";
    }

    @GetMapping("/user3/modify")
    public String modify(String uid, Model model){
        log.info("GetMapping - modify");
        User3DTO user3DTO = service.selectUser3(uid);
        model.addAttribute("user3DTO", user3DTO);
        return "/user3/modify";
    }

    @PostMapping("/user3/modify")
    public String modify(User3DTO user3DTO){
        log.info("PostMapping - modify");
        service.updateUser3(user3DTO);
        return "redirect:/user3/list";
    }

    @GetMapping("/user3/delete")
    public String delete(String uid){
        log.info("GetMapping - delete");
        service.deleteUser3(uid);
        return "redirect:/user3/list";
    }
}
