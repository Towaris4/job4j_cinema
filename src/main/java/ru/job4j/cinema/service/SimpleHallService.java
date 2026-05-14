package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.Repository.Sql2oHallRepository;
import ru.job4j.cinema.Repository.HallRepository;
import ru.job4j.cinema.model.Hall;

import java.util.Optional;

@Service
public class SimpleHallService implements HallService {
    private final HallRepository hallRepository;

    public SimpleHallService(HallRepository sql2oHallRepository) {
        this.hallRepository = sql2oHallRepository;
    }
    @Override
    public Optional<Hall> findById(int id) {
        return hallRepository.findById(id);
    }
}
