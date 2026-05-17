package ru.job4j.cinema.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.DbTestHelper;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.ticket.Sql2oTicketRepository;
import ru.job4j.cinema.repository.user.Sql2oUserRepository;
import ru.job4j.cinema.service.ticket.SimpleTicketService;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SimpleTicketServiceTest {

    private static SimpleTicketService service;
    private static Sql2oUserRepository userRepository;

    @BeforeAll
    public static void initService() {
        var sql2o = DbTestHelper.getSql2o();
        service = new SimpleTicketService(new Sql2oTicketRepository(sql2o));
        userRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    public void clear() {
        DbTestHelper.clearAll();
    }

    @Test
    public void whenSaveThenTicketPresent() {
        var sessionId = createSession();
        var userId = saveUser();
        var saved = service.save(new Ticket(0, sessionId, 4, 7, userId));
        assertThat(saved).isPresent();
        assertThat(saved.get().getId()).isGreaterThan(0);
    }

    @Test
    public void whenSeatTakenThenSaveEmpty() {
        var sessionId = createSession();
        var userId = saveUser();
        service.save(new Ticket(0, sessionId, 1, 1, userId));
        var second = service.save(new Ticket(0, sessionId, 1, 1, userId));
        assertThat(second).isEmpty();
    }

    @Test
    public void whenFindBySessionIdThenGetTickets() {
        var sessionId = createSession();
        var userId = saveUser();
        service.save(new Ticket(0, sessionId, 2, 3, userId));
        assertThat(service.findBySessionId(sessionId).size()).isEqualTo(1);
    }

    private int saveUser() {
        return userRepository.save(new User(0, "Иван", "buyer@mail.ru", "pass"))
                .get().getId();
    }

    private int createSession() {
        var fileId = DbTestHelper.insertFile("f.jpg", "posters/f.jpg");
        var genreId = DbTestHelper.insertGenre("Драма");
        var filmId = DbTestHelper.insertFilm("Фильм", "Описание", 2010,
                genreId, 12, 100, fileId);
        var hallId = DbTestHelper.insertHall("Зал", 10, 10, "Описание");
        var start = LocalDateTime.of(2026, 5, 15, 18, 0);
        return DbTestHelper.insertFilmSession(filmId, hallId, start, start.plusHours(2), 300);
    }
}
