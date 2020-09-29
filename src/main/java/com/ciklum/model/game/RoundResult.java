package com.ciklum.model.game;

import java.util.Optional;

import com.ciklum.model.shapes.Shape;
import com.ciklum.model.player.Player;
import com.ciklum.model.vo.RoundResultVO;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RoundResult {
    
    private final Shape s1;
    private final Shape s2;
    private final Optional<Player> winner;
    private final boolean isPlayer1TheWinner;
    private final boolean isPlayer2TheWinner;

    public RoundResult(Shape e1, Shape e2, Optional<Player> winner, boolean isPlayer1TheWinner, boolean isPlayer2TheWinner) {
        this.s1 = e1;
        this.s2 = e2;
        this.winner = winner;
        this.isPlayer1TheWinner = isPlayer1TheWinner;
        this.isPlayer2TheWinner = isPlayer2TheWinner;
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

    public boolean isPlayer1Winner() {
        return this.isPlayer1TheWinner;
    }

    public boolean isPlayer2Winner() {
        return this.isPlayer2TheWinner;
    }

    public boolean isDraw() {
        return !this.isPlayer1TheWinner && !this.isPlayer2TheWinner;
    }

    public RoundResultVO getVO() {
        String winnerString = "Draw";
        Optional<Player> optWinner = this.getWinner();
        if (optWinner.isPresent()) {
            winnerString = optWinner.get().getName();
        }
        return new RoundResultVO(s1.name(),
                                 s2.name(),
                                 winnerString);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((s1 == null) ? 0 : s1.hashCode());
        result = prime * result + ((s2 == null) ? 0 : s2.hashCode());
        result = prime * result + ( winner.hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof RoundResult))
            return false;

        RoundResult other = (RoundResult) obj;

        return this.s1.equals(other.s1) && this.s2.equals(other.s2) && this.winner.equals(other.winner);
    }


}
