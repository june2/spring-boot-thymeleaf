package com.line.example.service;

import com.line.example.domain.SecurityUser;
import com.line.example.domain.User;
import com.line.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<User> getList() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUser(String name) {
        return userRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public boolean hasUser(String name) {
        return userRepository.existsByName(name);
    }

    @Transactional
    public void addUser(User user) {
        if (null == user.getName() || null == user.getPassword()) {
            throw new IllegalArgumentException("User input cannot be null.");
        }
        if(hasUser(user.getName())) {
            throw new IllegalArgumentException("Duplicated name");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new SecurityUser(userRepository.findByName(username));
    }
}
