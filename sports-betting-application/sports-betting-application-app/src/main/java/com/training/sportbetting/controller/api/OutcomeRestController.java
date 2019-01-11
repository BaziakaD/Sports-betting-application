package com.training.sportbetting.controller.api;

import com.training.sportbetting.controller.api.dto.OutcomeDTO;
import com.training.sportbetting.controller.api.dto.OutcomeOddDTO;
import com.training.sportbetting.controller.api.exception.ResourceNotFoundException;
import com.training.sportbetting.repository.jpa.OutcomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("api/outcome")
public class OutcomeRestController {

    private OutcomeRepository outcomeRepository;

    @Autowired
    public OutcomeRestController(OutcomeRepository outcomeRepository) {
        this.outcomeRepository = outcomeRepository;
    }

    @PatchMapping(value = "/{id}/odd", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> addOddToOutcome(@RequestBody OutcomeOddDTO oddDTO, @PathVariable int id) {
        var optionalFromRepo = outcomeRepository.findWithOddsById(id);
        return optionalFromRepo
                .map(outcome -> {
                    outcome.getOutcomeOdds().add(OutcomeOddDTO.outcomeOddFromDTO(outcome, oddDTO));
                    return outcomeRepository.save(outcome);
                })
                .map(outcome -> ResponseEntity.ok(OutcomeDTO.dtoFromOutcome(outcome)))
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
