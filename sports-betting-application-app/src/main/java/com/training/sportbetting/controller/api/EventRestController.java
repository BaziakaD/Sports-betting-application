package com.training.sportbetting.controller.api;

import com.training.sportbetting.controller.api.dto.EventDTO;
import com.training.sportbetting.controller.api.dto.IdsDTO;
import com.training.sportbetting.controller.api.dto.ShortEventDTO;
import com.training.sportbetting.repository.jpa.SportEventRepository;
import com.training.sportbetting.service.SportEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("api/event")
public class EventRestController {

    private SportEventRepository eventRepository;
    private SportEventService eventService;

    @Autowired
    public EventRestController(SportEventRepository eventRepository, SportEventService eventService) {
        this.eventRepository = eventRepository;
        this.eventService = eventService;
    }

    @GetMapping(
            produces = APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<List<ShortEventDTO>> getAllSportEvents() {
        var allWithBets = eventRepository.findAllWithBetsAndResultsOutcomeValue();
        return ResponseEntity.ok()
                .body(
                        allWithBets
                                .stream()
                                .map(ShortEventDTO::dtoFromSportEvent)
                                .collect(toList()))
                ;
    }

    @GetMapping(value = "/{id}",
            produces = APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<EventDTO> findSportEvent(@PathVariable int id) {
        var eventWithBets = eventService.findByIdWithAllData(id);
        return ResponseEntity.status(OK).body(EventDTO.dtoFromSportEvent(eventWithBets));
    }

    @PostMapping(
            consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventToSaveDTO) {
        var eventToSave = EventDTO.sportEventFromDTO(eventToSaveDTO);

        eventService.saveEvent(eventToSave);

        return ResponseEntity.status(CREATED).body(EventDTO.dtoFromSportEvent(eventToSave));
    }

    @PostMapping(value = "/{eventId}/result",
            consumes = APPLICATION_JSON_UTF8_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<EventDTO> saveResult(@RequestBody IdsDTO outcomesIds, @PathVariable int eventId) {
        var sportEvent = eventService.saveSportEventResult(eventId, outcomesIds);

        return ResponseEntity.status(CREATED).body(EventDTO.dtoFromSportEvent(sportEvent));

    }
}
