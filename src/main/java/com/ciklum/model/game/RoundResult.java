package com.ciklum.model.game;

import java.util.Optional;

import com.ciklum.model.element.Element;
import com.ciklum.model.player.Player;
import com.ciklum.model.vo.RoundResultVO;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RoundResult {
    
    private final Element e1;
    private final Element e2;
    private final Optional<Player> winner;
    private final String statsText;

    public RoundResult(Element e1, Element e2, Optional<Player> winner, String statsText) {
        this.e1 = e1;
        this.e2 = e2;
        this.winner = winner;
        this.statsText = statsText;
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

    public RoundResultVO getVO() {
        String winnerString = "Draw";
        Optional<Player> optWinner = this.getWinner();
        if (optWinner.isPresent()) {
            winnerString = optWinner.get().getName();
        }
        return new RoundResultVO(e1.getElementName(),
                                 e2.getElementName(),
                                 winnerString);
    }

    /**
     * @return the statsText
     */
    public String getStatsText() {
        return statsText;
    }
}
