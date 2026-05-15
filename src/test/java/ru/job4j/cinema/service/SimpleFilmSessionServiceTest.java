package ru.job4j.cinema.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.DbTestHelper;
import ru.job4j.cinema.Repository.Sql2oFilmSessionRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SimpleFilmSessionServiceTest {

    private static SimpleFilmSessionService service;

    @BeforeAll
    public static void initService() {
        service = new SimpleFilmSessionService(
                new Sql2oFilmSessionRepository(DbTestHelper.getSql2o()));
    }

    @AfterEach
    public void clear() {
        DbTestHelper.clearAll();
    }

    @Test
    public void whenFindByIdThenGetSession() {
        var sessionId = createSession();
        var found = service.findById(sessionId);
        assertThat(found).isPresent();
        assertThat(found.get().getPrice()).isEqualTo(450);
    }

    @Test
    public void whenFindAllThenGetSessions() {
        createSession();
        assertThat(service.findAll().size()).isEqualTo(1);
    }

    private int createSession() {
        var fileId = DbTestHelper.insertFile("f.jpg", "posters/f.jpg");
        var genreId = DbTestHelper.insertGenre("Драма");
        var filmId = DbTestHelper.insertFilm("Фильм", "Описание", 2010,
                genreId, 12, 100, fileId);
        var hallId = DbTestHelper.insertHall("Зал", 10, 10, "Описание");
        var start = LocalDateTime.of(2026, 5, 20, 19, 0);
        return DbTestHelper.insertFilmSession(filmId, hallId, start, start.plusHours(2), 450);
    }
}
