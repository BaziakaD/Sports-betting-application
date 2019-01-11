package com.training.sportbetting.repository.jpa;

import com.training.sportbetting.domain.outcome.Outcome;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome, Integer> {

    @EntityGraph("outcomeWithOdds")
    Optional<Outcome> findWithOddsById(int id);
}
