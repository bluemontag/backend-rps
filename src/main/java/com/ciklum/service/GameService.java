package com.ciklum.service;

import java.util.List;

import com.ciklum.model.game.Game;
import com.ciklum.model.game.ServerMemory;
import com.ciklum.model.game.GameStats;
import com.ciklum.model.game.RoundResult;
import com.ciklum.model.player.AlwaysRockStrategy;
import com.ciklum.model.player.Player;
import com.ciklum.model.player.RandomStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GameService.class);

    @Autowired
    private ServerMemory serverMemory;

    /**
     * Plays a round with the predefined strategies (see exercise)
     *
     * @param userName
     * @param player1Name
     * @param player2Name
     * @return
     */
    public RoundResult playRound(String userName, String player1Name, String player2Name) {

        logger.info("GameService.playRound() called.");

        Player player1 = new Player(player1Name, new AlwaysRockStrategy());
        Player player2 = new Player(player2Name, new RandomStrategy());

        Game game = new Game(player1, player2);

        RoundResult result = game.playRound();

        this.serverMemory.addNewResult(userName, result);

        return result;
    }

    public List<RoundResult> getRoundsForUser(String userName) {

        logger.info("GameService.getRoundsForUser() called.");
        return serverMemory.getRounds(userName);
    }

    public GameStats getGameStats() {
        
        logger.info("GameService.getGameStats() called.");
        return serverMemory.getGameStats();
    }

    public void clearServerMemory() {
        logger.info("GameService.clearServerMemory() called.");
        this.serverMemory.clear();
    }
}

