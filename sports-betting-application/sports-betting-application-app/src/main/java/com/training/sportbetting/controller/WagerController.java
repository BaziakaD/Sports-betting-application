package com.training.sportbetting.controller;

import com.training.sportbetting.controller.form.NewWagerForm;
import com.training.sportbetting.domain.bet.Bet;
import com.training.sportbetting.domain.outcome.Outcome;
import com.training.sportbetting.domain.sportevent.SportEvent;
import com.training.sportbetting.repository.jpa.BetRepository;
import com.training.sportbetting.service.UserService;
import com.training.sportbetting.service.WagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Set;

import static com.training.sportbetting.utils.Formatter.dateTimeFormatter;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

@Controller
@RequestMapping("/wager")
public class WagerController {

    private WagerService wagerService;
    private UserService userService;
    private BetRepository betRepository;

    @Autowired
    public WagerController(WagerService wagerService, UserService userService, BetRepository betRepository) {
        this.wagerService = wagerService;
        this.userService = userService;
        this.betRepository = betRepository;
    }

    @GetMapping
    public String createWagerView(@RequestParam int betId, HttpSession session, Principal principal, Model model) {
        var eventAttr = session.getAttribute("event");
        checkNotNull(eventAttr, "Event is not present in current session.");
        checkState(eventAttr instanceof SportEvent, "Object 'event' must be instance of BetForm.");

        var event = (SportEvent) eventAttr;
        Bet bet = betRepository.findWithOutcomeById(betId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Bet with id %d not found.", betId)));

        var outcomes = bet.getOutcomes();
        session.setAttribute("outcomes", outcomes);

        var player = userService.findPlayerByEmail(principal.getName());
        String msg = createMsg(event, bet);

        model.addAttribute("betId", betId);
        model.addAttribute("msg", msg);
        model.addAttribute("outcomes", outcomes);
        model.addAttribute("currency", player.getCurrency().toString());
        return "wager";
    }

    @PostMapping
    public String createWager(@ModelAttribute NewWagerForm form, Principal principal, HttpSession session, Model model) {
        var outcomesAttr = session.getAttribute("outcomes");
        checkNotNull(outcomesAttr, "Outcomes is not present in current session.");
        checkState(outcomesAttr instanceof Set, "Object 'outcomes' must be instance of Outcome.");

        var outcomes = (Set<Outcome>) outcomesAttr;
        var outcome = getOutcome(outcomes, form.getOutcomeId());
        var player = userService.findPlayerByEmail(principal.getName());

        wagerService.createWager(player, form.getAmount(), outcome.getValidOutcomeOdd());

        return "redirect:/";
    }

    private String createMsg(SportEvent event, Bet bet) {
        return String.format("Create wager for %s event. Date - %s. %s. Bet type - %s.",
                event.getTitle(), dateTimeFormatter().format(event.getStartDate()), bet.getDescription(), bet.getType());
    }

    private Outcome getOutcome(Set<Outcome> outcomes, int id) {
        return outcomes.stream()
                .filter(o -> o.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Outcome with id %d not found", id)));
    }
}
