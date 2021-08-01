package com.line.example.controller;

import com.line.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@RequestMapping("/")
@Controller
public class UserController {

    private final UserService userService;

}
