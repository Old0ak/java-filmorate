package ru.yandex.practicum.filmorate.controllerTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.filmorate.controller.UserController;

public class UserControllerTest extends ControllerByTest {

    public UserControllerTest() {
        PATH = "/users";
    }

    @Autowired
    private UserController userController;

    @Test
    void create() throws Exception {
        String requestBody = getContentFromFile("create/request/user/user.json");
        String responseBody = getContentFromFile("create/response/user/user.json");

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseBody));
    }

    @Test
    void createFailRequestNull() throws Exception {
        String requestBody = getContentFromFile("create/request/user/failRequestNull.json");

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createFailEmail() throws Exception {
        String requestBody = getContentFromFile("create/request/user/failEmail.json");

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createFailEmailNull() throws Exception {
        String requestBody = getContentFromFile("create/request/user/failEmailNull.json");

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createFailLogin() throws Exception {
        String requestBody = getContentFromFile("create/request/user/failLogin.json");

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createFailLoginNull() throws Exception {
        String requestBody = getContentFromFile("create/request/user/failLoginNull.json");

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createNameNull() throws Exception {
        String requestBody = getContentFromFile("create/request/user/userNameNull.json");
        String responseBody = getContentFromFile("create/response/user/userNameByLogin.json");

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.content().json(responseBody))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createFailBirthDay() throws Exception {
        String requestBody = getContentFromFile("create/request/user/failBirthday.json");

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateFailId() throws Exception {
        String requestBody = getContentFromFile("create/request/user/user.json");
        String responseBody = getContentFromFile("create/response/user/user.json");

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.content().json(responseBody))
                .andExpect(MockMvcResultMatchers.status().isOk());

        String requestBody2 = getContentFromFile(
                "create/request/user/failUserId.json");

        mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody2))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
