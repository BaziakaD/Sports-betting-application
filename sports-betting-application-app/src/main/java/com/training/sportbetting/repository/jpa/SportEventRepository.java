package com.training.sportbetting.repository.jpa;

import com.training.sportbetting.domain.sportevent.SportEvent;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SportEventRepository extends JpaRepository<SportEvent, Integer> {

    @EntityGraph("eventWithBets")
    Optional<SportEvent> findWithBetsById(int eventId);

    Optional<SportEvent> findByTitle(String title);

    @EntityGraph("eventAll")
    Optional<SportEvent> findAllSportEventDataById(int id);

    @EntityGraph("eventWithBetsWithResult")
    @Query("SELECT event FROM SportEvent event")
    List<SportEvent> findAllWithBetsAndResultsOutcomeValue();

    @EntityGraph("eventWithBets")
    @Query("SELECT event FROM SportEvent event")
    List<SportEvent> findAllWithBets();
}
