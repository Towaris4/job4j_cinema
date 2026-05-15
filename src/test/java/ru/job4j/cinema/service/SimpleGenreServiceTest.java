package ru.job4j.cinema.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.DbTestHelper;
import ru.job4j.cinema.Repository.Sql2oGenreRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SimpleGenreServiceTest {

    private static SimpleGenreService service;

    @BeforeAll
    public static void initService() {
        service = new SimpleGenreService(new Sql2oGenreRepository(DbTestHelper.getSql2o()));
    }

    @AfterEach
    public void clear() {
        DbTestHelper.clearAll();
    }

    @Test
    public void whenFindByIdThenGetGenre() {
        var genreId = DbTestHelper.insertGenre("Фантастика");
        var found = service.findById(genreId);
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Фантастика");
    }

    @Test
    public void whenFindAllThenGetGenres() {
        DbTestHelper.insertGenre("Драма");
        DbTestHelper.insertGenre("Комедия");
        assertThat(service.findAll().size()).isEqualTo(2);
    }
}
