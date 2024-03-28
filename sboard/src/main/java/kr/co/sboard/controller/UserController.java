package kr.co.sboard.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.sboard.config.AppInfo;
import kr.co.sboard.dto.TermsDTO;
import kr.co.sboard.dto.UserDTO;
import kr.co.sboard.entity.User;
import kr.co.sboard.security.MyUserDetails;
import kr.co.sboard.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public String terms(TermsDTO termsDTO, Model model){
        log.info("/user/terms - POST");
        log.info("termsDTO : " + termsDTO.toString());
        String sms = termsDTO.getSms();
        model.addAttribute("sms", sms);
        return "/user/register";
    }

    @GetMapping("/user/register")
    public String register(){
        log.info("/user/register - GET");

        return "/user/register";
    }
/////////////////////////////////////////////////////////////////////////////////////////////
    // 소셜 회원가입
    @GetMapping("/user/SNSregister")
    public String SNSregister(Model model){
        log.info("/user/SNSregister - GET");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof MyUserDetails) {
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            // 사용자 정보를 모델에 추가
            model.addAttribute("user", userDetails.getUser());
        }

        return "/user/SNSregister";
    }
////////////////////////////////////////////////////////////////////////////////////////////////
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
        if(result == 0 && type.equals("email")){
            log.info("email : " + value);
            userService.sendEmailCode(session, value);
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return ResponseEntity.ok().body(resultMap);
    }
    // 아이디 찾기 이메일 인증
    @ResponseBody
    @PostMapping("/user/sendEmail/{email}/{name}")
    public ResponseEntity<?> sendEmailForUid(HttpSession session, @PathVariable("email") String email, @PathVariable("name") String name){
        log.info("이름");
        UserDTO user = userService.selectUserForNameAndEmail(name, email);
        if (user != null){
            return userService.sendEmailCode(session, email);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
    }

    // 비밀번호 찾기 이메일 인증
    @ResponseBody
    @GetMapping("/user/sendEmailForPw/{email}/{uid}")
    public ResponseEntity<?> sendEmailForPw(HttpSession session, @PathVariable("email") String email, @PathVariable("uid") String uid){
        log.info("아이디");
        UserDTO user = userService.selectUserForUidAndEmail(uid, email);
        if (user != null){
            return userService.sendEmailCode(session, email);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
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

    // 아이디 찾기
    @GetMapping("/user/findId")
    public String findId(){
        log.info("/user/findId");

        return "/user/findId";
    }

    // 아이디 찾기 결과
    @PostMapping("/user/findIdResult")
    public String findIdResult(UserDTO userDTO, Model model){
        log.info("/user/findIdResult");

        UserDTO user = userService.selectUserForNameAndEmail(userDTO.getName(), userDTO.getEmail());
        model.addAttribute("user", user);
        return "/user/findIdResult";
    }

    // 비밀번호 찾기
    @GetMapping("/user/findPassword")
    public String findPassword(){
        log.info("/user/findPassword");

        return "/user/findPassword";
    }

    // 비밀번호 변경
    @PostMapping("/user/findPassword")
    public String findPassword(UserDTO userDTO, Model model){
        log.info("/user/findPassword");
        log.info(userDTO.toString());
        model.addAttribute("user", userDTO.getUid());
        return "/user/findPasswordChange";
    }

    // 변경된 비밀번호 저장
    @PostMapping("/user/findPasswordChange")
    public String findPasswordChange(UserDTO userDTO, Model model){
        log.info("/user/findPasswordChange");
        log.info(userDTO.toString());

        int result = userService.updateUserPassword(userDTO);

        if (result > 0){
            return "redirect:/user/login";
        }else {
            model.addAttribute("user", userDTO.getUid());
            return "/user/findPasswordChange";
        }
    }
}
