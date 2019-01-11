package com.training.sportbetting.service;

import com.training.sportbetting.controller.form.PlayerForm;
import com.training.sportbetting.domain.user.Player;
import com.training.sportbetting.domain.user.User;
import com.training.sportbetting.repository.jpa.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * Service class used manipulation with users.
 */
@Service
public class UserService {

    private PlayerRepository playerRepository;

    @Autowired
    public UserService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * If amount less or equals to player balance, player balance will be reduced.
     * @param player Player which balance will be reduced.
     * @param amount Amount of money to subtract from the player balance.
     */
    public void subtractBill(Player player, double amount) {
        checkAmount(amount);

        Double balance = player.getBalance();
        checkState(balance - amount >= 0, "Amount of money must be less or equals to %s", balance);

        player.setBalance(balance - amount);
        playerRepository.save(player);
    }

    /**
     * Method for increasing player balance.
     * @param player Player which balance will be increased.
     * @param amount Amount of money to add to the player balance.
     */
    public void increaseBill(Player player, double amount) {
        checkAmount(amount);

        Double balance = player.getBalance();
        checkState(balance + amount <= Player.MAX_BALANCE,
                "Player balance must me lower or equals to %s", Player.MAX_BALANCE);

        player.setBalance(balance + amount);
        playerRepository.save(player);
    }

    /**
     * Find user in repository be their email.
     * @param email User email.
     * @return If user in a repository, optional will contains user else null.
     */
    public Player findPlayerByEmail(String email) {
        User user = playerRepository
                .findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with email %s not found.", email)));

        checkState(user.getClass() == Player.class, "User with email %s is not a player.", email);

        return (Player) user;
    }

    private void checkAmount(double amount) {
        checkArgument(amount >= Player.MIN_ACCURACY,"Amount of money must be bigger than $s", Player.MIN_ACCURACY);
        checkArgument(amount <= Player.MAX_BALANCE, "Amount of money must me lower or equals to %s", Player.MAX_BALANCE);
    }

    @Transactional
    public void updateUser(PlayerForm playerForm) {
        Optional<Player> playerOptional = playerRepository.findByAccountNumber(playerForm.getId());

        var player = playerOptional
                .orElseThrow(() -> new IllegalArgumentException(String.format("Player with acoount number %d not found.", playerForm.getId())));

        player.setName(playerForm.getName());
        player.setDateOfBirth(LocalDate.parse(playerForm.getBDate()));

        int index = Player.CURRENCIES.indexOf(Currency.getInstance(playerForm.getCurrency()));
        player.setCurrency(Player.CURRENCIES.get(index));

        playerRepository.save(player);
    }
}
