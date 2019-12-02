package net.unit8.spring.controller;

import org.springframework.stereotype.Controller;

@Controller("blog")
public class BlogController {
    public String post() {
        return "/view/posted";
    }
}
