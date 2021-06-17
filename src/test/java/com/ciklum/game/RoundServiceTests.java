package com.ciklum.game;

import com.ciklum.game.model.GameElement;
import com.ciklum.game.model.Round;
import com.ciklum.game.model.RoundResult;
import com.ciklum.game.service.RoundService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

@SpringBootTest
class RoundServiceTests {
	@Autowired
	RoundService roundService;

	@Test
	void returnsPlayRound() {
		try(MockedStatic<GameElement> mockedRandomGameElementCreator = mockStatic(GameElement.class)) {
			mockedRandomGameElementCreator.when(GameElement::getRandomRockPaperScissors).thenReturn(GameElement.PAPER);
			Round round = new Round(GameElement.ROCK, GameElement.PAPER, RoundResult.SECOND_PLAYER_WINS);
			assertEquals(round, roundService.createRound());
		}
	}
}
