package kr.co.ch08.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.ch08.dto.UserDTO;
import kr.co.ch08.sevice.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@Controller
public class User2Controller {

    private final UserService service;

    @GetMapping("/user2/login")
    public String login(){
        log.info("user2/login - Get");
        return "/user2/login";
    }

    @PostMapping("/user2/login")
    public String login(HttpSession session, UserDTO userDTO){
        log.info("user2/login - Post");
        UserDTO user = service.selectUser(userDTO);

        if(user != null){
            // 회원이 맞는 경우
            log.info("성공");
            session.setAttribute("sessUser", user);
            return "redirect:/user2/success";
        }else{
            // 회원이 아닌 경우
            return "redirect:/user2/login?success=100";
        }
    }

    @GetMapping("/user2/success")
    public String success(HttpSession session, HttpServletResponse resp){
        log.info("user2/success - Get");

        UserDTO sessUser = (UserDTO) session.getAttribute("sessUser");

        // 쿠키 생성
        Cookie cookie = new Cookie("username", sessUser.getUid());
        cookie.setMaxAge(60 * 3);  // 3분
        cookie.setPath("/");     // 쿠키 경로
        resp.addCookie(cookie);

        return "/user2/success";
    }

    @GetMapping("/user2/result")
    public String result(@CookieValue("JSESSIONID") String jsessionid,
                         @CookieValue("username") String username,
                         Model model){

        model.addAttribute("jsessionid", jsessionid);
        model.addAttribute("username", username);

        return "/user2/result";
    }

    @GetMapping("/user2/logout")
    public String logout(HttpSession session){
        log.info("user2/logout - Get");

        session.removeAttribute("sessUser");
        session.invalidate();

        return "redirect:/user2/login?success=200";
    }
}
