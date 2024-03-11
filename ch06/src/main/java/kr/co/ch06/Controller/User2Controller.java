package kr.co.ch06.Controller;

import kr.co.ch06.DTO.User2DTO;
import kr.co.ch06.Service.User2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class User2Controller {
    private final User2Service service;
    public User2Controller(User2Service service) {
        this.service = service;
    }

    @GetMapping("/user2/list")
    public String list(Model model){
        log.info("GetMapping - list");
        List<User2DTO> users = service.selectUser2s();
        model.addAttribute("users", users);
        return "/user2/list";
    }

    @GetMapping("/user2/register")
    public String register(){
        log.info("GetMapping - register");
        return "/user2/register";
    }

    @PostMapping("/user2/register")
    public String register(User2DTO user2DTO){
        log.info("PostMapping - register");
        service.insertUser2(user2DTO);
        log.info("insertUser2 : " + user2DTO);
        return "redirect:/user2/list";
    }

    @GetMapping("/user2/modify")
    public String modify(@RequestParam("uid") String uid, Model model){
        log.info("GetMapping - modify");
        User2DTO user2DTO = service.selectUser2(uid);
        log.info("selectUser2 : " + user2DTO);
        model.addAttribute("user2DTO", user2DTO);
        return "/user2/modify";
    }

    @PostMapping("/user2/modify")
    public String modify(User2DTO user2DTO){
        log.info("PostMapping - modify");
        service.updateUser2(user2DTO);
        log.info("updateUser2 : " + user2DTO);
        return "redirect:/user2/list";
    }

    @GetMapping("/user2/delete")
    public String delete(@RequestParam("uid") String uid){
        log.info("GetMapping - delete");
        service.deleteUser2(uid);
        return "redirect:/user2/list";
    }
}
