package com.ciklum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.ciklum.model.game.Game;
import com.ciklum.model.game.GameStats;
import com.ciklum.model.game.RoundResult;
import com.ciklum.model.player.Player;
import com.ciklum.model.player.RandomStrategy;
import com.ciklum.service.GameService;

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
public class GameServiceTest {
    
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GameServiceTest.class);

    @Autowired
    private GameService gameService;

    private String userName = "Javier";

    @Before
    public void beforeEach() {
        logger.info("==================================================================");
        this.gameService.clearServerMemory().join();
    }
    
    @After
    public void afterEach() {
        logger.info("==================================================================");
    }

    @Test
    public void testWhenPlayRoundThenStatsAreIncremented() {
        // given a game
        Game game = givenAGameBetweenPlayer1AndPlayer2();

        // play one round
        RoundResult result = gameService.playRound(userName, game).join();
        
        assertStatsIncrementAccordingToResult(result);

        logger.info("The test testWhenPlayRoundThenStatsAreIncremented finished ok.");
    }

    private void assertStatsIncrementAccordingToResult(RoundResult result) {
        GameStats stats = gameService.getGameStats().join();

        // check the total stats
        assertEquals(1, stats.getTotalRounds());
        
        if (result.isDraw()) {
            assertEquals(1, stats.getTotalDraws());
            assertEquals(0, stats.getTotalWinsP1());
            assertEquals(0, stats.getTotalWinsP2());
        } else if (result.isPlayer1Winner()) {
            assertEquals(0, stats.getTotalDraws());
            assertEquals(1, stats.getTotalWinsP1());
            assertEquals(0, stats.getTotalWinsP2());
        } else {
            assertEquals(0, stats.getTotalDraws());
            assertEquals(0, stats.getTotalWinsP1());
            assertEquals(1, stats.getTotalWinsP2());
        }
    }

    @Test
    public void testWhenPlayRoundThenResultIsStored() {
        // given a game
        Game game = givenAGameBetweenPlayer1AndPlayer2();

        // play one round
        RoundResult result = gameService.playRound(userName, game).join();
        
        assertRoundsForUserContainsResult(result, userName);

        logger.info("The test testWhenPlayRoundThenResultIsStored finished ok.");
    }

    private Game givenAGameBetweenPlayer1AndPlayer2() {
        Player player1 = new Player("Player 1", new RandomStrategy());
        Player player2 = new Player("Player 2", new RandomStrategy());
        return new Game(player1, player2);
    }

    private void assertRoundsForUserContainsResult(RoundResult result, String userName) {
        
        List<RoundResult> results = gameService.getRoundsForUser(userName).join();
        
        assertNotEquals(null, results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals(result, results.get(0));
    }

    @Test
    public void testWhenGettingStatsThenEmptyStatsIsReturned() {
        // given an empty game service

        // When getting game stats
        GameStats stats = gameService.getGameStats().join();

        // then
        assertNonEmptyStatsWithZeroValues(stats);

        logger.info("The test testWhenGettingStatsThenEmptyStatsIsReturned finished ok.");
    }

    private void assertNonEmptyStatsWithZeroValues(GameStats stats) {
        assertNotNull(stats);
        assertEquals(0, stats.getTotalRounds());
        assertEquals(0, stats.getTotalDraws());
        assertEquals(0, stats.getTotalWinsP1());
        assertEquals(0, stats.getTotalWinsP2());
    }

    @Test
    public void testWhenPlayARoundAndClearMemoryThenEmptyStatsIsReturned() {
        // given a game
        Game game = givenAGameBetweenPlayer1AndPlayer2();

        // When play one round
        gameService.playRound(userName, game).join();

        // and clear the memory:
        gameService.clearServerMemory().join();

        // Then
        assertNonEmptyStatsWithZeroValues(gameService.getGameStats().join());
        assertMemoryEmpty();

        logger.info("The test testWhenPlayARoundAndClearMemoryThenEmptyStatsIsReturned finished ok.");
    }

    private void assertMemoryEmpty() {
        List<RoundResult> results = gameService.getRoundsForUser(userName).join();

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
}

