package kr.co.sboard.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.sboard.config.AppInfo;
import kr.co.sboard.dto.TermsDTO;
import kr.co.sboard.dto.UserDTO;
import kr.co.sboard.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/user/login")
    public String login(@ModelAttribute("success") String success, Model model){
        // 매개변수 success에 @ModelAttribute 선언으로 View 참조할 수 있음
        log.info("/user/login - GET");
        return "/user/login";
    }

    @GetMapping("/user/terms")
    public String terms(Model model){
        log.info("/user/terms - GET");
        TermsDTO termsDTO = userService.selectTerms();
        model.addAttribute("termsDTO", termsDTO);
        return "/user/terms";
    }

    @PostMapping("/user/terms")
    public String terms(@RequestParam("agree3") String agree3, Model model){
        log.info("/user/terms - POST");
        String sms = null;
        if (agree3.equals("on")){
            model.addAttribute("sms", "Y");
        }else if (agree3 == null || agree3.isEmpty()) {
            model.addAttribute("sms", "N");
        }
        return "/user/register";
    }

    @GetMapping("/user/register")
    public String register(){
        log.info("/user/register - GET");

        return "/user/register";
    }

    // 회원가입 폼 전송 // 비밀번호 인코딩 잘못됨
    @PostMapping("/user/register")
    public String register(HttpServletRequest req, UserDTO userDTO){
        log.info("/user/register - POST");
        String regip = req.getRemoteAddr();
        userDTO.setRegip(regip);
        log.info(userDTO.toString());
        userService.insertUser(userDTO);
        return "redirect:/user/login?success=200";
    }

    // 중복 검사
    @ResponseBody
    @GetMapping("/userCheck/{type}/{value}")
    public ResponseEntity<?> usercheck(HttpSession session, @PathVariable("type") String type, @PathVariable("value") String value){

        int result = userService.checkUser(type, value);

        log.info("type : " + type + " | value : " + value + " | result : " + result);

        // 이메일 인증이면
        if(type.equals("email")){
            log.info("email : " + value);
            userService.sendEmailCode(session, value);
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return ResponseEntity.ok().body(resultMap);
    }

    // 이메일 인증 코드 검사
    @ResponseBody
    @GetMapping("/email/{code}")
    public ResponseEntity<?> checkEmail(HttpSession session, @PathVariable("code")  String code){

        String sessionCode = (String) session.getAttribute("code");

        if(sessionCode.equals(code)){
            // Json 생성
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", true);

            return ResponseEntity.ok().body(resultMap);
        }else{
            // Json 생성
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", false);

            return ResponseEntity.ok().body(resultMap);
        }
    }
}
