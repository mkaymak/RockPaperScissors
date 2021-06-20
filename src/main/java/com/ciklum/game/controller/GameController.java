package com.ciklum.game.controller;

import com.ciklum.game.model.RoundResult;
import com.ciklum.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    GameService gameService;

    GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/numberOfRounds")
    public Integer getTotalRounds() {
        return gameService.getTotalRoundsPlayed();
    }

    @GetMapping("/numberOfGivenResult")
    public Integer getNumberOfGivenResults(@RequestParam("result") RoundResult roundResult) {
        return gameService.getNumberOfGivenResultInAllGames(roundResult);
    }

}
