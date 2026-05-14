package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.Repository.FilmSessionRepository;
import ru.job4j.cinema.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleFilmSessionService implements FilmSessionService {
    private final FilmSessionRepository filmSessionRepository;

    public SimpleFilmSessionService(FilmSessionRepository sql2oFilmSessionRepository) {
        this.filmSessionRepository = sql2oFilmSessionRepository;
    }
    @Override
    public Optional<FilmSession> findById(int id) {
        return filmSessionRepository.findById(id);
    }

    @Override
    public Collection<FilmSession> findAll() {
        return filmSessionRepository.findAll();
    }
}
