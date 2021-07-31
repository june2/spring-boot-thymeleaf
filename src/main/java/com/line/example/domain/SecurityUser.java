package com.line.example.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
@Setter
public class SecurityUser extends User {
    private static final long serialVersionUID = 1L;

    public SecurityUser(com.line.example.domain.User user) {
        super(user.getName(), user.getPassword(), Collections.emptyList());
    }
}
