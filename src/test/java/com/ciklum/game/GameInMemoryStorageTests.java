package com.ciklum.game;

import com.ciklum.game.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameInMemoryStorageTests {

    GameInMemoryStorage storage;

    @BeforeEach
    void setup() {
        storage = new GameInMemoryStorage();
    }

    @Test
    void shouldReturnSavedGame() {
        Game expectedGame = createExampleGame();
        assertEquals(expectedGame, storage.saveGame(expectedGame));
    }

    @Test
    void shouldReturnTotalNumberOfRounds() {
        saveTwoGamesToStorage();
        assertEquals(8, storage.getTotalNumberOfRounds());
    }

    @Test
    void shouldReturnNumberOfRoundsThatFirstUserWins() {
        saveTwoGamesToStorage();
        assertEquals(2, storage.getNumberOfGivenResultInWholeGames(RoundResult.FIRST_PLAYER_WINS));
    }

    @Test
    void shouldReturnNumberOfRoundsThatSecondUserWins() {
        saveTwoGamesToStorage();
        assertEquals(4, storage.getNumberOfGivenResultInWholeGames(RoundResult.SECOND_PLAYER_WINS));
    }

    @Test
    void shouldReturnNumberOfRoundsThatDraw() {
        saveTwoGamesToStorage();
        assertEquals(2, storage.getNumberOfGivenResultInWholeGames(RoundResult.DRAW));
    }

    private void saveTwoGamesToStorage() {
        Game expectedGame1 = createExampleGame();
        storage.saveGame(expectedGame1);
        Game expectedGame2 = createExampleGame();
        storage.saveGame(expectedGame2);
    }

    private Game createExampleGame() {
        Game expectedGame = new Game();
        expectedGame.addRound(new Round(GameElement.ROCK, GameElement.PAPER, RoundResult.SECOND_PLAYER_WINS));
        expectedGame.addRound(new Round(GameElement.ROCK, GameElement.SCISSORS, RoundResult.FIRST_PLAYER_WINS));
        expectedGame.addRound(new Round(GameElement.ROCK, GameElement.ROCK, RoundResult.DRAW));
        expectedGame.addRound(new Round(GameElement.ROCK, GameElement.PAPER, RoundResult.SECOND_PLAYER_WINS));
        return expectedGame;
    }
}
