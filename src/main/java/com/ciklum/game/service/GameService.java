package com.ciklum.game.service;

import com.ciklum.game.model.*;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Arrays;
import java.util.List;

@Service
@SessionScope
public class GameService {
    private static final GameElement PREDEFINED_FIRST_USER_SELECTION = GameElement.ROCK;
    private static final String GAME_STORAGE_TYPE = "in_memory";
    private final GameStorage gameStorage = GameStorageFactory.getGameStorage(GAME_STORAGE_TYPE);
    private Game game = new Game();

    private final ImmutableMap<List<GameElement>,RoundResult> possibleGameResults =
            new ImmutableMap.Builder<List<GameElement>,RoundResult>()
                    .put(Arrays.asList(GameElement.ROCK, GameElement.SCISSORS), RoundResult.FIRST_PLAYER_WINS)
                    .put(Arrays.asList(GameElement.ROCK, GameElement.PAPER), RoundResult.SECOND_PLAYER_WINS)
                    .put(Arrays.asList(GameElement.SCISSORS, GameElement.PAPER), RoundResult.FIRST_PLAYER_WINS)
                    .put(Arrays.asList(GameElement.SCISSORS, GameElement.ROCK), RoundResult.SECOND_PLAYER_WINS)
                    .put(Arrays.asList(GameElement.PAPER, GameElement.SCISSORS), RoundResult.SECOND_PLAYER_WINS)
                    .put(Arrays.asList(GameElement.PAPER, GameElement.ROCK), RoundResult.FIRST_PLAYER_WINS)
                    .build();

    public Game createRound() {
        GameElement secondUserRandomSelection = GameElement.getRandomRockPaperScissors();
        RoundResult result =
                (secondUserRandomSelection == PREDEFINED_FIRST_USER_SELECTION) ? RoundResult.DRAW :
                        possibleGameResults.get(Arrays.asList(PREDEFINED_FIRST_USER_SELECTION, secondUserRandomSelection));
        Round lastRound = new Round(PREDEFINED_FIRST_USER_SELECTION, secondUserRandomSelection, result);
        game.addRound(lastRound);
        return game;
    }

    public void restartGame() {
        resetOldGame();
    }

    public Integer getTotalRoundsPlayed() {
        if(!game.getRounds().isEmpty())
            resetOldGame();
        return gameStorage.getTotalNumberOfRounds();
    }

    public Integer getNumberOfGivenResultInAllGames(RoundResult result) {
        if(!game.getRounds().isEmpty())
            resetOldGame();
        return gameStorage.getNumberOfGivenResultInWholeGames(result);
    }

    private void resetOldGame() {
        gameStorage.saveGame(game);
        game = new Game();
    }
}
