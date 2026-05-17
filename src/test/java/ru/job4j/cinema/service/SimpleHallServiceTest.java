package ru.job4j.cinema.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.DbTestHelper;
import ru.job4j.cinema.repository.hall.Sql2oHallRepository;
import ru.job4j.cinema.service.hall.SimpleHallService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SimpleHallServiceTest {

    private static SimpleHallService service;

    @BeforeAll
    public static void initService() {
        service = new SimpleHallService(new Sql2oHallRepository(DbTestHelper.getSql2o()));
    }

    @AfterEach
    public void clear() {
        DbTestHelper.clearAll();
    }

    @Test
    public void whenFindByIdThenGetHall() {
        var hallId = DbTestHelper.insertHall("VIP", 6, 8, "Комфорт");
        var found = service.findById(hallId);
        assertThat(found).isPresent();
        assertThat(found.get().getRowCount()).isEqualTo(6);
    }

    @Test
    public void whenFindAllThenGetHalls() {
        DbTestHelper.insertHall("Зал 1", 5, 5, "A");
        DbTestHelper.insertHall("Зал 2", 8, 8, "B");
        assertThat(service.findAll().size()).isEqualTo(2);
    }
}
