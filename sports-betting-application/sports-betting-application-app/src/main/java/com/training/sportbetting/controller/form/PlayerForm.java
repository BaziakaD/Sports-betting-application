package com.training.sportbetting.controller.form;

import com.training.sportbetting.domain.user.Player;
import lombok.*;

import java.util.Currency;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerForm {
    private int id;
    private String bDate;
    private String name;
    private List<Currency> currencies;
    private String currency;
    private Double balance;

    public static PlayerForm fromPlayer(Player player) {
        return new PlayerFormBuilder()
                .id(player.getAccountNumber())
                .bDate(player.getDateOfBirth().toString())
                .name(player.getName())
                .currencies(Player.CURRENCIES)
                .currency(player.getCurrency().toString())
                .balance(player.getBalance())
                .build();
    }
}
