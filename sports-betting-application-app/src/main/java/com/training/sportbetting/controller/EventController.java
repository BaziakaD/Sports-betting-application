package com.training.sportbetting.controller;

import com.training.sportbetting.controller.form.BetForm;
import com.training.sportbetting.controller.form.EventForm;
import com.training.sportbetting.domain.sportevent.SportEvent;
import com.training.sportbetting.repository.jpa.SportEventRepository;
import com.training.sportbetting.service.SportEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.training.sportbetting.utils.Formatter.dateTimeFormatter;

@Controller
@RequestMapping("/events")
public class EventController {

    private SportEventRepository eventRepository;
    private SportEventService eventService;

    @Autowired
    public EventController(SportEventRepository eventRepository, SportEventService eventService) {
        this.eventRepository = eventRepository;
        this.eventService = eventService;
    }

    @GetMapping
    public String getEvents(Model model) {
        List<EventForm> eventForms = EventForm.fromEvents(eventRepository.findAll());
        model.addAttribute("events", eventForms);
        return "events";
    }

    @GetMapping("/bets")
    public String getBets(@RequestParam int eventId, HttpSession session, Model model) {
        var event = eventService.findByIdWithBet(eventId);
        session.setAttribute("event", event);

        model.addAttribute("eventId", event.getId());
        model.addAttribute("msg", createMsg(event));
        model.addAttribute("bets", BetForm.fromBets(event.getBets()));
        return "bets";
    }

    private String createMsg(SportEvent event) {
        return String.format("Bets for event %s. %s", event.getTitle(), dateTimeFormatter().format(event.getStartDate()));
    }
}
