package com.training.sportbetting.controller.api.dto;

import com.training.sportbetting.domain.outcome.Outcome;
import com.training.sportbetting.domain.outcome.OutcomeOdd;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutcomeOddDTO {

    private Double value;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime validFrom;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime validTo;

    public static OutcomeOdd outcomeOddFromDTO(Outcome outcome, OutcomeOddDTO dto) {
        return new OutcomeOdd(dto.getValue(), dto.getValidFrom(), dto.getValidTo(), outcome);
    }

    public static OutcomeOddDTO dtoFromOutcomeOdd(OutcomeOdd outcomeOdd) {
        return new OutcomeOddDTO(outcomeOdd.getValue(), outcomeOdd.getValidFrom(), outcomeOdd.getValidTo());
    }
}
