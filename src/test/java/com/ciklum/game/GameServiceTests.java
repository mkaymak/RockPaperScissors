package com.ciklum.game;

import com.ciklum.game.model.*;
import com.ciklum.game.service.GameService;
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
	private GameService gameService;

	private Game game;
	private Game gameOfService;

	@Test
	void returnsGameAfterPlayingRound() {
		try(MockedStatic<GameElement> mockedRandomGameElementCreator = mockStatic(GameElement.class)) {
			mockedRandomGameElementCreator.when(GameElement::getRandomRockPaperScissors).thenReturn(GameElement.PAPER);
			createGivenNumberOfRoundsInGame(1);
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
