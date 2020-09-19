package com.ciklum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.ciklum.model.*;
import com.ciklum.utils.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BasicLogicTest {

    @BeforeEach
    public void beginTest() {
        System.out.println("================================================================================");
    }

    @AfterEach
    public void endTest() {
        System.out.println("================================================================================");
    }

    @Test
    public void rockBeatsScissorsTest() {

        Player p1 = new Player("Juan", new Rock());

        Player p2 = new Player("Pedro", new Scissors());

        Either<Player, Tie> outcome = p1.playWith(p2);

        // there must be a winner
        assertTrue(outcome.getLeft().isPresent(), "There is not a winner");
        
        // and must be p1
        assertEquals(outcome.getLeft().get(), p1, "There is a problem with the outcome");

        System.out.println("The test rockBeatsScissorsTest finished ok.");
    }

    @Test
    public void scissorsIsBeatenByRockTest() {

        Player p1 = new Player("Juan", new Scissors());

        Player p2 = new Player("Pedro", new Rock());

        Either<Player, Tie> outcome = p1.playWith(p2);

        // there must be a winner
        assertTrue(outcome.getLeft().isPresent(), "There is not a winner");
        
        // and must be p1
        assertEquals(outcome.getLeft().get(), p2, "There is a problem with the outcome");

        System.out.println("The test scissorsIsBeatenByRockTest finished ok.");
    }

    @Test
    public void paperBeatsRockTest() {

        Player p1 = new Player("Juan", new Paper());

        Player p2 = new Player("Pedro", new Rock());

        Either<Player, Tie> outcome = p1.playWith(p2);
        // there must be a winner
        assertTrue(outcome.getLeft().isPresent(), "There is not a winner");
        
        // and must be p1
        assertEquals(outcome.getLeft().get(), p1, "There is a problem with the outcome");

        System.out.println("The test paperBeatsRockTest finished ok.");
    }

    @Test
    public void rockIsBeatenByPaperTest() {

        Player p1 = new Player("Juan", new Rock());

        Player p2 = new Player("Pedro", new Paper());

        Either<Player, Tie> outcome = p1.playWith(p2);

        // there must be a winner
        assertTrue(outcome.getLeft().isPresent(), "There is not a winner");
        
        // and must be p1
        assertEquals(outcome.getLeft().get(), p2, "There is a problem with the outcome");

        System.out.println("The test rockIsBeatenByPaperTest finished ok.");
    }

    @Test
    public void scissorsBeatsPaperTest() {

        Player p1 = new Player("Juan", new Scissors());

        Player p2 = new Player("Pedro", new Paper());

        Either<Player, Tie> outcome = p1.playWith(p2);

        // there must be a winner
        assertTrue(outcome.getLeft().isPresent(), "There is not a winner");

        // and must be p1
        assertEquals(outcome.getLeft().get(), p1, "There is a problem with the outcome");

        System.out.println("The test scissorsBeatsPaperTest finished ok.");
    }

    @Test
    public void paperIsBeatenByScissorsTest() {

        Player p1 = new Player("Juan", new Paper());

        Player p2 = new Player("Pedro", new Scissors());

        Either<Player, Tie> outcome = p1.playWith(p2);

        // there must be a winner
        assertTrue(outcome.getLeft().isPresent(), "There is not a winner");
        
        // and must be p1
        assertEquals(outcome.getLeft().get(), p2, "There is a problem with the outcome");

        System.out.println("The test paperIsBeatenByScissorsTest finished ok.");
    }

    @Test
    public void tieTest() {

        Player p1 = new Player("Juan", new Scissors());

        Player p2 = new Player("Pedro", new Scissors());

        Either<Player, Tie> outcome = p1.playWith(p2);

        // there must be a tie
        assertTrue(outcome.getRight().isPresent(), "There is a winner");

        // and the value must be a Tie
        assertEquals(outcome.getRight().get(), new Tie(), "There is a problem with the outcome");

        p1.chooseNewElement(new Paper());
        p2.chooseNewElement(new Paper());

        // there must be a tie
        assertTrue(outcome.getRight().isPresent(), "There is a winner");

        // and the value must be a Tie
        assertEquals(outcome.getRight().get(), new Tie(), "There is a problem with the outcome");

        p1.chooseNewElement(new Rock());
        p2.chooseNewElement(new Rock());

        // there must be a tie
        assertTrue(outcome.getRight().isPresent(), "There is a winner");

        // and the value must be a Tie
        assertEquals(outcome.getRight().get(), new Tie(), "There is a problem with the outcome");

        System.out.println("The test tieTest finished ok.");
    }

}
