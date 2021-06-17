package com.ciklum.game.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Round {
    private GameElement firstUserSelection;
    private GameElement secondUserSelection;
    private RoundResult result;
}
