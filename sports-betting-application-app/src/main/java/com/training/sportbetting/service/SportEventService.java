package com.training.sportbetting.service;

import com.training.sportbetting.controller.api.dto.IdsDTO;
import com.training.sportbetting.controller.api.exception.ResourceExistsException;
import com.training.sportbetting.controller.api.exception.ResourceNotFoundException;
import com.training.sportbetting.domain.bet.Bet;
import com.training.sportbetting.domain.outcome.Outcome;
import com.training.sportbetting.domain.sportevent.Result;
import com.training.sportbetting.domain.sportevent.SportEvent;
import com.training.sportbetting.repository.jpa.SportEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Service class which provide functionality by working with events.
 * Can generate result for event based on the outcomes.
 */
@Service
public class SportEventService {

    private SportEventRepository eventRepository;
    private WagerService wagerService;

    @Autowired
    public SportEventService(SportEventRepository eventRepository, WagerService wagerService) {
        this.eventRepository = eventRepository;
        this.wagerService = wagerService;
    }

    /**
     * Generate result for the event based on the event outcomes.
     *
     * @param event Event for what we need result.
     * @return Result instance.
     */
    public Result generateResultForEvent(SportEvent event) {
        checkNotNull(event);
        checkNotNull(event.getBets());

        if (event.getResult() != null) {
            return event.getResult();
        } else {
            List<Outcome> resultOutcomes = new ArrayList<>();
            for (Bet bet : event.getBets()) {
                Set<Outcome> outcomes = bet.getOutcomes();
                if (outcomes != null && !outcomes.isEmpty()) {
                    Outcome outcome = outcomes.stream().findAny().get();
                    resultOutcomes.add(outcome);
                }
            }
            return new Result(resultOutcomes);
        }
    }

    public SportEvent findById(int eventId) {
        return eventRepository
                .findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException(eventId));
    }

    public SportEvent findByIdWithBet(int eventId) {
        return eventRepository
                .findWithBetsById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException(eventId));
    }

    public SportEvent findByIdWithAllData(int eventId) {
        return eventRepository
                .findAllSportEventDataById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException(eventId));
    }

    public void saveEvent(SportEvent eventToSave) {
        var optionalFromRepo = eventRepository.findByTitle(eventToSave.getTitle());

        if (optionalFromRepo.isPresent()) {
            if (optionalFromRepo.get().equals(eventToSave)) {
                throw new ResourceExistsException(eventToSave);
            }
        }
        eventRepository.save(eventToSave);
    }

    @Transactional
    public SportEvent saveSportEventResult(Integer eventId, IdsDTO outcomeIds) {

        var sportEvent = findByIdWithAllData(eventId);

        if (sportEvent.getResult() != null) {
            throw new ResourceExistsException(sportEvent.getId());
        }

        var ids = outcomeIds.getIds();
        var resultOutcomes = findSportEventOutcomesByIds(sportEvent, ids);

        Result result = new Result(resultOutcomes);

        sportEvent.setResult(result);
        eventRepository.save(sportEvent);

        wagerService.applyResultOfEvent(result);

        return sportEvent;
    }

    private List<Outcome> findSportEventOutcomesByIds(SportEvent sportEvent, Set<Integer> ids) {
        var resultOutcomes = new ArrayList<Outcome>(ids.size());

        for (Bet bet : sportEvent.getBets()) {
            bet.getOutcomes()
                    .stream()
                    .filter(outcome -> ids.contains(outcome.getId()))
                    .forEach(resultOutcomes::add);
        }

        if (resultOutcomes.size() != ids.size()) {
            throw new IllegalArgumentException(
                    String.format("Outcomes %s not found in event with id %d.", ids.toString(), sportEvent.getId())
            );
        }
        return resultOutcomes;
    }
}

