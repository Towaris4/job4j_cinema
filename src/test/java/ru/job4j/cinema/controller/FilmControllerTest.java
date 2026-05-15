package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.service.GenreService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(FilmController.class)
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmService filmService;

    @MockBean
    private GenreService genreService;

    @Test
    public void whenGetAllThenReturnFilmsList() throws Exception {
        var film = new Film(1, "Начало", "Описание", 2010, 1, 12, 148, 1);
        var genre = new Genre(1, "Фантастика");
        when(filmService.findAll()).thenReturn(List.of(film));
        when(genreService.findAll()).thenReturn(List.of(genre));
        mockMvc.perform(get("/films"))
                .andExpect(status().isOk())
                .andExpect(view().name("films/list"))
                .andExpect(model().attributeExists("films", "genres"));
    }
}
