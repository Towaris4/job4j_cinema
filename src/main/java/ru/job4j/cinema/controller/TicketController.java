package ru.job4j.cinema.controller;

import jakarta.servlet.http.HttpSession;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.film.FilmService;
import ru.job4j.cinema.service.filmsession.FilmSessionService;
import ru.job4j.cinema.service.hall.HallService;
import ru.job4j.cinema.service.ticket.TicketService;

@ThreadSafe
@Controller
public class TicketController {
    private final TicketService ticketService;
    private final FilmSessionService filmSessionService;
    private final FilmService filmService;
    private final HallService hallService;

    public TicketController(TicketService ticketService, FilmSessionService filmSessionService,
                            FilmService filmService, HallService hallService) {
        this.ticketService = ticketService;
        this.filmSessionService = filmSessionService;
        this.filmService = filmService;
        this.hallService = hallService;
    }

    @GetMapping("/tickets/buying")
    public String getBuyingPage(Model model,
                                @RequestParam(required = false) Integer sessionId) {
        fillBuyingModel(model);
        if (sessionId != null) {
            model.addAttribute("selectedSessionId", sessionId);
        }
        return "tickets/buying";
    }

    @PostMapping("/tickets/buying")
    public String buyTicket(@ModelAttribute Ticket ticket, Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        ticket.setUserId(user.getId());
        var savedTicket = ticketService.save(ticket);
        if (savedTicket.isEmpty()) {
            model.addAttribute("message", "Не удалось приобрести билет. Это место уже занято.");
            model.addAttribute("ticket", ticket);
            return "tickets/fail";
        }
        model.addAttribute("ticket", savedTicket.get());
        return "tickets/success";
    }

    private void fillBuyingModel(Model model) {
        model.addAttribute("filmSessions", filmSessionService.findAll());
        model.addAttribute("film", filmService.findAll());
        model.addAttribute("hall", hallService.findAll());
        model.addAttribute("tickets", ticketService.findAll());
    }
}
