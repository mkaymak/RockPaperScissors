package com.ciklum.game.model;

import java.util.ArrayList;
import java.util.List;

public class GameInMemoryStorage implements GameStorage {
    private List<Game> gameMap = new ArrayList<>();

    @Override
    public Game saveGame(Game game) {
        gameMap.add(game);
        return game;
    }

    @Override
    public Integer getTotalNumberOfRounds() {
        return gameMap.stream()
                      .map(Game::getNumberOfRounds)
                      .mapToInt(Integer::valueOf)
                      .sum();
    }

    @Override
    public Integer getNumberOfGivenResultInWholeGames(RoundResult result) {
        if(result == RoundResult.DRAW)
            return gameMap.stream().mapToInt(Game::getNumberOfRoundsDrawing).sum();
        else if (result == RoundResult.FIRST_PLAYER_WINS)
            return gameMap.stream().mapToInt(Game::getNumberOfRoundsThatFirstUserWins).sum();
        return gameMap.stream().mapToInt(Game::getNumberOfRoundsThatSecondUserWins).sum();
    }
}

