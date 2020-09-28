package com.ciklum.model.game;

import java.util.Optional;

import com.ciklum.model.element.Element;
import com.ciklum.model.player.Player;

/**
 * Game is an instance of a RPS Game
 * 
 * It contains the logic for playing one round of the game.
 * 
 */
public class Game {
    
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Game.class);

    // immutable fields
    private final Player player1;
    private final Player player2;


    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Plays a round for the elements e1 and e2,
     *  then adds the result to the server (for the current userName).
     * 
     * @return the result in case you need to test the value from the outside
     */
    private RoundResult playRoundWithElements(Element e1, Element e2) {

        logger.info("{} is playing with {}", this.player1.getName(), this.player2.getName());

        int outcome = e1.compareTo(e2);
        RoundResult result = null;

        switch (outcome) {
            case 0:
                // Tie        
                logger.info("There is a tie");
                result = new RoundResult(e1, e2, Optional.empty(), false, false);
                break;

            case -1:
                // The first element wins
                logger.info("{} beats {}", e1, e2);
                result = new RoundResult(e1, e2, Optional.of(this.player1), true, false);
                break;

            case 1:
                // The second element wins
                logger.info("{} beats {}", e2, e1);
                result = new RoundResult(e1, e2, Optional.of(this.player2), false, true);
                break;

            default:
                // raise exception or return null (there is some unexpected behaviour)
                return null;
        }

        return result;
    }

    /**
     * Choose an element for each user, according 
     * to their selected strategies, and plays a 
     * round with those elements.
     * 
     * @return the {@link RoundResult} that corresponds with the result of the round (winner or a tie)
     */
    public RoundResult playRound() {
        Element e1 = this.player1.chooseNewElement();
        Element e2 = this.player2.chooseNewElement();

        return this.playRoundWithElements(e1, e2);
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
}
