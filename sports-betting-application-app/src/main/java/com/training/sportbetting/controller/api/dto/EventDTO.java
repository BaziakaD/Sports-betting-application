package com.training.sportbetting.controller.api.dto;

import com.training.sportbetting.domain.sportevent.FootballSportEvent;
import com.training.sportbetting.domain.sportevent.SportEvent;
import com.training.sportbetting.domain.sportevent.SportEventType;
import com.training.sportbetting.domain.sportevent.TennisSportEvent;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("resultValues")
public class EventDTO extends ShortEventDTO {

    private List<BetDTO> bets;
    private List<OutcomeDTO> result;

    @Builder(builderMethodName = "eventDTObuilder")
    public EventDTO(String id, String title, SportEventType type, LocalDateTime startDate, LocalDateTime endDate, List<BetDTO> bets, List<OutcomeDTO> result) {
        super(id, title, type, startDate, endDate, null);
        this.bets = bets;
        this.result = result;
    }

    public static SportEvent sportEventFromDTO(EventDTO eventDTO) {

        switch (eventDTO.getType()) {
            case FOOTBALL:
                var footballSportEvent = new FootballSportEvent(eventDTO.getTitle(), eventDTO.getStartDate(), eventDTO.getEndDate());
                setBets(eventDTO, footballSportEvent);
                return footballSportEvent;
            case TENNIS:
                var tennisSportEvent = new TennisSportEvent(eventDTO.getTitle(), eventDTO.getStartDate(), eventDTO.getEndDate());
                setBets(eventDTO, tennisSportEvent);
                return tennisSportEvent;
            default:
                throw new IllegalArgumentException(String.format("Event type %s is not exist.", eventDTO.getType()));
        }
    }

    private static void setBets(EventDTO eventDTO, SportEvent sportEvent) {
        var bets = eventDTO.getBets().stream()
                .map(betDTO -> BetDTO.betFromDTO(sportEvent, betDTO))
                .collect(Collectors.toList());
        sportEvent.getBets().addAll(bets);
    }

    public static EventDTO dtoFromSportEvent(SportEvent event) {
        List<OutcomeDTO> result = null;

        if (event.getResult() != null) {
            result = event.getResult().getOutcomes().stream().map(OutcomeDTO::dtoFromOutcome).collect(toList());
        } else {
            result = emptyList();
        }

        return EventDTO.eventDTObuilder()
                .id(String.valueOf(event.getId()))
                .title(event.getTitle())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .type(event.getType())
                .bets(event.getBets().stream().map(bet -> BetDTO.dtoFromBet(event, bet)).collect(toList()))
                .result(result)
                .build();
    }
}
