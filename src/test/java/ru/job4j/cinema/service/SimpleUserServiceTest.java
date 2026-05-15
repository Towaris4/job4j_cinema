package ru.job4j.cinema.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.DbTestHelper;
import ru.job4j.cinema.Repository.Sql2oUserRepository;
import ru.job4j.cinema.model.User;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SimpleUserServiceTest {

    private static SimpleUserService service;

    @BeforeAll
    public static void initService() {
        service = new SimpleUserService(new Sql2oUserRepository(DbTestHelper.getSql2o()));
    }

    @AfterEach
    public void clear() {
        DbTestHelper.clearAll();
    }

    @Test
    public void whenSaveThenFindByEmailAndPassword() {
        var user = new User(0, "Иван", "user@mail.ru", "secret");
        var saved = service.save(user);
        assertThat(saved).isPresent();
        var found = service.findByEmailAndPassword("user@mail.ru", "secret");
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.get().getId());
    }

    @Test
    public void whenDuplicateEmailThenSaveEmpty() {
        service.save(new User(0, "Первый", "dup@mail.ru", "pass1"));
        var second = service.save(new User(0, "Второй", "dup@mail.ru", "pass2"));
        assertThat(second).isEmpty();
    }
}
