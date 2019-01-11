package com.training.sportbetting.service;

import com.training.sportbetting.domain.sportevent.Result;
import com.training.sportbetting.domain.sportevent.SportEvent;
import com.training.sportbetting.repository.jpa.SportEventRepository;
import com.training.sportbetting.repository.jpa.UserRepository;
import com.training.sportbetting.utils.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SportEventServiceTest {

    @Mock
    private SportEventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    private SportEventService eventService;

    private List<SportEvent> events = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        eventService = new SportEventService(eventRepository, mock(WagerService.class));

        when(eventRepository.save(any()))
                .then(invocationOnMock -> {
                    events.add(invocationOnMock.getArgument(0));
                    return invocationOnMock.getArgument(0);
                });

        DataGenerator.generateData(eventRepository, userRepository, mock(WagerService.class), mock(SportEventService.class));
    }

    @Test
    public void generatingResultForEventsTest() {
        SportEvent event = events.get(0);
        assertNull(event.getResult());

        Result result = eventService.generateResultForEvent(event);
        assertNotNull(result);
    }

    @Test
    void generatingResultForEventWithResult() {
        SportEvent event = events.get(0);
        assertNull(event.getResult());

        Result result = eventService.generateResultForEvent(event);
        event.setResult(result);

        Result theSameResult = eventService.generateResultForEvent(event);

        assertEquals(result, theSameResult);
    }
}