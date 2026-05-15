package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.DbTestHelper;
import ru.job4j.cinema.Repository.Sql2oUserRepository;
import ru.job4j.cinema.model.User;

import static java.util.Optional.empty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oUserRepositoryTest {

    private static Sql2oUserRepository repository;

    @BeforeAll
    public static void initRepository() {
        repository = new Sql2oUserRepository(DbTestHelper.getSql2o());
    }

    @AfterEach
    public void clearUsers() {
        DbTestHelper.clearAll();
    }

    @Test
    public void whenSaveThenGetSame() {
        var userToSave = new User(0, "Иван Иванов", "test@mail.ru", "password123");
        var savedUser = repository.save(userToSave);

        assertThat(savedUser).isPresent();
        assertThat(savedUser.get().getId()).isGreaterThan(0);
        assertThat(savedUser.get().getEmail()).isEqualTo("test@mail.ru");
        assertThat(savedUser.get().getFullName()).isEqualTo("Иван Иванов");

        var found = repository.findByEmailAndPassword("test@mail.ru", "password123");
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(savedUser.get().getId());
    }

    @Test
    public void whenDontSaveThenNothingFound() {
        var found = repository.findByEmailAndPassword("nonexistent@mail.ru", "any");
        assertThat(found).isEqualTo(empty());
    }

    @Test
    public void whenSaveUserWithSameEmailThenReturnEmpty() {
        var user1 = new User(0, "Первый", "duplicate@mail.ru", "pass1");
        var user2 = new User(0, "Второй", "duplicate@mail.ru", "pass2");

        var savedUser1 = repository.save(user1);
        var savedUser2 = repository.save(user2);

        assertThat(savedUser1).isPresent();
        assertThat(savedUser2).isEmpty();
    }

    @Test
    public void whenFindByEmailWithWrongPasswordThenEmpty() {
        repository.save(new User(0, "Иван", "test@mail.ru", "correctPass"));
        var found = repository.findByEmailAndPassword("test@mail.ru", "wrongPass");
        assertThat(found).isEmpty();
    }
}
