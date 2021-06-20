package com.ciklum.game;

import com.ciklum.game.model.*;
import com.ciklum.game.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class GameServiceTests {

	@Autowired
	GameService gameService;

	GameInMemoryStorage gameStorageMock;
	Game game;
	Game gameOfService;

	@BeforeEach
	void setUp() {
		gameStorageMock = mock(GameInMemoryStorage.class);
	}

	@Test
	void returnsGameAfterPlayingRound() {
		verifyGivenNumberOfRoundCreation(1);
	}

	@Test
	void shouldReturnTotalNumberOfRoundsWithoutRestart() {
		when(gameStorageMock.getTotalNumberOfRounds()).thenReturn(5);
		Integer actual = gameService.getTotalRoundsPlayed();
		assertEquals(5, actual);
	}

	@Test
	void shouldReturnTotalNumberOfRoundsWithRestart() {
		verifyGivenNumberOfRoundCreation(5);
		when(gameStorageMock.saveGame(any())).thenReturn(game);
		gameService.restartGame();
		verifyGivenNumberOfRoundCreation(2);
		when(gameStorageMock.getTotalNumberOfRounds()).thenReturn(7);
		assertEquals(7, gameService.getTotalRoundsPlayed());
	}

	private void verifyGivenNumberOfRoundCreation(int numberOfRounds) {
		try(MockedStatic<GameElement> mockedRandomGameElementCreator = mockStatic(GameElement.class)) {
			mockedRandomGameElementCreator.when(GameElement::getRandomRockPaperScissors).thenReturn(GameElement.PAPER);
			createGivenNumberOfRoundsInGame(numberOfRounds);
			assertEquals(game, gameOfService);
		}
	}

	private void createGivenNumberOfRoundsInGame(int numberOfRounds) {
		game = new Game();
		for(int i=0; i< numberOfRounds; i++) {
			Round round = new Round(GameElement.ROCK, GameElement.PAPER, RoundResult.SECOND_PLAYER_WINS);
			game.addRound(round);
			gameOfService = gameService.createRound();
		}
	}
}
