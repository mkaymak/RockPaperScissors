package com.ciklum.game.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Round {
    private final GameElement firstUserSelection;
    private final GameElement secondUserSelection;
    private final RoundResult result;
}
