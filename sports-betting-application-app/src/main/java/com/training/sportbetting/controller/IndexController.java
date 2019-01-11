package com.training.sportbetting.controller;

import com.training.sportbetting.controller.form.PlayerForm;
import com.training.sportbetting.controller.form.PlayerWagerForm;
import com.training.sportbetting.domain.wager.Wager;
import com.training.sportbetting.repository.jpa.BetRepository;
import com.training.sportbetting.repository.jpa.WagerRepository;
import com.training.sportbetting.service.UserService;
import com.training.sportbetting.service.WagerService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;

@Controller
@RequestMapping("/")
public class IndexController {

    private UserService userService;
    private WagerRepository wagerRepository;
    private WagerService wagerService;
    private BetRepository betRepository;

    @Autowired
    public IndexController(UserService userService, WagerRepository wagerRepository,
                           WagerService wagerService, BetRepository betRepository) {
        this.userService = userService;
        this.wagerRepository = wagerRepository;
        this.wagerService = wagerService;
        this.betRepository = betRepository;
    }

    @GetMapping
    public String index(Model model, Principal principal) {

        var player = userService.findPlayerByEmail(principal.getName());
        var wagers = wagerRepository.findByPlayerEmail(player.getEmail());
        var bets = betRepository.findAllWithOutcomesAndOdds();

        var wagerComparator = Collections.reverseOrder(Comparator.comparing(Wager::getTimestamp));
        wagers.sort(wagerComparator);

        model.addAttribute("playerForm", PlayerForm.fromPlayer(player));
        model.addAttribute("wagerForm", PlayerWagerForm.fromWagersAndBets(wagers, bets));

        return "index";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute PlayerForm playerForm) {
        userService.updateUser(playerForm);
        return "redirect:/";
    }

    @PostMapping(value = "/deleteWager")
    public String deleteWager(@ModelAttribute WagerToDeleteForm form) {
        wagerService.removeUnprocessedWager(form.getWagerId());
        return "redirect:/";
    }

    @Data
    class WagerToDeleteForm {
        private int wagerId;
    }
}
