package com.ciklum.model.game;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY)
public class GameStats {
    
    // some constants
    public static final String PLAYER1_WON = "Player1";
    public static final String PLAYER2_WON = "Player2";
    public static final String TIE_RESULT  = "Tie";

    // immutable state
    private final long totalRounds;
    private final long totalWinsP1;
    private final long totalWinsP2;
    private final long totalDraws;


    public GameStats(long totalRounds, long totalWinsP1, long totalWinsP2, long totalDraws) {
        this.totalRounds = totalRounds;
        this.totalWinsP1 = totalWinsP1;
        this.totalWinsP2 = totalWinsP2;
        this.totalDraws = totalDraws;
    }

    /**
     * @return the totalRounds
     */
    public long getTotalRounds() {
        return totalRounds;
    }

    /**
     * @return the totalWinsP1
     */
    public long getTotalWinsP1() {
        return totalWinsP1;
    }

    /**
     * @return the totalWinsP2
     */
    public long getTotalWinsP2() {
        return totalWinsP2;
    }

    /**
     * @return the totalDraws
     */
    public long getTotalDraws() {
        return totalDraws;
    }

    
}
