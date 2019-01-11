package com.training.sportbetting.controller.api.dto;


import com.training.sportbetting.domain.bet.Bet;
import com.training.sportbetting.domain.bet.BetType;
import com.training.sportbetting.domain.sportevent.SportEvent;
import lombok.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BetDTO {

    private String description;
    private BetType type;
    private List<OutcomeDTO> outcomes;

    public static Bet betFromDTO(SportEvent sportEvent, BetDTO betDTO) {

        var outcomes = betDTO.getOutcomes().stream()
                .map(OutcomeDTO::outcomeFromDTO)
                .collect(toSet());

        return new Bet(sportEvent, betDTO.getDescription(), betDTO.getType(), outcomes);
    }

    public static BetDTO dtoFromBet(SportEvent event, Bet bet) {

        var outcomes = bet.getOutcomes().stream()
                .map(OutcomeDTO::dtoFromOutcome)
                .collect(toList());

        return new BetDTO(bet.getDescription(), bet.getType(), outcomes);

    }
}
