package ru.job4j.cinema.Repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.FilmSession;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Repository
public class Sql2oFilmSessionRepository implements FilmSessionRepository{

    private static final Logger LOG = LoggerFactory.getLogger(Sql2oFilmSessionRepository.class);
    private final Sql2o sql2o;

    public Sql2oFilmSessionRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<FilmSession> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM film_sessions WHERE id = :id");
            query.addParameter("id", id);
            var filmSession = query.setColumnMappings(FilmSession.COLUMN_MAPPING).executeAndFetchFirst(FilmSession.class);
            return Optional.ofNullable(filmSession);
        } catch (Exception e) {
            LOG.error("Ошибка при поиске сеанса фильма по id: {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public Collection<FilmSession> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM film_sessions");
            return query.setColumnMappings(FilmSession.COLUMN_MAPPING).executeAndFetch(FilmSession.class);
        } catch (Exception e) {
            LOG.error("Ошибка при получении списка всех сеансов фильмов", e);
            return Collections.emptyList();
        }
    }
}
