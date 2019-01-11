package com.training.sportbetting.service;

import com.training.sportbetting.configuration.AppConfiguration;
import com.training.sportbetting.domain.user.Player;
import com.training.sportbetting.repository.jpa.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitWebConfig(AppConfiguration.class)
public class OptimisticLockingTest {

    @Autowired
    private PlayerRepository playerRepository;

    private Player player;

    @BeforeEach
    void setUp() {
        player =  (Player) playerRepository.findByEmail("email@mail.com").get();
    }

    @Test
    void optimisticLockTest() {
        player.setBalance(1d);

        assertThrows(ObjectOptimisticLockingFailureException.class, () -> updatePlayer(player));
    }

    @Transactional
    public void updatePlayer(Player player) {
        playerRepository.save(player);
        updatePlayerInNewTransaction(player);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updatePlayerInNewTransaction(Player player) {
        playerRepository.save(player);
    }
}
