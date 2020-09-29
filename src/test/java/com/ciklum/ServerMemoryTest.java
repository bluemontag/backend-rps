package com.ciklum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import com.ciklum.model.shapes.Shape;
import com.ciklum.model.game.Game;
import com.ciklum.model.game.GameStats;
import com.ciklum.model.game.RoundResult;
import com.ciklum.model.game.ServerMemory;
import com.ciklum.model.player.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = com.ciklum.config.TestConfig.class)
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class})
public class ServerMemoryTest {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ServerMemoryTest.class);

    @Autowired
    private ServerMemory serverMemory;

    private String user1 = "Jorge";
    private String user2 = "Javier";

    @Before
    public void beforeEach() {
        logger.info("==================================================================");
        serverMemory.clear();
    }
    
    @After
    public void afterEach() {
        logger.info("==================================================================");
    }

    @Test
    public void testWhenNRoundsArePlayedThenResultsAreStored() {

        // Given a game
        Game game = givenAGameOfPlayers(Shape.PAPER, Shape.SCISSORS);

        int nRounds = 10;
        // When
        whenPlayingNRoundsWithUser(game, nRounds, user1);

        // Then
        assertNRoundsForUser(nRounds, user1);
    }

    private void assertResultListHasNElements(int n, String user) {
        List<RoundResult> results = serverMemory.getRounds(user);

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(n, results.size());
    }

    @Test
    public void testWhenTwoDifferentUsersThenTheyDontInterfereWithEachOther() {

        // given 2 different games
        Game game = givenAGameOfPlayers(Shape.ROCK, Shape.PAPER);
        Game game2 = givenAGameOfPlayers(Shape.ROCK, Shape.SCISSORS);
        
        int roundsForUser1 = 2;
        int roundsForUser2 = 3;
        // when playing 2 and 3 rounds with each user
        whenPlayingNRoundsWithUser(game, roundsForUser1, user1);
        whenPlayingNRoundsWithUser(game2, roundsForUser2, user2);

        // then
        assertNRoundsForUser(roundsForUser1, user1);
        assertNRoundsForUser(roundsForUser2, user2);
        
        logger.info("The test finished ok.");
    }

    // create test data to operate on
    private Game givenAGameOfPlayers(Shape e1, Shape e2) {
        // create 2 players with strategies "always e1 and always e2"
        Player p1 = new Player("Juan", () -> e1);
        Player p2 = new Player("Pedro", () -> e2);
        return new Game(p1, p2);
    }


    private void whenPlayingNRoundsWithUser(Game game, int rounds, String user) {

        // repeat "round" times
        for (int i=1; i <= rounds; i++) {
            // play a round
            RoundResult result = game.playRound();
            // add result to memory
            serverMemory.addNewResult(user, result);
        }
    }

    private void assertNRoundsForUser(int roundsForUser, String userName) {
        assertResultListHasNElements(roundsForUser, userName);

        int rounds = serverMemory.getRoundsPlayedForUser(userName);
        assertEquals(roundsForUser, rounds);
    }

    @Test
    public void testWhenPlayARoundThenTotalRoundsAreOk() {

        // Given
        Game game = givenAGameOfPlayers(Shape.ROCK, Shape.PAPER);

        // When 5 rounds are played with user 1
        whenPlayingNRoundsWithUser(game, 5, user1);
        // And 6 rounds are played with user 2
        whenPlayingNRoundsWithUser(game, 6, user2);

        // Then
        assertTotalRoundsAre(11);
    }

    private void assertTotalRoundsAre(int n) {
        assertEquals(n, serverMemory.getGameStats().getTotalRounds());
    }

    @Test
    public void testWhenPlayer1WinsNTimesAndPlayer2WinsMTimesThenStatsAreOk() {

        // Given
        Game game = givenAGameOfPlayers(Shape.ROCK, Shape.SCISSORS);

        // When Rock Wins 8 times
        whenPlayingNRoundsWithUser(game, 8, user1);

        // And Player 2 wins 7 times
        game = givenAGameOfPlayers(Shape.ROCK, Shape.PAPER);
        whenPlayingNRoundsWithUser(game, 7, user1);

        // And There are 3 draws
        game = givenAGameOfPlayers(Shape.PAPER, Shape.PAPER);
        whenPlayingNRoundsWithUser(game, 3, user1);

        // Then
        assertStatsAreOk(8, 7, 3);
    }

    private void assertStatsAreOk(int winsForPlayer1, int winsForPlayer2, int draws) {
        GameStats stats = serverMemory.getGameStats();

        assertEquals(winsForPlayer1, stats.getTotalWinsP1());
        assertEquals(winsForPlayer2, stats.getTotalWinsP2());
        assertEquals(draws, stats.getTotalDraws());
        assertEquals(winsForPlayer1 + winsForPlayer2 + draws, stats.getTotalRounds());
    }

    @Test
    public void testWhenPlayNRoundsAndClearMemoryThenStatsAreOk() {

        // Given
        Game game = givenAGameOfPlayers(Shape.ROCK, Shape.SCISSORS);

        // When Rock Wins 8 times
        whenPlayingNRoundsWithUser(game, 8, user1);

        // And clear memory
        serverMemory.clear();
        
        // Then
        assertStatsAreOk(0, 0, 0);
    }
}
