package ru.job4j.cinema.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.DbTestHelper;
import ru.job4j.cinema.Repository.Sql2oFilmRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SimpleFilmServiceTest {

    private static SimpleFilmService service;

    @BeforeAll
    public static void initService() {
        service = new SimpleFilmService(new Sql2oFilmRepository(DbTestHelper.getSql2o()));
    }

    @AfterEach
    public void clear() {
        DbTestHelper.clearAll();
    }

    @Test
    public void whenFindByIdThenGetFilm() {
        var fileId = DbTestHelper.insertFile("f.jpg", "posters/f.jpg");
        var genreId = DbTestHelper.insertGenre("Драма");
        var filmId = DbTestHelper.insertFilm("Интерстеллар", "Космос", 2014,
                genreId, 12, 169, fileId);
        var found = service.findById(filmId);
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Интерстеллар");
    }

    @Test
    public void whenFindAllThenGetFilms() {
        var fileId = DbTestHelper.insertFile("f.jpg", "posters/f.jpg");
        var genreId = DbTestHelper.insertGenre("Драма");
        DbTestHelper.insertFilm("Фильм 1", "A", 2010, genreId, 12, 100, fileId);
        DbTestHelper.insertFilm("Фильм 2", "B", 2011, genreId, 16, 120, fileId);
        assertThat(service.findAll().size()).isEqualTo(2);
    }
}
