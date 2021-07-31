package com.line.example;

import com.line.example.annotation.BasicTestAnnotations;
import com.line.example.controller.MainController;
import com.line.example.repository.UserRepository;
import com.line.example.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;


@BasicTestAnnotations
@DisplayName("Boot, Security, JPA example application basic tests")
class BootSecurityJPAExampleApplicationTests {

    @Autowired private MainController mainController;
    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;

    @Test
    @DisplayName("Simple context loading test")
    void contextLoads() {
        // Given

        // When & Then
        assertThat(mainController).isNotNull();
        assertThat(userService).isNotNull();
        assertThat(userRepository).isNotNull();
    }

}
