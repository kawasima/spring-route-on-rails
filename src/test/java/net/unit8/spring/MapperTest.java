package net.unit8.spring;

import net.unit8.spring.controller.AdminUserController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

class MapperTest {
    @Test
    void test() {
        final ConfigurableApplicationContext context = SpringApplication.run(TestApplication.class, new String[]{});
        AdminUserController hogeController = context.getBean("admin.User", AdminUserController.class);
        assertThat(hogeController).isNotNull();
    }
}
