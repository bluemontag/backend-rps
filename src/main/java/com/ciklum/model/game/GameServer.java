package com.ciklum.model.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The GameServer is the memory for all the outcomes for all the different users.
 * For each userName, it contains the list of outcomes for all the games played from the 
 *  beggining of the GameServer creation.
 * 
 */
public class GameServer {
    
    private Map<String, List<RoundResult>> games;
    
    public GameServer() {
        // begin server with empty memory map
        this.games = new HashMap<>();
    }

    /**
     * @return the roundsPlayed
     */
    public int getRoundsPlayed(String userName) {
        return this.games.getOrDefault(userName, new ArrayList<>()).size();
    }

    /**
     * @return the rounds
     */
    public List<RoundResult> getRounds(String userName) {
        return this.games.getOrDefault(userName, new ArrayList<>());
    }

    /**
     * Add a round outcome to the user "userName"
     * 
     * @param userName
     * @param result
     */
    public void addNewResult(String userName, RoundResult result) {
        // begin a new round
        List<RoundResult> rounds = this.games.getOrDefault(userName, new ArrayList<>());

        rounds.add(result);

        this.games.put(userName, rounds);
    }

}
