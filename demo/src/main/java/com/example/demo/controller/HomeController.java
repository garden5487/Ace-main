package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        // templates/home.html 파일을 찾아서 렌더링한다
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

}

