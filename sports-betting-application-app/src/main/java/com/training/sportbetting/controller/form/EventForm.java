package com.training.sportbetting.controller.form;

import com.training.sportbetting.domain.sportevent.SportEvent;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventForm {

    private int id;
    private String title;
    private String type;
    private String startDate;
    private String endDate;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EventForm fromEvent(SportEvent event) {
        return new EventFormBuilder()
                .id(event.getId())
                .title(event.getTitle())
                .type(event.getType().toString())
                .startDate(dateTimeFormatter.format(event.getStartDate()))
                .endDate(dateTimeFormatter.format(event.getEndDate()))
                .build();
    }

    public static List<EventForm> fromEvents(List<SportEvent> events) {
        return events.stream()
                .map(EventForm::fromEvent)
                .collect(Collectors.toList());
    }
}
