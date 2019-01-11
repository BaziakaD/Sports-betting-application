package com.training.sportbetting.repository.jpa;

import com.training.sportbetting.domain.bet.Bet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BetRepository extends JpaRepository<Bet, Integer> {

    @EntityGraph("betsWithOutcomes")
    @Query("SELECT bet FROM Bet bet")
    List<Bet> findAllWithOutcomesAndOdds();

    @EntityGraph("betsWithOutcomes")
    Optional<Bet> findWithOutcomeById(int id);

}
