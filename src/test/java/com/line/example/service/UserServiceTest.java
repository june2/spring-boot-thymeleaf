package com.line.example.service;

import com.line.example.annotation.TransactionalBasicTestAnnotations;
import com.line.example.domain.User;
import com.line.example.repository.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static com.line.example.fixture.UserFixture.constructTestUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


@TransactionalBasicTestAnnotations
@DisplayName("User service logic tests")
class UserServiceTest {

    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @BeforeEach
    void before() {
        userRepository.deleteAll(); // TODO: To remove sample data from classpath:/resources/data.sql. This is to be removed when the sample data is removed.
    }


    @Test
    @DisplayName("Get User list containing single User")
    void getList() {
        // Given
        String name = "test";
        String password = "password";
        userRepository.save(constructTestUser(name, password));

        // When
        List<User> list = userService.getList();

        // Then
        assertThat(list).hasSize(1);
        assertThat(list.get(0))
                .hasFieldOrPropertyWithValue("name", name)
                .hasFieldOrPropertyWithValue("password", password);
    }

    @Test
    @DisplayName("Get a User")
    void getUser() {
        // Given
        String name = "test";
        String password = "pw";
        userRepository.save(constructTestUser(name, password));

        // When
        User user = userService.getUser(name);

        // Then
        assertThat(user)
                .hasFieldOrPropertyWithValue("name", name)
                .hasFieldOrPropertyWithValue("password", password);
    }

    @Test
    @DisplayName("Add a normal User")
    void addUser() {
        // Given
        User user = constructTestUser("test", "pw");

        // When
        userService.addUser(user);
        List<User> result = userRepository.findAll();

        // Then
        assertThat(result)
                .hasSize(1)
                .contains(user);
    }

    @Test
    @DisplayName("Check if the password encryption has done well")
    void checkAddedUserPassword() {
        // Given
        String name = "test";
        String password = "pw";
        User user = constructTestUser(name, password);

        // When
        userService.addUser(user);
        User result = userService.getUser(name);

        // Then
        assertThat(passwordEncoder.matches(password, result.getPassword())).isTrue();
        assertThat(result.getPassword()).contains("{bcrypt}");

    }

    @Test
    @DisplayName("(Exception) Add a User with a missing field")
    void addIncompleteUser() {
        // Given
        User user = constructTestUser(null, "pw2");

        // When
        Throwable thrown = catchThrowable(() -> userService.addUser(user));

        // Then
        assertThat(thrown)
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasStackTraceContaining("name");
    }

    @Test
    @DisplayName("(Exception) Add a null value")
    void addNullUser() {
        // Given

        // When
        Throwable thrown = catchThrowable(() -> userService.addUser(null));

        // Then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("null");
    }

    @Test
    @DisplayName("(Exception) Add an existing user")
    void addExistingUser() {
        // Given
        String name = "test";
        String password = "pw";
        User user = constructTestUser(name, password);
        userRepository.save(constructTestUser(name, password));

        // When
        Throwable thrown = catchThrowable(() -> userService.addUser(user));

        // Then
        assertThat(thrown)
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasCauseExactlyInstanceOf(ConstraintViolationException.class)
                .hasStackTraceContaining("Unique index or primary key violation");
    }
}
