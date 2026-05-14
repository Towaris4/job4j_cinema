package ru.job4j.cinema.Repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.cinema.Repository.TicketRepository;
import ru.job4j.cinema.model.Ticket;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Repository
public class Sql2oTicketRepository implements TicketRepository {

    private static final Logger LOG = LoggerFactory.getLogger(Sql2oTicketRepository.class);
    private final Sql2o sql2o;

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Ticket> findById(int id) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM tickets WHERE id = :id";
            Ticket ticket = con.createQuery(sql)
                    .addParameter("id", id)
                    .setColumnMappings(Ticket.COLUMN_MAPPING)
                    .executeAndFetchFirst(Ticket.class);
            return Optional.ofNullable(ticket);
        } catch (Exception e) {
            LOG.error("Ошибка при поиске билета по id: {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public Collection<Ticket> findAll() {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM tickets";
            return con.createQuery(sql)
                    .setColumnMappings(Ticket.COLUMN_MAPPING)
                    .executeAndFetch(Ticket.class);
        } catch (Exception e) {
            LOG.error("Ошибка при получении всех билетов", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Collection<Ticket> findBySessionId(int sessionId) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM tickets WHERE session_id = :sessionId";
            return con.createQuery(sql)
                    .addParameter("sessionId", sessionId)
                    .setColumnMappings(Ticket.COLUMN_MAPPING)
                    .executeAndFetch(Ticket.class);
        } catch (Exception e) {
            LOG.error("Ошибка при поиске билетов по сеансу: {}", sessionId, e);
            return Collections.emptyList();
        }
    }

    @Override
    public Collection<Ticket> findByUserId(int userId) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM tickets WHERE user_id = :userId";
            return con.createQuery(sql)
                    .addParameter("userId", userId)
                    .setColumnMappings(Ticket.COLUMN_MAPPING)
                    .executeAndFetch(Ticket.class);
        } catch (Exception e) {
            LOG.error("Ошибка при поиске билетов по пользователю: {}", userId, e);
            return Collections.emptyList();
        }
    }

    @Override
    public Ticket save(Ticket ticket) {
        try (Connection con = sql2o.open()) {
            String sql = "INSERT INTO tickets (session_id, row_number, place_number, user_id) " +
                    "VALUES (:sessionId, :rowNumber, :placeNumber, :userId)";
            Query query = con.createQuery(sql, true)
                    .addParameter("sessionId", ticket.getSessionId())
                    .addParameter("rowNumber", ticket.getRowNumber())
                    .addParameter("placeNumber", ticket.getPlaceNumber())
                    .addParameter("userId", ticket.getUserId());
            ticket.setId(query.executeUpdate().getKey(Integer.class));
            return ticket;
        } catch (Exception e) {
            LOG.error("Ошибка при сохранении билета: {}", ticket, e);
            throw new RuntimeException("Ошибка при сохранении билета", e);
        }
    }

    @Override
    public boolean update(Ticket ticket) {
        try (Connection con = sql2o.open()) {
            String sql = "UPDATE tickets SET session_id = :sessionId, row_number = :rowNumber, " +
                    "place_number = :placeNumber, user_id = :userId WHERE id = :id";
            return con.createQuery(sql)
                    .addParameter("sessionId", ticket.getSessionId())
                    .addParameter("rowNumber", ticket.getRowNumber())
                    .addParameter("placeNumber", ticket.getPlaceNumber())
                    .addParameter("userId", ticket.getUserId())
                    .addParameter("id", ticket.getId())
                    .executeUpdate().getResult() > 0;
        } catch (Exception e) {
            LOG.error("Ошибка при обновлении билета: {}", ticket, e);
            return false;
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection con = sql2o.open()) {
            String sql = "DELETE FROM tickets WHERE id = :id";
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate().getResult() > 0;
        } catch (Exception e) {
            LOG.error("Ошибка при удалении билета по id: {}", id, e);
            return false;
        }
    }

    @Override
    public boolean deleteBySessionId(int sessionId) {
        try (Connection con = sql2o.open()) {
            String sql = "DELETE FROM tickets WHERE session_id = :sessionId";
            return con.createQuery(sql)
                    .addParameter("sessionId", sessionId)
                    .executeUpdate().getResult() > 0;
        } catch (Exception e) {
            LOG.error("Ошибка при удалении билетов по сеансу: {}", sessionId, e);
            return false;
        }
    }

    @Override
    public boolean isSeatTaken(int sessionId, int rowNumber, int placeNumber) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT COUNT(*) FROM tickets " +
                    "WHERE session_id = :sessionId AND row_number = :rowNumber AND place_number = :placeNumber";
            Integer count = con.createQuery(sql)
                    .addParameter("sessionId", sessionId)
                    .addParameter("rowNumber", rowNumber)
                    .addParameter("placeNumber", placeNumber)
                    .executeScalar(Integer.class);
            return count != null && count > 0;
        } catch (Exception e) {
            LOG.error("Ошибка при проверке занятости места: session={}, row={}, place={}",
                    sessionId, rowNumber, placeNumber, e);
            return false;
        }
    }
}
