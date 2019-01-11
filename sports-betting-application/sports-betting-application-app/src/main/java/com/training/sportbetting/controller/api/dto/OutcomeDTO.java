package com.training.sportbetting.controller.api.dto;

import com.training.sportbetting.domain.outcome.Outcome;
import lombok.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutcomeDTO {

    private String id;
    private String value;
    private List<OutcomeOddDTO> outcomeOdds;

    public static Outcome outcomeFromDTO(OutcomeDTO outcomeDTO) {
        Outcome outcome = new Outcome(outcomeDTO.getValue());
        outcomeDTO.getOutcomeOdds().stream()
                .map(dto -> OutcomeOddDTO.outcomeOddFromDTO(outcome, dto))
                .forEach(outcomeOdd -> outcome.getOutcomeOdds().add(outcomeOdd));
        return outcome;
    }

    public static OutcomeDTO dtoFromOutcome(Outcome outcome) {
        return new OutcomeDTOBuilder()
                .id(String.valueOf(outcome.getId()))
                .value(outcome.getValue())
                .outcomeOdds(outcome.getOutcomeOdds().stream().map(OutcomeOddDTO::dtoFromOutcomeOdd).collect(toList()))
                .build();
    }
}
