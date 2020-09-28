package com.ciklum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import com.ciklum.model.game.GameStats;
import com.ciklum.model.game.RoundResult;
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
        this.gameService.clearServerMemory();
    }
    
    @After
    public void afterEach() {
        logger.info("==================================================================");
    }

    @Test
    public void testStatsIncrement() {
        // given an empty game Service
        
        // play one round
        RoundResult result = gameService.playRound(userName, "Player 1", "Player 2");
        
        assertStatsIncrementAccordingToResult(result);

        logger.info("The test testStatsIncrement finished ok.");
    }

    private void assertStatsIncrementAccordingToResult(RoundResult result) {
        GameStats stats = gameService.getGameStats();

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
    public void testUserStatsIncrement() {
        // given an empty game Service

        // play one round
        RoundResult result = gameService.playRound(userName, "Player 1", "Player 2");
        
        assertRoundsForUserContainsResult(result, userName);

        logger.info("The test testUserStatsIncrement finished ok.");
    }

    private void assertRoundsForUserContainsResult(RoundResult result, String userName) {
        
        List<RoundResult> results = gameService.getRoundsForUser(userName);
        
        assertNotEquals(null, results);
        assertFalse(results.isEmpty());
        assertEquals(result, results.get(0));
    }
}

