package kr.co.ch06.Controller;

import kr.co.ch06.DTO.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class MainController {

    // private Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping(value = {"/", "/index"})
    public String index(Model model){
        log.debug("index()...");

        String str1 = "Hello world";
        String str2 = "Hello Spring Boot";

        // Constructor(생성자) 초기화
        UserDTO user1 = new UserDTO("A101", "김유신", 23, "김해");
        log.debug(user1.toString());

        // setter 초기화
        UserDTO user2 = new UserDTO();
        user2.setUid("A102");
        user2.setName("김춘추");
        user2.setAge(43);
        user2.setAddr("경주");
        log.debug(user2.toString());

        // Builder 패턴 초기화
        UserDTO user3 = UserDTO.builder()
                            .uid("A103")
                            .name("장보고")
                            .age(33)
                            .addr("완도")
                            .build();

        log.debug(user3.toString());

        UserDTO user4 = null;

        // List 생성
        List<UserDTO> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        model.addAttribute("str1", str1);
        model.addAttribute("str2", str2);
        model.addAttribute("user1", user1);
        model.addAttribute("user2", user2);
        model.addAttribute("user3", user3);
        model.addAttribute("user4", user4);
        model.addAttribute("users", users);

        return "/index";
    }

    @GetMapping("/sub2/content1")
    public String content1(){
        return "/sub2/content1";
    }

    @GetMapping("/sub2/content2")
    public String content2(){
        return "/sub2/content2";
    }
}
