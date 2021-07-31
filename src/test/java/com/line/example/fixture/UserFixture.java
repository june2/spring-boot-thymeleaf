package com.line.example.fixture;

import com.line.example.domain.User;


/**
 * A fixture class for creating {@link User} test data.
 */
public class UserFixture {

    /**
     * Creates an instance from {@link User}.
     *
     * @param name User login ID
     * @param password User password
     * @return A new {@link User} object
     */
    public static User constructTestUser(String name, String password) {
        return User.builder()
                .name(name)
                .password(password)
                .build();
    }
}
