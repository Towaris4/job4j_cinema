package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void whenGetLoginThenReturnLoginPage() throws Exception {
        mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/login"));
    }

    @Test
    public void whenGetRegisterThenReturnRegisterPage() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/register"));
    }

    @Test
    public void whenLoginSuccessThenRedirectIndex() throws Exception {
        var user = new User(1, "Иван", "ivan@mail.ru", "pass");
        when(userService.findByEmailAndPassword("ivan@mail.ru", "pass"))
                .thenReturn(Optional.of(user));
        mockMvc.perform(post("/users/login")
                        .param("email", "ivan@mail.ru")
                        .param("password", "pass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));
    }

    @Test
    public void whenLoginFailThenReturnLoginPage() throws Exception {
        when(userService.findByEmailAndPassword(any(), any())).thenReturn(Optional.empty());
        mockMvc.perform(post("/users/login")
                        .param("email", "wrong@mail.ru")
                        .param("password", "wrong"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/login"));
    }

    @Test
    public void whenRegisterSuccessThenRedirectIndex() throws Exception {
        var user = new User(1, "Иван", "new@mail.ru", "pass");
        when(userService.save(any())).thenReturn(Optional.of(user));
        mockMvc.perform(post("/users/register")
                        .param("fullName", "Иван")
                        .param("email", "new@mail.ru")
                        .param("password", "pass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));
    }
}
