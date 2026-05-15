package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.DbTestHelper;
import ru.job4j.cinema.Repository.Sql2oGenreRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oGenreRepositoryTest {

    private static Sql2oGenreRepository repository;

    @BeforeAll
    public static void initRepository() {
        repository = new Sql2oGenreRepository(DbTestHelper.getSql2o());
    }

    @AfterEach
    public void clear() {
        DbTestHelper.clearAll();
    }

    @Test
    public void whenFindByIdThenGetGenre() {
        var genreId = DbTestHelper.insertGenre("Драма");
        var found = repository.findById(genreId);
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Драма");
    }

    @Test
    public void whenFindByWrongIdThenEmpty() {
        assertThat(repository.findById(999)).isEmpty();
    }

    @Test
    public void whenFindAllThenGetAllGenres() {
        DbTestHelper.insertGenre("Драма");
        DbTestHelper.insertGenre("Комедия");
        assertThat(repository.findAll().size()).isEqualTo(2);
    }
}
