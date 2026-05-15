package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.DbTestHelper;
import ru.job4j.cinema.Repository.Sql2oFilmRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oFilmRepositoryTest {

    private static Sql2oFilmRepository repository;

    @BeforeAll
    public static void initRepository() {
        repository = new Sql2oFilmRepository(DbTestHelper.getSql2o());
    }

    @AfterEach
    public void clear() {
        DbTestHelper.clearAll();
    }

    @Test
    public void whenFindByIdThenGetFilm() {
        var fileId = DbTestHelper.insertFile("f.jpg", "posters/f.jpg");
        var genreId = DbTestHelper.insertGenre("Драма");
        var filmId = DbTestHelper.insertFilm("Начало", "Описание", 2010,
                genreId, 12, 148, fileId);
        var found = repository.findById(filmId);
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Начало");
        assertThat(found.get().getGenreId()).isEqualTo(genreId);
    }

    @Test
    public void whenFindByWrongIdThenEmpty() {
        assertThat(repository.findById(999)).isEmpty();
    }

    @Test
    public void whenFindAllThenGetAllFilms() {
        var fileId = DbTestHelper.insertFile("f.jpg", "posters/f.jpg");
        var genreId = DbTestHelper.insertGenre("Драма");
        DbTestHelper.insertFilm("Фильм 1", "Описание 1", 2010, genreId, 12, 100, fileId);
        DbTestHelper.insertFilm("Фильм 2", "Описание 2", 2011, genreId, 16, 120, fileId);
        assertThat(repository.findAll().size()).isEqualTo(2);
    }
}
