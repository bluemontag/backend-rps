package com.ciklum.service;

import java.util.List;

import com.ciklum.model.game.Game;
import com.ciklum.model.game.ServerMemory;
import com.ciklum.model.game.GameStats;
import com.ciklum.model.game.RoundResult;
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
    public RoundResult playRound(String userName, Game game) {

        logger.info("GameService.playRound() called.");

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

    public boolean clearServerMemory() {
        logger.info("GameService.clearServerMemory() called.");
        
        return this.serverMemory.clear();
    }
}

