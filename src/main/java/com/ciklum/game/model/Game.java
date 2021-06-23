package com.ciklum.game.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Game {
    private List<Round> rounds = new ArrayList<>();
    @JsonIgnore
    private int numberOfRoundsThatFirstUserWins = 0;
    @JsonIgnore
    private int numberOfRoundsThatSecondUserWins = 0;
    @JsonIgnore
    private int numberOfRoundsDrawing = 0;

    public void addRound(Round round) {
        if (round.getResult() == RoundResult.DRAW) numberOfRoundsDrawing++;
        else if(round.getResult() == RoundResult.FIRST_PLAYER_WINS) numberOfRoundsThatFirstUserWins++;
        else numberOfRoundsThatSecondUserWins++;
        rounds.add(round);
    }

    public Integer getNumberOfRounds() {
        return rounds.size();
    }

    public int getNumberOfRoundsWithGivenResult(RoundResult result) {
        return (int) rounds.stream().filter(round -> round.getResult() == result).count();
    }
}