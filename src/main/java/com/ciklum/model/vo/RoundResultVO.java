package com.ciklum.model.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY)
public class RoundResultVO {
    
    // choice for player 1
    private String p1Choice;
    // choice for player 2
    private String p2Choice;
    // winner name:
    private String winner;

    public RoundResultVO(String p1Choice, String p2Choice, String winner) {
        this.p1Choice = p1Choice;
        this.p2Choice = p2Choice;
        this.winner = winner;
    }
    /**
     * @return the p1Choice
     */
    public String getP1Choice() {
        return p1Choice;
    }

    /**
     * @param p1Choice the p1Choice to set
     */
    public void setP1Choice(String p1Choice) {
        this.p1Choice = p1Choice;
    }

    /**
     * @return the p2Choice
     */
    public String getP2Choice() {
        return p2Choice;
    }

    /**
     * @param p2Choice the p2Choice to set
     */
    public void setP2Choice(String p2Choice) {
        this.p2Choice = p2Choice;
    }

    /**
     * @return the winner
     */
    public String getWinner() {
        return winner;
    }

    /**
     * @param winner the winner to set
     */
    public void setWinner(String winner) {
        this.winner = winner;
    }

    
}
