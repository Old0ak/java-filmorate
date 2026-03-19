package ru.yandex.practicum.filmorate.controllerTests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class ControllerByTest {

    protected static String PATH;

    @Autowired
    protected MockMvc mockMvc;

    protected String getContentFromFile(String fileName) {
        try {
             return Files.readString(ResourceUtils.getFile("classpath:" + fileName).toPath(),
                     StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new RuntimeException("Не открывается файл", exception);
        }
    }
}
