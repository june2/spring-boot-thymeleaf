package com.line.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.line.example.fixture.UserFixture;
import com.line.example.annotation.TransactionalBasicTestAnnotations;
import com.line.example.domain.User;
import com.line.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TransactionalBasicTestAnnotations
@DisplayName("Spring Data Rest API Tests")
class SpringDataRestAPITests {

    @Autowired private WebApplicationContext context;
    @Autowired private UserRepository userRepository;
    @Autowired private ObjectMapper mapper;

    private MockMvc mvc;
    private String baseUrl;
    private String searchUrl;

    @BeforeEach
    void before() {
        baseUrl = "/api/users";
        searchUrl = baseUrl + "/search";
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Nested
    @TransactionalBasicTestAnnotations
    @DisplayName("for basic CRUD APIs")
    class ForBasicAPIs {
        @Test
        @DisplayName("[POST] Create a user")
        void createUserTest() throws Exception {
            // Given
            User user = UserFixture.constructTestUser("newUser", "pw");
            String body = mapper.writeValueAsString(user);

            // When & Then
            mvc.perform(
                    post(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body)
            )
                    .andExpect(status().isCreated());
        }

        @Test
        @DisplayName("[GET] Get a user info.")
        void showUserTest() throws Exception {
            // Given
            User savedUser = userRepository.save(UserFixture.constructTestUser("test", "pw"));
            long id = savedUser.getId();

            // When & Then
            mvc.perform(get(baseUrl + "/" + id))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("[GET] Get an entire user list.")
        void showUsersTest() throws Exception {
            // Given

            // When & Then
            mvc.perform(get(baseUrl))
                    .andExpect(status().isOk());
        }
    }


    @Nested
    @TransactionalBasicTestAnnotations
    @DisplayName("for user-custom(search) APIs")
    class ForSearchAPIs {
        @Test
        @DisplayName("[GET] Search a user with login_id")
        void showUsersTest() throws Exception {
            // Given
            String name = "testId";
            String password = "testPassword";
            User user = UserFixture.constructTestUser(name, password);
            userRepository.save(user);

            // When & Then
            mvc.perform(
                    get(searchUrl + "/findByLoginId")
                            .param("name", name)
            )
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString(password)));
        }

        @Test
        @DisplayName("[GET] Check if a user with login_id exists")
        void checkIfExists() throws Exception {
            // Given
            String name = "testId";
            User user = UserFixture.constructTestUser(name, "pw");
            userRepository.save(user);

            // When & Then
            mvc.perform(
                    get(searchUrl + "/existsByLoginId")
                            .param("name", name)
            )
                    .andExpect(status().isOk())
                    .andExpect(content().string(is(Boolean.TRUE.toString())));
        }
    }

}
