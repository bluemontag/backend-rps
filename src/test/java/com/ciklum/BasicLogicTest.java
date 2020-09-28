package com.ciklum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

        Game game = givenAGameOfPlayers(new Rock(), new Scissors());

        RoundResult result = game.playRound();

        assertWinnerPlayer1(result, game);

        logger.info("The test rockBeatsScissorsTest finished ok.");
    }

    // create test data to operate on
    private Game givenAGameOfPlayers(Element e1, Element e2) {
        // create 2 players with strategies "always e1 and always e2"
        Player p1 = new Player("Juan", () -> e1);
        Player p2 = new Player("Pedro", () -> e2);
        return new Game(p1, p2);
    }

    private void assertWinnerPlayer1(RoundResult result, Game game) {
        // there must be a winner
        assertTrue(result.getWinner().isPresent(), "There is not a winner");
        // and must be p1
        assertEquals(result.getWinner().get(), game.getPlayer1(), "There is a problem with the outcome");
    }

    @Test
    public void scissorsIsBeatenByRockTest() {

        Game game = givenAGameOfPlayers(new Scissors(), new Rock());

        RoundResult result = game.playRound();

        assertWinnerPlayer2(result, game);

        logger.info("The test scissorsIsBeatenByRockTest finished ok.");
    }

    private void assertWinnerPlayer2(RoundResult result, Game game) {
        // there must be a winner
        assertTrue(result.getWinner().isPresent(), "There is not a winner");
        // and must be p1
        assertEquals(result.getWinner().get(), game.getPlayer2(), "There is a problem with the outcome");
    }

    @Test
    public void paperBeatsRockTest() {

        Game game = givenAGameOfPlayers(new Paper(), new Rock());

        RoundResult result = game.playRound();

        assertWinnerPlayer1(result, game);

        logger.info("The test paperBeatsRockTest finished ok.");
    }

    @Test
    public void rockIsBeatenByPaperTest() {

        Game game = givenAGameOfPlayers(new Rock(), new Paper());

        RoundResult result = game.playRound();

        assertWinnerPlayer2(result, game);

        logger.info("The test rockIsBeatenByPaperTest finished ok.");
    }

    @Test
    public void scissorsBeatsPaperTest() {

        Game game = givenAGameOfPlayers(new Scissors(), new Paper());

        RoundResult result = game.playRound();

        assertWinnerPlayer1(result, game);

        logger.info("The test scissorsBeatsPaperTest finished ok.");
    }

    @Test
    public void paperIsBeatenByScissorsTest() {

        Game game = givenAGameOfPlayers(new Paper(), new Scissors());

        RoundResult result = game.playRound();

        assertWinnerPlayer2(result, game);

        logger.info("The test paperIsBeatenByScissorsTest finished ok.");
    }

    @Test
    public void scissorsDrawTest() {

        Game game = givenAGameOfPlayers(new Scissors(), new Scissors());

        RoundResult result = game.playRound();

        assertDrawResult(result);

        logger.info("The test scissorsDrawTest finished ok.");
    }

    public void papersDrawTest() {

        Game game = givenAGameOfPlayers(new Paper(), new Paper());

        RoundResult result = game.playRound();

        assertDrawResult(result);

        logger.info("The test paperDrawTest finished ok.");
    }

    public void rocksDrawTest() {

        Game game = givenAGameOfPlayers(new Rock(), new Rock());

        RoundResult result = game.playRound();

        assertDrawResult(result);

        logger.info("The rocksDrawTest tieTest finished ok.");
    }

    private void assertDrawResult(RoundResult result) {
        assertFalse(result.getWinner().isPresent(), "There should not be a winner (its a tie)");
    }
}
