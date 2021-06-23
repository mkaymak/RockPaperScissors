package com.ciklum.game.model;

public interface GameStorage {
    Game saveGame(Game game);
    Integer getTotalNumberOfRounds();
    Integer getNumberOfRoundsWithGivenResultInAllGames(RoundResult result);
}
