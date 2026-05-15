package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(FilmSessionController.class)
class FilmSessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmSessionService filmSessionService;

    @MockBean
    private FilmService filmService;

    @MockBean
    private HallService hallService;

    @Test
    public void whenGetAllThenReturnSessionsList() throws Exception {
        var start = LocalDateTime.of(2026, 5, 15, 18, 0);
        var session = new FilmSession(1, 1, 1, start, start.plusHours(2), 300);
        when(filmSessionService.findAll()).thenReturn(List.of(session));
        when(filmService.findAll()).thenReturn(List.of(new Film()));
        when(hallService.findAll()).thenReturn(List.of(new Hall()));
        mockMvc.perform(get("/filmSessions"))
                .andExpect(status().isOk())
                .andExpect(view().name("filmSessions/list"))
                .andExpect(model().attributeExists("filmSessions", "film", "hall"));
    }
}
