package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.Repository.TicketRepository;
import ru.job4j.cinema.model.Ticket;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleTicketService implements TicketService {

    private final TicketRepository ticketRepository;

    public SimpleTicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        // Бизнес-правило: нельзя забронировать уже занятое место
        if (ticketRepository.isSeatTaken(ticket.getSessionId(),
                ticket.getRowNumber(),
                ticket.getPlaceNumber())) {
            return Optional.empty();
        }
        return Optional.of(ticketRepository.save(ticket));
    }

    @Override
    public boolean update(Ticket ticket) {
        return false;
    }

    @Override
    public Optional<Ticket> findById(int id) {
        return ticketRepository.findById(id);
    }

    @Override
    public Collection<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Collection<Ticket> findBySessionId(int sessionId) {
        return ticketRepository.findBySessionId(sessionId);
    }

    @Override
    public Collection<Ticket> findByUserId(int userId) {
        return ticketRepository.findByUserId(userId);
    }

    @Override
    public boolean deleteById(int id) {
        return ticketRepository.deleteById(id);
    }

    @Override
    public boolean deleteBySessionId(int sessionId) {
        return ticketRepository.deleteById(sessionId);
    }

    @Override
    public boolean isSeatTaken(int sessionId, int rowNumber, int placeNumber) {
        return ticketRepository.isSeatTaken(sessionId, rowNumber, placeNumber);
    }
}