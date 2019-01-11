package com.training.sportbetting.service;

import com.training.sportbetting.domain.user.Admin;
import com.training.sportbetting.domain.user.Player;
import com.training.sportbetting.repository.jpa.PlayerRepository;
import com.training.sportbetting.repository.jpa.SportEventRepository;
import com.training.sportbetting.utils.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private UserService userService;

    private Player player;
    private double amount = 100d;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        when(playerRepository.save(any()))
                .then(invocationOnMock -> {
                    player = invocationOnMock.getArgument(0);
                    return player;
                });

        DataGenerator.generateData(mock(SportEventRepository.class), playerRepository, mock(WagerService.class), mock(SportEventService.class));

        when(playerRepository.findByEmail("email@mail.com"))
                .thenReturn(Optional.of(player));

        when(playerRepository.findByEmail("admin@mail.com"))
                .thenReturn(Optional.of(new Admin("admin@mail.com", "", null)));
    }

    @Test
    void findAvailableUserTest() {
        Player player = userService.findPlayerByEmail("email@mail.com");
        assertNotNull(player);
    }

    @Test
    void findNotAvailableUserTest() {
        assertThrows(IllegalArgumentException.class,
                () -> userService.findPlayerByEmail("anotherEmail@mail.com"));
    }

    @Test
    void findAvailableNotPlayerTest() {
        assertThrows(IllegalStateException.class,
                () -> userService.findPlayerByEmail("admin@mail.com"));
    }

    @Test
        // Does it work with super high values? (like 100 billion, Double.MAX_VALUE) Are there limits for your balance? +
        // Does it work with super low values? (0.001d) +
        // What if player had zero balance? +
        // What if player's balance is null? -
        // What if we add a negative number? +
    void increaseBill() {
        Double balance = player.getBalance();

        userService.increaseBill(player, amount);

        assertEquals(balance + amount, player.getBalance().doubleValue());
    }

    @Test
    void extraHighValueTest() {
        assertThrows(IllegalArgumentException.class, () -> userService.increaseBill(player, Double.MAX_VALUE));
        assertThrows(IllegalArgumentException.class, () -> userService.subtractBill(player, Double.MAX_VALUE));
    }

    @Test
    void extraLowValueTest() {
        assertThrows(IllegalArgumentException.class, () -> userService.increaseBill(player, 0.001d));
        assertThrows(IllegalArgumentException.class, () -> userService.subtractBill(player, 0.001d));
        assertThrows(IllegalArgumentException.class, () -> userService.subtractBill(player, Double.MIN_VALUE));
        assertThrows(IllegalArgumentException.class, () -> userService.increaseBill(player, Double.MIN_VALUE));
    }

    @Test
    void negativeValuesTest() {
        assertThrows(IllegalArgumentException.class, () -> userService.increaseBill(player, -1d));
        assertThrows(IllegalArgumentException.class, () -> userService.subtractBill(player, -1d));
    }

    @Test
        // Same about null value of balance, then what? -
        // Same about extreme numbers +
        // Zero balance, zero amount +
        // What if amount is NaN? Infinity? Will you get the expected behavior? +
        // What if we subtract a negative number? +
    void validSubtractBill() {
        Double balance = player.getBalance();

        userService.subtractBill(player, amount);

        assertEquals(balance - amount, player.getBalance().doubleValue());
    }

    @Test
    void extremeValuesSubtractTest() {
        assertThrows(IllegalArgumentException.class,
                () -> userService.subtractBill(player, Double.NEGATIVE_INFINITY));

        assertThrows(IllegalArgumentException.class,
                () -> userService.subtractBill(player, Double.POSITIVE_INFINITY));

        assertThrows(IllegalArgumentException.class,
                () -> userService.subtractBill(player, Double.MAX_VALUE));

        assertThrows(IllegalArgumentException.class,
                () -> userService.subtractBill(player, Double.MIN_VALUE));

        assertThrows(IllegalArgumentException.class,
                () -> userService.subtractBill(player, Double.MIN_NORMAL));
    }

    @Test
    void nonValidSubtractBill() {
        player.setBalance(0d);

        assertThrows(IllegalStateException.class,
                () -> userService.subtractBill(player, 1));
    }
}