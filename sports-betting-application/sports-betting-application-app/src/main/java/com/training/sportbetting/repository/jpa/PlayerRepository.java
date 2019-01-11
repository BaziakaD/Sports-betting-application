package com.training.sportbetting.repository.jpa;

import com.training.sportbetting.domain.user.Player;

import java.util.Optional;

/**
 * Interface PlayerRepository extends UserRepository by adding find by account number functionality.
 */
public interface PlayerRepository extends UserRepository {

    Optional<Player> findByAccountNumber(Integer accountNumber);
}
