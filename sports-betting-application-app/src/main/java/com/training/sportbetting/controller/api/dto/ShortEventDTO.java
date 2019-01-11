package com.training.sportbetting.controller.api.dto;

import com.training.sportbetting.domain.outcome.Outcome;
import com.training.sportbetting.domain.sportevent.SportEvent;
import com.training.sportbetting.domain.sportevent.SportEventType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShortEventDTO {

    private String id;
    private String title;
    private SportEventType type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
    private List<String> resultValues;

    public static ShortEventDTO dtoFromSportEvent(SportEvent sportEvent) {
        List<String> result = null;

        if (sportEvent.getResult() != null) {
            result = sportEvent.getResult().getOutcomes()
                    .stream()
                    .map(Outcome::getValue)
                    .collect(toList());
        } else {
            result = emptyList();
        }

        return builder()
                .id(String.valueOf(sportEvent.getId()))
                .title(sportEvent.getTitle())
                .startDate(sportEvent.getStartDate())
                .endDate(sportEvent.getEndDate())
                .type(sportEvent.getType())
                .resultValues(result)
                .build();
    }
}
