package com.ciklum.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ciklum.model.game.Game;
import com.ciklum.model.game.GameStats;
import com.ciklum.model.game.RoundResult;
import com.ciklum.model.player.AlwaysRockStrategy;
import com.ciklum.model.player.Player;
import com.ciklum.model.player.RandomStrategy;
import com.ciklum.model.vo.RoundResultVO;
import com.ciklum.service.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author igallegosagastume@gmail.com
 *
 */
@RestController
@RequestMapping("/game")
public class GameController {

    private static final int USERNAME_MAX_LEN = 50;
    private static final int PLAYER_NAME_MAX_LEN = 30;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GameController.class);

	@Autowired
	private GameService gameService;
    
    /**
     * Plays a round of Rock-Paper-Scissors in this Game Server,
     * for the provided userName, 
     * @param userName
     * @param player1Name
     * @param player2Name
     * @return
     */
    @PostMapping(value = "/playRound", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoundResultVO> playRound(@RequestParam(required = true) @NotBlank @Size(max = USERNAME_MAX_LEN) String userName,
                                   @RequestParam(required = true) @NotBlank @Size(max = PLAYER_NAME_MAX_LEN) String player1Name,
                                   @RequestParam(required = true) @NotBlank @Size(max = PLAYER_NAME_MAX_LEN) String player2Name) {

        logger.info("POST /playRound userName={}, player1Name={}, player2Name={}", userName, player1Name, player2Name);
        
        // Recomended by the book "Clean Code"
        validateRequiredStringParameter(userName, "userName", USERNAME_MAX_LEN);
        validateRequiredStringParameter(player1Name, "player1Name", PLAYER_NAME_MAX_LEN);
        validateRequiredStringParameter(player2Name, "player2Name", PLAYER_NAME_MAX_LEN);

        // once the parameters are validated, create objects and call the service
        Game game = createGameWithSpecifiedStrategies(player1Name, player2Name);
        
        CompletableFuture<RoundResultVO> result =
          this.gameService.playRound(userName, game).thenApply( RoundResult::getVO );

        return ResponseEntity.ok(result.join());
    }

    private void validateRequiredStringParameter(String value, String name, int maxLength) {
        if (value == null || value.isEmpty() || value.length() > maxLength) {
            throw new IllegalArgumentException("Parameter " + name + " is invalid");
        }
    }

    /**
     * Creates a new game instance, with the 2 specified players and their Strategies
     * (specified in the problem statement).
     * 
     * @param player1Name string name for player 1
     * @param player2Name string name for player 2
     * @return a new Game
     */
    private Game createGameWithSpecifiedStrategies(String player1Name, String player2Name) {
        Player player1 = new Player(player1Name, new AlwaysRockStrategy());
        Player player2 = new Player(player2Name, new RandomStrategy());

        return new Game(player1, player2);
    }

    @GetMapping(value = "/getRoundsForUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RoundResultVO>> getRoundsForUser(@RequestParam(required = true) @NotBlank @Size(max = USERNAME_MAX_LEN) String userName) {
        
        logger.info("GET /getRoundsForUser userName={}", userName);

        // Recomended by the book "Clean Code"
        validateRequiredStringParameter(userName, "userName", USERNAME_MAX_LEN);

        CompletableFuture<List<RoundResult>> roundResults = this.gameService.getRoundsForUser(userName);

        CompletableFuture<List<RoundResultVO>> resultsVO =
           roundResults.thenApply( list -> list.stream().map( RoundResult::getVO ).collect(Collectors.toList())  );

        return ResponseEntity.ok(resultsVO.join());
    }

    @GetMapping(value = "/getGameStats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameStats> getGameStats() {
        
        logger.info("GET /getGameStats");

        return ResponseEntity.ok(this.gameService.getGameStats().join());
    }

    @DeleteMapping(value = "/cleanServerMemory", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> cleanServerMemory() {

        logger.info("DELETE /cleanServerMemory");

        return ResponseEntity.ok(gameService.clearServerMemory().join());
    }
}