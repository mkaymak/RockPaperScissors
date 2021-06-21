package com.ciklum.game;

import com.ciklum.game.model.Game;
import com.ciklum.game.model.GameElement;
import com.ciklum.game.model.Round;
import com.ciklum.game.model.RoundResult;
import com.ciklum.game.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class GameControllerTests {

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
            this.mockMvc.perform(get("/games/numberOfRounds"))
                        .andExpect(status().isOk())
                        .andExpect((ResultMatcher) content().string("10"));
        } catch (Exception e) {
            //TODO: add logger
            e.printStackTrace();
        }
    }

    @Test
    void shouldReturnNumberOfGivenResult() {
        try {
            this.mockMvc.perform(get("/games/numberOfGivenResult")
                        .param("result", "FIRST_PLAYER_WINS"))
                        .andExpect(status().isOk())
                        .andExpect((ResultMatcher) content().string(containsString("3")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldReturnPlayedGame() {
        String gameJson = createGameJsonString(expectedGame);
        try {
            this.mockMvc.perform(get("/game/round/play"))
                        .andDo(print())
                        .andExpect((ResultMatcher) content().json(gameJson));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String createGameJsonString(Game expectedGame) {
        ObjectMapper objMapper = new ObjectMapper();
        try {
            return objMapper.writeValueAsString(expectedGame);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
