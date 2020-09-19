package com.ciklum.service;

import java.util.List;

import com.ciklum.model.element.Rock;
import com.ciklum.model.element.Scissors;
import com.ciklum.model.game.Game;
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

        Player player1 = new Player(player1Name, new AlwaysRockStrategy(), new Rock());
        Player player2 = new Player(player2Name, new RandomStrategy(), new Scissors());

        Game game = new Game(server, userName, player1, player2);

        game.chooseNewElementsForEachPlayer();
        
        return game.playRound();
    }

    public List<RoundResult> getRounds(String userName) {
        
        return server.getRounds(userName);
    }
}

