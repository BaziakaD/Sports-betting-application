package com.training.sportbetting.service;

import com.training.sportbetting.domain.outcome.OutcomeOdd;
import com.training.sportbetting.domain.sportevent.Result;
import com.training.sportbetting.domain.user.Player;
import com.training.sportbetting.domain.wager.Wager;
import com.training.sportbetting.repository.jpa.WagerRepository;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Service class used manipulation with wagers.
 */
@Service
public class WagerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WagerService.class);

    private WagerRepository wagerRepository;
    private UserService userService;

    @Autowired
    public WagerService(WagerRepository wagerRepository, UserService userService) {
        this.wagerRepository = wagerRepository;
        this.userService = userService;
    }

    /**
     * Factory method for wager creation.
     *
     * @param player     Player which want to create wager.
     * @param amount     Amount of money which player gives to wager.
     * @param outcomeOdd Outcome odd instance encapsulate outcome, date and coefficient.
     * @return If wager was create successfully method will be return true else false.
     */
    @Transactional
    public boolean createWager(Player player, Double amount, OutcomeOdd outcomeOdd) {
        checkArgument(player != null, "Player must be not null");
        checkArgument(amount != null, "Amount and outcome odd must be not null");
        checkArgument(outcomeOdd != null, "Outcome odd must be not null");

        Wager wager = new Wager(player, outcomeOdd, amount, player.getCurrency(), LocalDateTime.now());
        try {
            userService.subtractBill(player, amount);
            wagerRepository.save(wager);
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Method apply event result to players.
     *
     * @param result Event result.
     */
    @Transactional
    public void applyResultOfEvent(Result result) {
        checkNotNull(result, "Result cannot be be null");
        List<Wager> wagers = Lists.newArrayList(wagerRepository.findAll());

        var resultOutcomes = result.getOutcomes();

        for (Wager wager : wagers) {
            if (wager.isProcessed()) {
                continue;
            }

            OutcomeOdd outcomeOdd = wager.getOutcomeOdd();
            if (resultOutcomes.contains(outcomeOdd.getOutcome())) {
                Player player = wager.getPlayer();
                double amount = wager.getAmount() * outcomeOdd.getValue();
                userService.increaseBill(player, amount);
                updateProcessedWager(wager, true);
            } else {
                updateProcessedWager(wager, false);
            }
        }
    }

    private void updateProcessedWager(Wager wager, boolean isWin) {
        wager.setProcessed(true);
        wager.setWin(isWin);
        wagerRepository.save(wager);
    }

    public void removeUnprocessedWager(int wagerId) {
        var wager = wagerRepository.findById(wagerId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Wager with id %d not found.", wagerId)));

        if (!wager.isProcessed()) {
            wagerRepository.deleteById(wager.getId());
            userService.increaseBill(wager.getPlayer(), wager.getAmount());
        } else {
            throw new IllegalStateException("Wager with id %d has been processed and cannot be removed.");
        }
    }
}
