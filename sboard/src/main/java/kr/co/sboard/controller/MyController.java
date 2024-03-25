package kr.co.sboard.controller;

import kr.co.sboard.dto.UserDTO;
import kr.co.sboard.service.MyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MyController {

    private final MyService myService;

    @GetMapping("/my/setting")
    public String setting(String uid, Model model){
        log.info("/my/setting");
        log.info("uid : " + uid);
        UserDTO user = myService.selectUserForSetting(uid);
        model.addAttribute("user", user);
        return "/my/setting";
    }
}
