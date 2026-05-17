package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.DbTestHelper;
import ru.job4j.cinema.repository.hall.Sql2oHallRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oHallRepositoryTest {

    private static Sql2oHallRepository repository;

    @BeforeAll
    public static void initRepository() {
        repository = new Sql2oHallRepository(DbTestHelper.getSql2o());
    }

    @AfterEach
    public void clear() {
        DbTestHelper.clearAll();
    }

    @Test
    public void whenFindByIdThenGetHall() {
        var hallId = DbTestHelper.insertHall("Зал 1", 10, 15, "Стандарт");
        var found = repository.findById(hallId);
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Зал 1");
        assertThat(found.get().getRowCount()).isEqualTo(10);
        assertThat(found.get().getPlaceCount()).isEqualTo(15);
    }

    @Test
    public void whenFindByWrongIdThenEmpty() {
        assertThat(repository.findById(999)).isEmpty();
    }

    @Test
    public void whenFindAllThenGetAllHalls() {
        DbTestHelper.insertHall("Зал 1", 5, 5, "A");
        DbTestHelper.insertHall("Зал 2", 8, 8, "B");
        assertThat(repository.findAll().size()).isEqualTo(2);
    }
}
