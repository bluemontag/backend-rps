package com.ciklum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.ciklum.model.player.*;
import com.ciklum.model.game.*;
import com.ciklum.model.element.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BasicLogicTest {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BasicLogicTest.class);

    @BeforeEach
    public void beginTest() {
        logger.info("================================================================================");
    }

    @AfterEach
    public void endTest() {
        logger.info("================================================================================");
    }

    @Test
    public void rockBeatsScissorsTest() {

        Player p1 = new Player("Juan", () -> new Rock());
        Player p2 = new Player("Pedro", () -> new Scissors());
        GameLogic game = new GameLogic(new GameServer(), "Mathiew", p1, p2);

        RoundResult result = game.playRound();

        // there must be a winner
        assertTrue(result.getWinner().isPresent(), "There is not a winner");
        
        // and must be p1
        assertEquals(result.getWinner().get(), p1, "There is a problem with the outcome");

        logger.info("The test rockBeatsScissorsTest finished ok.");
    }

    @Test
    public void scissorsIsBeatenByRockTest() {

        Player p1 = new Player("Juan", () -> new Scissors());
        Player p2 = new Player("Pedro", () -> new Rock());
        GameLogic game = new GameLogic(new GameServer(), "Mathiew", p1, p2);

        RoundResult result = game.playRound();

        // there must be a winner
        assertTrue(result.getWinner().isPresent(), "There is not a winner");
        
        // and must be p1
        assertEquals(result.getWinner().get(), p2, "There is a problem with the outcome");

        logger.info("The test scissorsIsBeatenByRockTest finished ok.");
    }

    @Test
    public void paperBeatsRockTest() {

        Player p1 = new Player("Juan", () -> new Paper());
        Player p2 = new Player("Pedro", () -> new Rock());
        GameLogic game = new GameLogic(new GameServer(), "Mathiew", p1, p2);

        RoundResult result = game.playRound();
        // there must be a winner
        assertTrue(result.getWinner().isPresent(), "There is not a winner");
        
        // and must be p1
        assertEquals(result.getWinner().get(), p1, "There is a problem with the outcome");

        logger.info("The test paperBeatsRockTest finished ok.");
    }

    @Test
    public void rockIsBeatenByPaperTest() {

        Player p1 = new Player("Juan", () -> new Rock());
        Player p2 = new Player("Pedro", () -> new Paper());
        GameLogic game = new GameLogic(new GameServer(), "Mathiew", p1, p2);

        RoundResult result = game.playRound();

        // there must be a winner
        assertTrue(result.getWinner().isPresent(), "There is not a winner");
        
        // and must be p1
        assertEquals(result.getWinner().get(), p2, "There is a problem with the outcome");

        logger.info("The test rockIsBeatenByPaperTest finished ok.");
    }

    @Test
    public void scissorsBeatsPaperTest() {

        Player p1 = new Player("Juan", () -> new Scissors());
        Player p2 = new Player("Pedro", () -> new Paper());
        GameLogic game = new GameLogic(new GameServer(), "Mathiew", p1, p2);

        RoundResult result = game.playRound();

        // there must be a winner
        assertTrue(result.getWinner().isPresent(), "There is not a winner");

        // and must be p1
        assertEquals(result.getWinner().get(), p1, "There is a problem with the outcome");

        logger.info("The test scissorsBeatsPaperTest finished ok.");
    }

    @Test
    public void paperIsBeatenByScissorsTest() {

        Player p1 = new Player("Juan", () -> new Paper());
        Player p2 = new Player("Pedro", () -> new Scissors());
        GameLogic game = new GameLogic(new GameServer(), "Mathiew", p1, p2);

        RoundResult result = game.playRound();

        // there must be a winner
        assertTrue(result.getWinner().isPresent(), "There is not a winner");
        
        // and must be p1
        assertEquals(result.getWinner().get(), p2, "There is a problem with the outcome");

        logger.info("The test paperIsBeatenByScissorsTest finished ok.");
    }

    @Test
    public void tieTest() {

        // create players with always scissors strategy:
        Player p1 = new Player("Juan", () -> new Scissors());
        Player p2 = new Player("Pedro", () -> new Scissors());
        GameLogic game = new GameLogic(new GameServer(), "Mathiew", p1, p2);
        
        RoundResult result = game.playRound();

        // there must be a tie
        assertTrue(!result.getWinner().isPresent(), "There should not be a winner (its a tie)");

        p1 = new Player("Juan", () -> new Paper());
        p2 = new Player("Pedro", () -> new Paper());

       result = game.playRound();

        // there must be a tie
        assertTrue(!result.getWinner().isPresent(), "There should not be a winner (its a tie)");

        p1 = new Player("Juan", () -> new Rock());
        p2 = new Player("Pedro", () -> new Rock());

       result = game.playRound();

        // there must be a tie
        assertTrue(!result.getWinner().isPresent(), "There should not be a winner (its a tie)");

        logger.info("The test tieTest finished ok.");
    }

}
