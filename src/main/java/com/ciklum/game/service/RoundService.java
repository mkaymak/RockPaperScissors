package com.ciklum.game.service;

import com.ciklum.game.model.GameElement;
import com.ciklum.game.model.Round;
import com.ciklum.game.model.RoundResult;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
public class RoundService {

    public Round createRound() {

        return new Round(GameElement.ROCK,
                         GameElement.PAPER,
                         RoundResult.SECOND_PLAYER_WINS);
    }
}
