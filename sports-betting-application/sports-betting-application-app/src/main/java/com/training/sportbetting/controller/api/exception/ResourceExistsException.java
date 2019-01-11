package com.training.sportbetting.controller.api.exception;

import com.training.sportbetting.domain.sportevent.SportEvent;

import static com.training.sportbetting.utils.Formatter.dateTimeFormatter;

public class ResourceExistsException extends RuntimeException {
    public ResourceExistsException(SportEvent event) {
        super(String.format("Resource \"%s\" on %s is already exists.",
                event.getTitle(),
                dateTimeFormatter().format(event.getStartDate())));
    }

    public ResourceExistsException(int id) {
        super(String.format("Resource with id %d is already exist", id));
    }
}
