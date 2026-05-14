package ru.job4j.cinema.Repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Genre;

import java.util.Optional;

@Repository
public class Sql2oGenreRepository implements GenreRepository {

    private static final Logger LOG = LoggerFactory.getLogger(Sql2oGenreRepository.class);
    private final Sql2o sql2o;

    public Sql2oGenreRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Genre> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM genres WHERE id = :id");
            query.addParameter("id", id);
            var genre = query.setColumnMappings(Genre.COLUMN_MAPPING).executeAndFetchFirst(Genre.class);
            return Optional.ofNullable(genre);
        } catch (Exception e) {
            LOG.error("Ошибка при поиске жанра фильма по id: {}", id, e);
            return Optional.empty();
        }
    }
}
