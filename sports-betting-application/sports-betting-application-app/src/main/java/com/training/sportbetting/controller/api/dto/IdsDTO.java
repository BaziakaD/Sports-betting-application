package com.training.sportbetting.controller.api.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdsDTO {

    private Set<Integer> ids;
}
