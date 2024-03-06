package kr.co.ch05.Controller;

import kr.co.ch05.DTO.User5DTO;
import kr.co.ch05.Service.User5Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class User5Controller {

    private final User5Service service;

    public User5Controller(User5Service service) {
        this.service = service;
    }

    @GetMapping("/user5/list")
    public String list(Model model){
        List<User5DTO> users = service.selectUser5s();
        model.addAttribute("users", users);
        return "/user5/list";
    }

    @GetMapping("/user5/register")
    public String register(){
        return "/user5/register";
    }

    @PostMapping("/user5/register")
    public String register(User5DTO user5DTO){
        service.insertUser5(user5DTO);
        return "redirect:/user5/list";
    }

    @GetMapping("/user5/modify")
    public String modify(@RequestParam("seq") String seq, Model model){
        User5DTO user5DTO = service.selectUser5(seq);
        model.addAttribute(user5DTO);
        return "/user5/modify";
    }

    @PostMapping("/user5/modify")
    public String modify(User5DTO user5DTO){
        service.updateUser5(user5DTO);
        return "redirect:/user5/list";
    }

    @GetMapping("/user5/delete")
    public String delete(@RequestParam("seq") String seq){
        service.deleteUser5(seq);
        return "redirect:/user5/list";
    }
}
