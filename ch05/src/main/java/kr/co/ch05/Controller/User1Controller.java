package kr.co.ch05.Controller;

import kr.co.ch05.DTO.User1DTO;
import kr.co.ch05.Service.User1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class User1Controller {

    private final User1Service service;
    @Autowired
    public User1Controller(User1Service service){
        this.service = service;
    }

    @RequestMapping(value = "/user1/list", method = RequestMethod.GET)
    public String list(Model model){
        List<User1DTO> users = service.selectUser1s();
        model.addAttribute("users", users);
        return "/user1/list";
    }

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

    @GetMapping("/user1/modify")
    public String modify(@RequestParam("uid") String uid, Model model){
        User1DTO user1DTO = service.selectUser1(uid);
        model.addAttribute(user1DTO);
        return "/user1/modify";
    }
    @PostMapping("/user1/modify")
    public String modify(User1DTO user1DTO){
        service.updateUser1(user1DTO);
        return "redirect:/user1/list";
    }

    @GetMapping("/user1/delete")
    public String delete(@RequestParam("uid") String uid){
        service.deleteUser1(uid);
        return "redirect:/user1/list";
    }

}
