package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Ticket;

import java.util.Collection;
import java.util.Optional;

public interface TicketService {
    Optional<Ticket> findById(int id);

    Collection<Ticket> findAll();

    Collection<Ticket> findBySessionId(int sessionId);

    Collection<Ticket> findByUserId(int userId);

    Optional<Ticket> save(Ticket ticket);

    boolean update(Ticket ticket);

    boolean deleteById(int id);

    boolean deleteBySessionId(int sessionId);

    boolean isSeatTaken(int sessionId, int rowNumber, int placeNumber);
}
