package com.training.sportbetting.controller.form;

import com.training.sportbetting.domain.bet.Bet;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BetForm {

    private int id;
    private String betDescription;
    private String betType;

    public static BetForm fromBet(Bet bet) {
        return new BetFormBuilder()
                .id(bet.getId())
                .betDescription(bet.getDescription())
                .betType(bet.getType().toString())
                .build();
    }

    public static List<BetForm> fromBets(Set<Bet> bets) {
        return bets.stream()
                .map(BetForm::fromBet)
                .collect(Collectors.toList());
    }
}
