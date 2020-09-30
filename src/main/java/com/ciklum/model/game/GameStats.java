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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (totalDraws ^ (totalDraws >>> 32));
        result = prime * result + (int) (totalRounds ^ (totalRounds >>> 32));
        result = prime * result + (int) (totalWinsP1 ^ (totalWinsP1 >>> 32));
        result = prime * result + (int) (totalWinsP2 ^ (totalWinsP2 >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        
        GameStats other = (GameStats) obj;
        
        return (totalDraws == other.totalDraws) && (totalRounds == other.totalRounds) &&
               (totalWinsP1 == other.totalWinsP1) && (totalWinsP2 == other.totalWinsP2);
    }
    
}
