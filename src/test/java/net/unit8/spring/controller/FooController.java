package net.unit8.spring.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController("foo")
public class FooController {
    public static String publicStatic() {
        return "publicStatic";
    }

    protected String protectedMethod() {
        return "protected";
    }

    private String privateMethod() {
        return "private";
    }
}
