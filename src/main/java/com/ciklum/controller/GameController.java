package com.ciklum.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
    @PostMapping(value = "/playRound", produces = MediaType.APPLICATION_JSON_VALUE)
    public RoundResultVO playRound(@RequestParam @NotBlank @Size(max = USERNAME_MAX_LEN) String userName,
                                   @RequestParam @NotBlank @Size(max = PLAYER_NAME_MAX_LEN) String player1Name,
                                   @RequestParam @NotBlank @Size(max = PLAYER_NAME_MAX_LEN) String player2Name) {

        logger.info("POST /playRound userName={}, player1Name={}, player2Name={}", userName, player1Name, player2Name);
        
        // Recomended by the book "Clean Code"
        assert(userName != null && !userName.isEmpty() && userName.length() <= USERNAME_MAX_LEN); // NOSONAR
        assert(player1Name != null && !player1Name.isEmpty() && player1Name.length() <= PLAYER_NAME_MAX_LEN); // NOSONAR
        assert(player2Name != null && !player2Name.isEmpty() && player2Name.length() <= PLAYER_NAME_MAX_LEN); // NOSONAR

        Player player1 = new Player(player1Name, new AlwaysRockStrategy());
        Player player2 = new Player(player2Name, new RandomStrategy());

        Game game = new Game(player1, player2);

        RoundResult result = this.gameService.playRound(userName, game);

        return result.getVO();
    }

    @GetMapping(value = "/getRoundsForUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RoundResultVO> getRoundsForUser(@PathVariable @NotBlank @Size(max = USERNAME_MAX_LEN) String userName) {
        
        logger.info("GET /getRoundsForUser userName={}", userName);

        // Recomended by the book "Clean Code"
        assert(userName != null && !userName.isEmpty() && userName.length() <= USERNAME_MAX_LEN); // NOSONAR

        List<RoundResult> results = this.gameService.getRoundsForUser(userName);

        return results.stream().map( RoundResult::getVO ).collect(Collectors.toList());
    }

    @GetMapping(value = "/getGameStats", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameStats getGameStats() {
        
        logger.info("GET /getGameStats");

        return this.gameService.getGameStats();
    }

    @DeleteMapping(value = "/cleanServerMemory", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean cleanServerMemory() {

        logger.info("DELETE /cleanServerMemory");

        return this.gameService.clearServerMemory();
    }
}