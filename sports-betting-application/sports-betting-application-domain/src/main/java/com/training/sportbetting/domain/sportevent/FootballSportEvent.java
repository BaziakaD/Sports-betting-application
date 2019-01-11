package com.training.sportbetting.domain.sportevent;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class FootballSportEvent extends SportEvent {


    public FootballSportEvent(String title, LocalDateTime startDate, LocalDateTime endDate) {
        super(title, startDate, endDate);

        type = SportEventType.FOOTBALL;
    }
}
