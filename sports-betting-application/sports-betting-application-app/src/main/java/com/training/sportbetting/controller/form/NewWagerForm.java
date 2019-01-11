package com.training.sportbetting.controller.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewWagerForm {
    private int outcomeId;
    private double amount;
}
