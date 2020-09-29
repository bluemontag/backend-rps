package com.ciklum.model.game;

import java.util.Optional;

import com.ciklum.model.shapes.Shape;
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
     * Plays a round for the shapes s1 and s2,
     *  then adds the result to the server (for the current userName).
     * 
     * @return the result in case you need to test the value from the outside
     */
    private RoundResult playRoundWithElements(Shape s1, Shape s2) {

        logger.info("{} is playing with {}", this.player1.getName(), this.player2.getName());

        RoundResult result = null;

        if (s1 == s2) {
                // Tie        
                logger.info("There is a tie");
                result = new RoundResult(s1, s2, Optional.empty(), false, false);
        } else if ((s1 == Shape.ROCK && s2 == Shape.SCISSORS) ||
                   (s1 == Shape.PAPER && s2 == Shape.ROCK) ||
                   (s1 == Shape.SCISSORS && s2 == Shape.PAPER)
                   ) {
                // The first shape wins
                logger.info("{} beats {}", s1, s2);
                result = new RoundResult(s1, s2, Optional.of(this.player1), true, false);
        } else {
            // The second shape wins
            logger.info("{} beats {}", s2, s1);
            result = new RoundResult(s1, s2, Optional.of(this.player2), false, true);
        }

        return result;
    }

    /**
     * Choose an shape for each user, according 
     * to their selected strategies, and plays a 
     * round with those elements.
     * 
     * @return the {@link RoundResult} that corresponds with the result of the round (winner or a tie)
     */
    public RoundResult playRound() {
        Shape s1 = this.player1.chooseNewElement();
        Shape s2 = this.player2.chooseNewElement();

        return this.playRoundWithElements(s1, s2);
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
