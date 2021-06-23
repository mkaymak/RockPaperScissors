package com.ciklum.game;

import com.ciklum.game.controller.GameController;
import com.ciklum.game.model.Game;
import com.ciklum.game.model.GameElement;
import com.ciklum.game.model.Round;
import com.ciklum.game.model.RoundResult;
import com.ciklum.game.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameController.class)
class GameControllerTests {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String TOTAL_NUMBER_OF_ROUNDS_SERVICE = "/games/numberOfRounds";
    private final String NUMBER_OF_GIVEN_RESULT_SERVICE = "/games/numberOfGivenResult";
    private final String PLAY_ROUND_SERVICE = "/game/round/play";
    private final String SERVICE_CALL_ERROR_MESSAGE = "Cannot call {} mockMvc service";

    @MockBean
    GameService gameService;

    @Autowired
    MockMvc mockMvc;

    Game expectedGame = new Game();

    @BeforeEach
    void setup() {
        when(gameService.getTotalRoundsPlayed()).thenReturn(10);
        when(gameService.getNumberOfGivenResultInAllGames(RoundResult.FIRST_PLAYER_WINS)).thenReturn(3);
        expectedGame.addRound(new Round(GameElement.ROCK, GameElement.PAPER, RoundResult.SECOND_PLAYER_WINS));
        when(gameService.createRound()).thenReturn(expectedGame);
    }

    @Test
    void shouldReturnTotalNumberOfRounds() {
        try {
            this.mockMvc.perform(get(TOTAL_NUMBER_OF_ROUNDS_SERVICE))
                        .andExpect(status().isOk())
                        .andExpect(content().string("10"));
        } catch (Exception e) {
            logger.error(SERVICE_CALL_ERROR_MESSAGE, TOTAL_NUMBER_OF_ROUNDS_SERVICE);
        }
    }

    @Test
    void shouldReturnNumberOfGivenResult() {
        try {
            this.mockMvc.perform(get(NUMBER_OF_GIVEN_RESULT_SERVICE)
                        .param("result", "FIRST_PLAYER_WINS"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("3")));
        } catch (Exception e) {
            logger.error(SERVICE_CALL_ERROR_MESSAGE, NUMBER_OF_GIVEN_RESULT_SERVICE);
        }
    }

    @Test
    void shouldReturnPlayedGame() {
        String gameJson = createGameJsonString(expectedGame);
        try {
            assert gameJson != null;
            this.mockMvc.perform(get(PLAY_ROUND_SERVICE))
                        .andExpect(content().json(gameJson));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String createGameJsonString(Game expectedGame) {
        ObjectMapper objMapper = new ObjectMapper();
        try {
            return objMapper.writeValueAsString(expectedGame);
        } catch (JsonProcessingException e) {
            logger.error("Cannot convert Game object to JSON string");
            return null;
        }
    }
}
