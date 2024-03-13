package kr.co.ch08.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@AllArgsConstructor
@Controller
public class MainController {


    @GetMapping(value = {"/", "/index"})
    public String index(){
        log.info("index - get");

        return "/index";
    }

    @GetMapping("/admin/index")
    public String adminIndex(){
        log.info("adminIndex - get");

        return "/admin/index";
    }

    @GetMapping("/manager/index")
    public String managerIndex(){
        log.info("managerIndex - get");

        return "/manager/index";
    }
}
