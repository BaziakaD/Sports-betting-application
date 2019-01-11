package com.training.sportbetting.service;

import com.training.sportbetting.domain.outcome.Outcome;
import com.training.sportbetting.domain.outcome.OutcomeOdd;
import com.training.sportbetting.domain.sportevent.Result;
import com.training.sportbetting.domain.user.Player;
import com.training.sportbetting.domain.wager.Wager;
import com.training.sportbetting.repository.jpa.PlayerRepository;
import com.training.sportbetting.repository.jpa.SportEventRepository;
import com.training.sportbetting.repository.jpa.WagerRepository;
import com.training.sportbetting.utils.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WagerServiceTest {

    @Mock
    private WagerRepository wagerRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private UserService userService;

    private WagerService wagerService;

    private Player player;
    private List<Wager> wagers = new ArrayList<>();

    private double amount = 100d;
    private double coefficient = 1.7d;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        wagerService = new WagerService(wagerRepository, userService);

        when(playerRepository.save(any()))
                .then(invocationOnMock -> {
                    player = invocationOnMock.getArgument(0);
                    return player;
                });

        when(wagerRepository.save(any()))
                .then(invocationOnMock -> {
                    wagers.add(invocationOnMock.getArgument(0));
                    return invocationOnMock.getArgument(0);
                });

        when(wagerRepository.findAll())
                .thenReturn(wagers);

        DataGenerator.generateData(mock(SportEventRepository.class), playerRepository, mock(WagerService.class), mock(SportEventService.class));
    }

    @Test
        // For every tested method once again go through extreme values, null-values etc. +
    void validCreateWagerTest() {
        assertTrue(wagerService.createWager(player, 100d, createOutcomeOdd()));
    }

    @Test
    void nonValidCreateWagerTest() {
        assertFalse(wagerService.createWager(player, player.getBalance() + 1, createOutcomeOdd()));
    }

    @Test
    void createWagerWithNullValueTest() {
        OutcomeOdd outcomeOdd = createOutcomeOdd();

        assertThrows(IllegalArgumentException.class,
                () -> wagerService.createWager(null, 100d, outcomeOdd));

        assertThrows(IllegalArgumentException.class,
                () -> wagerService.createWager(player, null, outcomeOdd));

        assertThrows(IllegalArgumentException.class,
                () -> wagerService.createWager(player, 100d, null));
    }

    @Test
        // what if wagers not found +
        // what if result has no outcomes +
        // this index fails +
    void resultApplyTest() {
        Double balance = player.getBalance();
        Result result = createResult();

//        when(wagerRepository.findAll())
//                .thenReturn(Collections.singletonList(createWager(player)));
        assertTrue(wagerService.createWager(player, amount, createOutcomeOdd()));

        wagerService.applyResultOfEvent(result);
        assertEquals((balance - amount) + amount * coefficient, player.getBalance().doubleValue());
    }

    @Test
    void applyResultWithoutWagersTest() {
        Double balance = player.getBalance();
        Result result = createResult();

        when(wagerRepository.findAll())
                .thenReturn(Collections.emptyList());

        wagerService.applyResultOfEvent(result);
        assertEquals(balance, player.getBalance());
    }

    @Test
    void applyResultWithoutOutcome() {
        Result result = new Result(Collections.emptyList());

        wagerService.applyResultOfEvent(result);
    }

    private Result createResult() {
        OutcomeOdd outcomeOdd = createOutcomeOdd();
        return new Result(Collections.singletonList(outcomeOdd.getOutcome()));
    }

    private OutcomeOdd createOutcomeOdd() {
        Outcome dynamo = new Outcome("Dynamo");
        LocalDateTime time1 = LocalDateTime.of(2018, 1, 1, 1, 1);
        OutcomeOdd odd = new OutcomeOdd(coefficient, time1, time1.plusHours(2), dynamo);
        dynamo.getOutcomeOdds().add(odd);
        return odd;
    }

    public Wager createWager(Player player) {
        return new Wager(player, createOutcomeOdd(), amount, player.getCurrency(), LocalDateTime.now());
    }
}