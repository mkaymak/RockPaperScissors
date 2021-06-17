package com.ciklum.game.model;

import java.util.Random;

public enum GameElement {
    ROCK,
    PAPER,
    SCISSORS;

    private static final GameElement[] VALUES = values();
    private static final int SIZE = VALUES.length;
    private static final Random RANDOM = new Random();

    public static GameElement getRandomRockPaperScissors() {
        return VALUES[RANDOM.nextInt(SIZE)];
    }
}

