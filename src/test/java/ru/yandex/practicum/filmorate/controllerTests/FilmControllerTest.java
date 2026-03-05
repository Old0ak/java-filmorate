package ru.yandex.practicum.filmorate.controllerTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.filmorate.controller.FilmController;

public class FilmControllerTest extends ControllerByTest {

    public FilmControllerTest() {
        PATH = "/films";
    }

    @Autowired
    private FilmController filmController;

    @Test
    void create() throws Exception {
        String requestBody = getContentFromFile("create/request/film/film.json");
        String responseBody = getContentFromFile("create/response/film/film.json");

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseBody));
    }

    @Test
    void createFailRequestNull() throws Exception {
        String requestBody = getContentFromFile("create/request/film/failRequestNull.json");

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createFailName() throws Exception {
        String requestBody = getContentFromFile("create/request/film/failNameFilm.json");

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createFailDescription() throws Exception {
        String requestBody = getContentFromFile("create/request/film/failDescription.json");

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createFailReleaseDate() throws Exception {
        String requestBody = getContentFromFile("create/request/film/failReleaseDate.json");

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createFailDuration() throws Exception {
        String requestBody = getContentFromFile("create/request/film/failDuration.json");

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateFailId() throws Exception {
        String requestBody = getContentFromFile("create/request/film/film.json");
        String responseBody = getContentFromFile("create/response/film/film.json");

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.content().json(responseBody))
                .andExpect(MockMvcResultMatchers.status().isOk());

        String requestBody2 = getContentFromFile("create/request/film/failFilmId.json");

        mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody2))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
