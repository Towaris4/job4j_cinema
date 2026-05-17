package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.service.file.FileService;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileController.class)
class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @Test
    public void whenFileFoundThenReturnContent() throws Exception {
        var dto = new FileDto("poster.jpg", new byte[]{1, 2, 3});
        when(fileService.getFileById(1)).thenReturn(Optional.of(dto));
        mockMvc.perform(get("/files/1"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(new byte[]{1, 2, 3}));
    }

    @Test
    public void whenFileNotFoundThenNotFound() throws Exception {
        when(fileService.getFileById(99)).thenReturn(Optional.empty());
        mockMvc.perform(get("/files/99"))
                .andExpect(status().isNotFound());
    }
}
