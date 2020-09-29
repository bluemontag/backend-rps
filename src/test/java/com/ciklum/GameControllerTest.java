package com.ciklum;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ciklum.controller.GameController;
import com.ciklum.model.game.Game;
import com.ciklum.model.game.GameStats;
import com.ciklum.model.game.RoundResult;
import com.ciklum.model.player.Player;
import com.ciklum.model.shapes.Shape;
import com.ciklum.model.vo.RoundResultVO;
import com.ciklum.service.GameService;

import org.apache.catalina.connector.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = com.ciklum.config.TestConfig.class)
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class})
public class GameControllerTest {
    
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GameControllerTest.class);

    @InjectMocks
    GameController gameController;
     
    @Mock
    GameService gameService;

    @Before
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        logger.info("==================================================================");
    }

    @After
    public void afterEach() {
        logger.info("==================================================================");
    }
     
    @Test
    public void testWhenPlayingOneRoundThenResultIsCorrect() 
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        when(gameService.playRound(any(String.class), any(Game.class))).thenReturn(
            new RoundResult(Shape.ROCK, Shape.SCISSORS, Optional.of(new Player("Jacinto")), true, false));
         
        ResponseEntity<RoundResultVO> responseEntity = gameController.playRound("Matias", "Juan", "Pedro");
         
        assertResultOk(responseEntity);

        logger.info("Test testWhenPlayingOneRoundThenResultIsCorrect finished ok");
    }

    private void assertResultOk(ResponseEntity<RoundResultVO> responseEntity) {
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertThat("The DTO is not OK", responseEntity.getBody(), org.hamcrest.Matchers.instanceOf(RoundResultVO.class));
        assertEquals("Jacinto", responseEntity.getBody().getWinner());
        assertNotNull(Integer.valueOf(responseEntity.getBody().hashCode()));
    }

    @Test
    public void testsWhenSubmittingBadParametersThenExceptionIsThrown() {
        // given the mocked gameService object

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        
        // When playing one round
        when(gameService.playRound(any(String.class), any(Game.class))).thenReturn(
            new RoundResult(Shape.ROCK, Shape.SCISSORS, Optional.of(new Player("Jacinto")), true, false));
        
        // Then code throws exception
        assertCodeThrowsIllegalArgumentException(gameController);

        logger.info("Test testsWhenSubmittingBadParametersThenExceptionIsThrown finished ok");
    }

    private void assertCodeThrowsIllegalArgumentException(GameController gameController) {
        assertThrows(IllegalArgumentException.class, () -> { gameController.playRound(null, "Juan", "Pedro"); },
                    "Argument \"userName\" was accepted when was invalid (null)");
        assertThrows(IllegalArgumentException.class, () -> { gameController.playRound("Matias", null, "Pedro"); },
                    "Argument \"player1Name\" was accepted when was invalid (null)");
        assertThrows(IllegalArgumentException.class, () -> { gameController.playRound("Matias", "Juan", null); },
                    "Argument \"player2Name\" was accepted when was invalid (null)");
        assertThrows(IllegalArgumentException.class, () -> { gameController.playRound("", "Juan", "Pedro"); },
                    "Argument \"userName\" was accepted when was invalid (blank)");
        assertThrows(IllegalArgumentException.class, () -> { gameController.playRound("Matias", "", "Pedro"); },
                    "Argument \"player1Name\" was accepted when was invalid (blank)");
        assertThrows(IllegalArgumentException.class, () -> { gameController.playRound("Matias", "Juan", ""); },
                    "Argument \"player2Name\" was accepted when was invalid (blank)");
    }

    @Test
    public void testsWhenGetRoundsForUserThenResultOk() {
        // given the mocked gameService object

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        
        // When getting rounds for user
        List<RoundResult> mockedResult = new ArrayList<>();
        mockedResult.add(new RoundResult(Shape.ROCK, Shape.SCISSORS, Optional.of(new Player("Jacinto")), true, false));
        when(gameService.getRoundsForUser(any(String.class))).thenReturn(mockedResult);
        
        ResponseEntity<List<RoundResultVO>> responseEntity = gameController.getRoundsForUser("Ignacio");

        // Then
        assertResultsHasOneElement(responseEntity);

        logger.info("Test testsWhenGetRoundsForUserThenResultOk finished ok");
    }

    private void assertResultsHasOneElement(ResponseEntity<List<RoundResultVO>> responseEntity) {
        assertEquals(200, responseEntity.getStatusCodeValue());
        List<RoundResultVO> results = responseEntity.getBody();
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    public void testsWhenGetStatsThenResultOk() {
        // given the mocked gameService object

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        
        // When getting rounds for user
        GameStats mockedResult = new GameStats(0L, 0L, 0L, 0L);
        when(gameService.getGameStats()).thenReturn(mockedResult);
        
        ResponseEntity<GameStats> responseEntity = gameController.getGameStats();

        // Then
        assertGameStatsAreOk(responseEntity, mockedResult);

        logger.info("Test testsWhenGetStatsThenResultOk finished ok");
    }

    private void assertGameStatsAreOk(ResponseEntity<GameStats> responseEntity, GameStats gameStats) {
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(gameStats, responseEntity.getBody());
        assertEquals(gameStats.hashCode(), responseEntity.getBody().hashCode());
    }

    @Test
    public void testsWhenDeleteServerMemoryThenResultOk() {
        // given the mocked gameService object

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        
        // When getting rounds for user
        when(gameService.clearServerMemory()).thenReturn(true);
        
        ResponseEntity<Boolean> responseEntity = gameController.cleanServerMemory();

        // Then
        assertClearMemoryResponseIsOk(responseEntity);

        logger.info("Test testsWhenDeleteServerMemoryThenResultOk finished ok");
    }

    private void assertClearMemoryResponseIsOk(ResponseEntity<Boolean> responseEntity) {
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(Boolean.TRUE, responseEntity.getBody());
    }
}
