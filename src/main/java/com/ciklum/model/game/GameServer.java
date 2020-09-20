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
    
    private long totalRoundsPlayed = 0;
    private long totalWinsP1 = 0;
    private long totalWinsP2 = 0;
    private long totalTies = 0;



    public GameServer() {
        // begin server with empty memory map
        this.games = new HashMap<>();
    }

    /**
     * @return the roundsPlayed
     */
    public int getRoundsPlayedForUser(String userName) {
        return this.games.getOrDefault(userName, new ArrayList<>()).size();
    }

    private void accumulateStatsForResult(RoundResult result) {
        String whoWon = result.getStatsText();

        this.totalRoundsPlayed++;

        if (whoWon.equals(GameStats.TIE_RESULT)) {
            this.totalTies++;
        } else if (whoWon.equals(GameStats.PLAYER1_WON)) {
            this.totalWinsP1++;
        } else {
            this.totalWinsP2++;
        }
    }

    /**
     * @return the roundsPlayed
     */
    // public long getTotalRoundsPlayed() {
    //     // Collection<List<RoundResult>> list = this.games.values();
    //     // return list.stream().flatMap(List::stream).count();

    //     return this.totalRoundsPlayed;
    // }

    // public long getTotalWinsForPlayer1() {
    //     // Collection<List<RoundResult>> list = this.games.values();

    //     // List<RoundResult> rounds = list.stream().flatMap(List::stream).collect(Collectors.toList());

    //     // long totalWinsP1 = rounds.stream().filter( (final RoundResult result) -> result.getStatsText().equals("Player1")).count(); // NOSONAR

    //     // return totalWinsP1;
    //     return this.totalWinsP1;
    // }

    // public long getTotalWinsForPlayer2() {
    //     return this.totalWinsP2;
    // }

    // public long getTotalTies() {
    //     // Collection<List<RoundResult>> list = this.games.values();

    //     // List<RoundResult> rounds = list.stream().flatMap(List::stream).collect(Collectors.toList());

    //     // long totalWinsP1 = rounds.stream().filter( (final RoundResult result) -> result.getStatsText().equals("Tie")).count(); // NOSONAR

    //     // return totalWinsP1;
    //     return this.totalTies;
    // }

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

        return new GameStats(this.totalRoundsPlayed, this.totalWinsP1, totalWinsP2, totalTies);
    }
}
