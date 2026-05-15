package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @MockBean
    private FilmSessionService filmSessionService;

    @MockBean
    private FilmService filmService;

    @MockBean
    private HallService hallService;

    @Test
    public void whenGetBuyingThenReturnBuyingPage() throws Exception {
        when(filmSessionService.findAll()).thenReturn(Collections.emptyList());
        when(filmService.findAll()).thenReturn(Collections.emptyList());
        when(hallService.findAll()).thenReturn(Collections.emptyList());
        when(ticketService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/tickets/buying"))
                .andExpect(status().isOk())
                .andExpect(view().name("tickets/buying"));
    }

    @Test
    public void whenBuySuccessThenReturnSuccessPage() throws Exception {
        var user = new User(1, "Иван", "ivan@mail.ru", "pass");
        var session = new MockHttpSession();
        session.setAttribute("user", user);
        var ticket = new Ticket(1, 1, 2, 3, 1);
        when(ticketService.save(any())).thenReturn(Optional.of(ticket));
        mockMvc.perform(post("/tickets/buying")
                        .session(session)
                        .param("sessionId", "1")
                        .param("rowNumber", "2")
                        .param("placeNumber", "3"))
                .andExpect(status().isOk())
                .andExpect(view().name("tickets/buyingSuccesful"))
                .andExpect(model().attributeExists("ticket"));
    }

    @Test
    public void whenBuyFailThenReturnFailPage() throws Exception {
        var user = new User(1, "Иван", "ivan@mail.ru", "pass");
        var session = new MockHttpSession();
        session.setAttribute("user", user);
        when(ticketService.save(any())).thenReturn(Optional.empty());
        mockMvc.perform(post("/tickets/buying")
                        .session(session)
                        .param("sessionId", "1")
                        .param("rowNumber", "2")
                        .param("placeNumber", "3"))
                .andExpect(status().isOk())
                .andExpect(view().name("tickets/buyingFail"))
                .andExpect(model().attributeExists("message", "ticket"));
    }

    @Test
    public void whenGetBuyingWithSessionIdThenSelectSession() throws Exception {
        var start = LocalDateTime.of(2026, 5, 15, 18, 0);
        var filmSession = new FilmSession(5, 1, 1, start, start.plusHours(2), 300);
        when(filmSessionService.findAll()).thenReturn(Collections.singletonList(filmSession));
        when(filmService.findAll()).thenReturn(Collections.emptyList());
        when(hallService.findAll()).thenReturn(Collections.emptyList());
        when(ticketService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/tickets/buying").param("sessionId", "5"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("selectedSessionId", 5));
    }
}
