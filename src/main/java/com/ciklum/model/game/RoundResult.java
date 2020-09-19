package com.ciklum.model.game;

import java.util.Optional;
import com.ciklum.model.player.Player;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RoundResult {
    
    private final Player player1;
    private final Player player2;
    private final Optional<Player> winner;
    
    public RoundResult(Player player1, Player player2, Optional<Player> winner) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
    }

    /**
     * @return the player1
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * @return the player2
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * This method returns the winner,
     * or and empty optional in case of a Tie.
     * 
     * @return 
     */
    public Optional<Player> getWinner() {
        return this.winner;
    }

}
