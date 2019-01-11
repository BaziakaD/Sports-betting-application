package com.training.sportbetting.controller.form;

import com.training.sportbetting.domain.bet.Bet;
import com.training.sportbetting.domain.wager.Wager;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PlayerWagerForm {
    private long id;
    private String eventTitle;
    private String eventType;
    private String betType;
    private String outcomeValue;
    private Double outcomeOddValue;
    private Double amount;
    private boolean isWin;
    private boolean isProcessed;

    public static List<PlayerWagerForm> fromWagersAndBets(List<Wager> wagers, List<Bet> bets) {
        var eventWagers = new ArrayList<PlayerWagerForm>(wagers.size());

        for (Wager wager : wagers) {
            for (Bet bet : bets) {
                var outcome = wager.getOutcomeOdd().getOutcome();
                if (bet.getOutcomes().contains(outcome)) {
                    eventWagers.add(fromWagerAndBet(bet, wager));
                }
            }
        }
        return eventWagers;
    }

    private static PlayerWagerForm fromWagerAndBet(Bet bet, Wager wager) {
        return new PlayerWagerFormBuilder()
                .id(wager.getId())
                .eventTitle(bet.getEvent().getTitle())
                .eventType(bet.getEvent().getType().name())
                .betType(bet.getType().toString())
                .outcomeValue(wager.getOutcomeOdd().getOutcome().getValue())
                .outcomeOddValue(wager.getOutcomeOdd().getValue())
                .amount(wager.getAmount())
                .isWin(wager.isWin())
                .isProcessed(wager.isProcessed())
                .build();
    }
}
