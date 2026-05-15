package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.DbTestHelper;
import ru.job4j.cinema.Repository.Sql2oTicketRepository;
import ru.job4j.cinema.Repository.Sql2oUserRepository;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Sql2oTicketRepositoryTest {

    private static Sql2oTicketRepository repository;
    private static Sql2oUserRepository userRepository;

    @BeforeAll
    public static void initRepository() {
        var sql2o = DbTestHelper.getSql2o();
        repository = new Sql2oTicketRepository(sql2o);
        userRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    public void clear() {
        DbTestHelper.clearAll();
    }

    @Test
    public void whenSaveThenFindById() {
        var sessionId = createSession();
        var userId = saveUser();
        var ticket = new Ticket(0, sessionId, 3, 5, userId);
        var saved = repository.save(ticket);
        assertThat(saved.getId()).isGreaterThan(0);
        var found = repository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getRowNumber()).isEqualTo(3);
        assertThat(found.get().getPlaceNumber()).isEqualTo(5);
    }

    @Test
    public void whenSaveThenSeatIsTaken() {
        var sessionId = createSession();
        var userId = saveUser();
        repository.save(new Ticket(0, sessionId, 1, 1, userId));
        assertThat(repository.isSeatTaken(sessionId, 1, 1)).isTrue();
        assertThat(repository.isSeatTaken(sessionId, 1, 2)).isFalse();
    }

    @Test
    public void whenSaveSameSeatThenThrow() {
        var sessionId = createSession();
        var userId = saveUser();
        repository.save(new Ticket(0, sessionId, 2, 2, userId));
        assertThrows(RuntimeException.class, () ->
                repository.save(new Ticket(0, sessionId, 2, 2, userId)));
    }

    @Test
    public void whenFindBySessionIdThenGetTickets() {
        var sessionId = createSession();
        var userId = saveUser();
        repository.save(new Ticket(0, sessionId, 1, 1, userId));
        repository.save(new Ticket(0, sessionId, 1, 2, userId));
        assertThat(repository.findBySessionId(sessionId).size()).isEqualTo(2);
    }

    private int saveUser() {
        var user = userRepository.save(
                new User(0, "Иван", "ticket@mail.ru", "pass"));
        return user.get().getId();
    }

    private int createSession() {
        var fileId = DbTestHelper.insertFile("f.jpg", "posters/f.jpg");
        var genreId = DbTestHelper.insertGenre("Драма");
        var filmId = DbTestHelper.insertFilm("Фильм", "Описание", 2010,
                genreId, 12, 100, fileId);
        var hallId = DbTestHelper.insertHall("Зал", 10, 10, "Описание");
        var start = java.time.LocalDateTime.of(2026, 5, 15, 18, 0);
        return DbTestHelper.insertFilmSession(filmId, hallId, start, start.plusHours(2), 300);
    }
}
