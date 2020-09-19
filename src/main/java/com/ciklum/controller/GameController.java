package com.ciklum.controller;

import com.ciklum.model.game.RoundResult;
import com.ciklum.service.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author igallegosagastume@gmail.com
 *
 */
@RestController
@RequestMapping("/game")
public class GameController {


    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GameController.class);

	@Autowired
	private GameService gameService;
	
    @RequestMapping(value = "/playRound", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)//NOSONAR
    public RoundResult playRound(String userName, String player1Name, String player2Name) {

        logger.info("GET /playRound userName={}, player1Name={}, player2Name={}", userName, player1Name, player2Name);
        
        return this.gameService.playRound(userName, player1Name, player2Name);

    }
}