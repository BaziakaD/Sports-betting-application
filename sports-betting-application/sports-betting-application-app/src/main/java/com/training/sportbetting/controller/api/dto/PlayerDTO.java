package com.training.sportbetting.controller.api.dto;

import com.training.sportbetting.domain.user.Player;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.Currency;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerDTO {

    private String name;
    private Double balance;
    private Currency currency;
    private Integer accountNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    public static PlayerDTO dtoFromPlayer(Player player) {
        return new PlayerDTOBuilder()
                .name(player.getName())
                .balance(player.getBalance())
                .currency(player.getCurrency())
                .accountNumber(player.getAccountNumber())
                .birthDate(player.getDateOfBirth())
                .build();
    }
}
