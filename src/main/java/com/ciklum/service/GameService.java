package com.ciklum.service;

import java.util.List;

import com.ciklum.model.game.GameLogic;
import com.ciklum.model.game.GameServer;
import com.ciklum.model.game.RoundResult;
import com.ciklum.model.player.AlwaysRockStrategy;
import com.ciklum.model.player.Player;
import com.ciklum.model.player.RandomStrategy;

import org.springframework.stereotype.Service;

@Service
public class GameService {

    // the memory of the current server game
    private GameServer server = new GameServer();


    public RoundResult playRound(String userName, String player1Name, String player2Name) {

        Player player1 = new Player(player1Name, new AlwaysRockStrategy());
        Player player2 = new Player(player2Name, new RandomStrategy());

        GameLogic game = new GameLogic(server, userName, player1, player2);

        return game.playRound();
    }

    public List<RoundResult> getRoundsForUser(String userName) {
        
        return server.getRounds(userName);
    }
}

