package com.ciklum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import com.ciklum.model.game.GameStats;
import com.ciklum.model.game.RoundResult;
import com.ciklum.service.GameService;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = com.ciklum.config.TestConfig.class)
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class})
public class GameServiceTest {
    
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GameServiceTest.class);

    @Autowired
    private GameService gameService;

    @BeforeAll
    private void beforeAllTests() {
        
    }

    @BeforeEach
    public void beforeEach() {
        logger.info("==================================================================");
    }
    
    @AfterEach
    public void afterEach() {
        logger.info("==================================================================");
    }

    @AfterAll
    private void afterAllTests() {

    }

    @Test
    public void getStatsTest() {
        logger.info("================================================================================");
        GameStats stats = gameService.getGameStats();
        
        // must be empty
        assertEquals(0, stats.getTotalRounds());

        // add one game and check stats
        gameService.playRound("Ignacio", "Player 1", "Player 2");
        
        List<RoundResult> ignacioRounds = gameService.getRoundsForUser("Ignacio");
        assertTrue(!ignacioRounds.isEmpty());
        assertEquals(1, ignacioRounds.size());
        // check the total stats
        stats = gameService.getGameStats();
        assertEquals(1, stats.getTotalRounds());

        gameService.playRound("Ramiro", "Player 1", "Player 2");
        List<RoundResult> ramiroRounds = gameService.getRoundsForUser("Ramiro");
        assertTrue(!ramiroRounds.isEmpty());
        assertEquals(1, ramiroRounds.size());
        // stats for 1st user are the same
        assertTrue(!ignacioRounds.isEmpty());
        assertEquals(1, ignacioRounds.size());

        // now the total stats are 2
        stats = gameService.getGameStats();
        assertEquals(2, stats.getTotalRounds());

        // the final game
        gameService.playRound("Ramiro", "Player 1", "Player 2");
        ramiroRounds = gameService.getRoundsForUser("Ramiro");
        assertTrue(!ramiroRounds.isEmpty());
        assertEquals(2, ramiroRounds.size());
        stats = gameService.getGameStats();
        assertEquals(3, stats.getTotalRounds());

        logger.info("The test finished ok.");
        logger.info("================================================================================");
    }
}

