package ru.job4j.cinema.repository.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.File;

import java.util.Optional;

@Repository
public class Sql2oFileRepository implements FileRepository {

    private static final Logger LOG = LoggerFactory.getLogger(Sql2oFileRepository.class);
    private final Sql2o sql2o;

    public Sql2oFileRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<File> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM files WHERE id = :id");
            var file = query.addParameter("id", id).executeAndFetchFirst(File.class);
            return Optional.ofNullable(file);
        } catch (Exception e) {
            LOG.error("Не удалось найти файл по id={}", id, e);
        }
        return Optional.empty();
    }
}
