package com.ciklum.model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The GameMemory is the memory for all the outcomes for all the different users.
 * For each userName, it contains the list of outcomes for all the games played from the 
 *  beggining of the GameServer creation.
 * 
 */
public class ServerMemory {
    
    private Map<String, List<RoundResult>> games;
    
    private long totalRoundsPlayed = 0;
    private long totalWinsP1 = 0;
    private long totalWinsP2 = 0;
    private long totalDraws = 0;



    public ServerMemory() {
        // begin server with empty Concurrent HashMap
        this.games = new ConcurrentHashMap<>();
    }

    /**
     * @return the roundsPlayed
     */
    public int getRoundsPlayedForUser(String userName) {
        return this.games.getOrDefault(userName, new ArrayList<>()).size();
    }

    private void accumulateStatsForResult(RoundResult result) {

        this.totalRoundsPlayed++;

        if (result.isDraw()) {
            this.totalDraws++;
        } else if (result.isPlayer1Winner()) {
            this.totalWinsP1++;
        } else {
            this.totalWinsP2++;
        }
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

        this.accumulateStatsForResult(result);
    }

    public GameStats getGameStats() {

        return new GameStats(this.totalRoundsPlayed, this.totalWinsP1, totalWinsP2, totalDraws);
    }

    public boolean clear() {
        this.games.clear();
        this.totalDraws = 0;
        this.totalRoundsPlayed = 0;
        this.totalWinsP1 = 0;
        this.totalWinsP2 = 0;

        return true;
    }
}
