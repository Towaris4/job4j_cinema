package ru.job4j.cinema.Repository;

import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

public interface FilmRepository {
    Optional<Film> findById(int id);
    Collection<Film> findAll();
}
