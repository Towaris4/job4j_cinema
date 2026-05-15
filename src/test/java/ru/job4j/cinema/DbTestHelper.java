package ru.job4j.cinema;

import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;

import java.time.LocalDateTime;
import java.util.Properties;

public final class DbTestHelper {

    private static Sql2o sql2o;

    private DbTestHelper() {
    }

    public static Sql2o getSql2o() {
        if (sql2o == null) {
            initSql2o();
        }
        return sql2o;
    }

    private static void initSql2o() {
        var properties = new Properties();
        try (var inputStream = DbTestHelper.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");
        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);
    }

    public static void clearAll() {
        try (var connection = getSql2o().open()) {
            connection.createQuery("DELETE FROM tickets").executeUpdate();
            connection.createQuery("DELETE FROM film_sessions").executeUpdate();
            connection.createQuery("DELETE FROM films").executeUpdate();
            connection.createQuery("DELETE FROM users").executeUpdate();
            connection.createQuery("DELETE FROM halls").executeUpdate();
            connection.createQuery("DELETE FROM genres").executeUpdate();
            connection.createQuery("DELETE FROM files").executeUpdate();
        }
    }

    public static int insertFile(String name, String path) {
        try (var connection = getSql2o().open()) {
            return connection.createQuery(
                    "INSERT INTO files (name, path) VALUES (:name, :path)", true)
                    .addParameter("name", name)
                    .addParameter("path", path)
                    .executeUpdate().getKey(Integer.class);
        }
    }

    public static int insertGenre(String name) {
        try (var connection = getSql2o().open()) {
            return connection.createQuery(
                    "INSERT INTO genres (name) VALUES (:name)", true)
                    .addParameter("name", name)
                    .executeUpdate().getKey(Integer.class);
        }
    }

    public static int insertHall(String name, int rowCount, int placeCount, String description) {
        try (var connection = getSql2o().open()) {
            return connection.createQuery(
                    "INSERT INTO halls (name, row_count, place_count, description) "
                            + "VALUES (:name, :rowCount, :placeCount, :description)", true)
                    .addParameter("name", name)
                    .addParameter("rowCount", rowCount)
                    .addParameter("placeCount", placeCount)
                    .addParameter("description", description)
                    .executeUpdate().getKey(Integer.class);
        }
    }

    public static int insertFilm(String name, String description, int year,
                                 int genreId, int minimalAge, int duration, int fileId) {
        try (var connection = getSql2o().open()) {
            return connection.createQuery(
                    "INSERT INTO films (name, description, \"year\", genre_id, "
                            + "minimal_age, duration_in_minutes, file_id) "
                            + "VALUES (:name, :description, :year, :genreId, "
                            + ":minimalAge, :duration, :fileId)", true)
                    .addParameter("name", name)
                    .addParameter("description", description)
                    .addParameter("year", year)
                    .addParameter("genreId", genreId)
                    .addParameter("minimalAge", minimalAge)
                    .addParameter("duration", duration)
                    .addParameter("fileId", fileId)
                    .executeUpdate().getKey(Integer.class);
        }
    }

    public static int insertFilmSession(int filmId, int hallId,
                                        LocalDateTime start, LocalDateTime end, int price) {
        try (var connection = getSql2o().open()) {
            return connection.createQuery(
                    "INSERT INTO film_sessions (film_id, halls_id, start_time, end_time, price) "
                            + "VALUES (:filmId, :hallId, :start, :end, :price)", true)
                    .addParameter("filmId", filmId)
                    .addParameter("hallId", hallId)
                    .addParameter("start", start)
                    .addParameter("end", end)
                    .addParameter("price", price)
                    .executeUpdate().getKey(Integer.class);
        }
    }
}
