package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.Repository.FilmRepository;
import ru.job4j.cinema.model.Film;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleFilmService implements FilmService {

    private final FilmRepository filmRepository;

    public SimpleFilmService(FilmRepository sql2oFilmRepository) {
        this.filmRepository = sql2oFilmRepository;
    }

    @Override
    public Optional<Film> findById(int id) {
        return filmRepository.findById(id);
    }

    @Override
    public Collection<Film> findAll() {
        return filmRepository.findAll();
    }
}
