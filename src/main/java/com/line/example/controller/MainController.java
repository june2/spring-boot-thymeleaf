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
public class MainController {

    private final UserService userService;

    @GetMapping
    public String main(Authentication authentication, Model model) {
        if (authentication == null) { return "redirect:/login"; }

        model.addAttribute("users", userService.getList());
        model.addAttribute("auth", userService.getUser(authentication.getName()));

        return "index";
    }

    @PostMapping
    public String main(Authentication authentication, Model model, User user) {
        if (authentication == null) { return "redirect:/login"; }

        if (!userService.hasUser(user.getName())) {
            userService.addUser(user);
        }

        model.addAttribute("users", userService.getList());
        model.addAttribute("auth", userService.getUser(authentication.getName()));

        return "index";
    }

    // Login form
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Login form with error
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
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
