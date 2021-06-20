package com.ciklum.game.controller;

import com.ciklum.game.model.Game;
import com.ciklum.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class RoundController {

    @Autowired
    GameService gameService;

    RoundController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/round/play")
    public Game playRandomRound() {
        return gameService.createRound();
    }

    @GetMapping("/restart")
    public void restartGame() {
        gameService.restartGame();
    }
}
