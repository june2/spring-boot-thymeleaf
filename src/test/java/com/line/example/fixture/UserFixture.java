package com.line.example.fixture;

import com.line.example.domain.User;


/**
 * A fixture class for creating {@link User} test data.
 */
public class UserFixture {

    public static User constructTestUser(String name, String password) {
        return User.builder()
                .name(name)
                .password(password)
                .build();
    }
}
