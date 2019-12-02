package net.unit8.spring.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("admin.User")
public class AdminUserController {
    public String index() {
        return "user list";
    }

    public String show(@PathVariable("id") String id) {
        return "id=" + id;
    }
}
