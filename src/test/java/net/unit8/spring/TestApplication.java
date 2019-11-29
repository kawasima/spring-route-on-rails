package net.unit8.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@EnableAutoConfiguration
public class TestApplication {
    @Bean
    public Rails2LikeUrlMapping mapping(ApplicationContext context) {
        var mapping = new Rails2LikeUrlMapping("routes-1.xml");
        mapping.setOrder(Integer.MAX_VALUE - 2);
        mapping.setApplicationContext(context);
        return mapping;
    }

    @Bean("admin.User")
    public AdminUserController hogeController() {
        return new AdminUserController();
    }

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
