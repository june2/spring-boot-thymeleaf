package com.line.example.controller;

import com.line.example.domain.User;
import com.line.example.dto.UserDto;
import com.line.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@RequestMapping("/")
@Controller
public class UserController {

    private final UserService userService;

    // Login form
    @RequestMapping("/login.html")
    public String login() {
        return "login.html";
    }

    // Login form with error
    @RequestMapping("/login-error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login.html";
    }


    // 회원가입 페이지
    @GetMapping("/signup")
    public String signup() {
        return "/signup";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(UserDto userDto) {
        System.out.println(userDto);
        userService.addUser(User.builder().name(userDto.getName()).password(userDto.getPassword()).build());
        return "redirect:/user/login";
    }
}
