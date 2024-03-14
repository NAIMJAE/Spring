package kr.co.ch08.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.ch08.dto.UserDTO;
import kr.co.ch08.sevice.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@AllArgsConstructor
@Controller
@SessionAttributes("sessUser")
public class User3Controller {

    private final UserService service;

    @GetMapping("/user3/login")
    public String login(){
        log.info("user3/login - Get");

        return "/user3/login";
    }

    @PostMapping("/user3/login")
    public String login(UserDTO userDTO, Model model){
        log.info("user3/login - Post");

        UserDTO user = service.selectUser(userDTO);

        if (user != null){
            // 어노테이션으로 선언된 session 객체(sessUser) 참조
            model.addAttribute("sessUser", user);
            return "redirect:/user3/success";
        }else {
            return "redirect:/user3/login?success=100";
        }
    }

    @GetMapping("/user3/success")
    public String success(){
        log.info("user3/success - Get");

        return "/user3/success";
    }

    @GetMapping("/user3/logout")
    public String logout(SessionStatus status){
        log.info("user3/logout - Get");

        // @SessionAttribute로 설정한 세션 해제
        status.setComplete();

        return "redirect:/user3/login?success=200";
    }
}
