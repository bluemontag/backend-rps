package com.ciklum;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ciklum.model.element.Paper;
import com.ciklum.model.element.Rock;
import com.ciklum.model.game.GameLogic;
import com.ciklum.model.game.GameServer;
import com.ciklum.model.player.Player;

import org.junit.jupiter.api.Test;

public class GameLogicTest {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GameLogicTest.class);

    @Test
    public void gameCreationTest() {

        GameServer server = new GameServer();

        String user = "Ignacio";

        Player p1 = new Player("Jorge", () -> new Rock());
        Player p2 = new Player("Andres", () -> new Paper());
        GameLogic game = new GameLogic(server, user, p1, p2);

        // play 2 rounds with user "Ignacio"
        game.playRound();
        assertEquals(1, server.getRoundsPlayedForUser(user), "The number of rounds played should be 1");
        game.playRound();
        assertEquals(2, server.getRoundsPlayedForUser(user), "The number of rounds played should be 2");

        user = "Javier";
        game = new GameLogic(server, user, p1, p2);
        // play 3 rounds with user "Javier"
        game.playRound();
        assertEquals(1, server.getRoundsPlayedForUser(user), "The number of rounds played should be 1");
        game.playRound();
        assertEquals(2, server.getRoundsPlayedForUser(user), "The number of rounds played should be 2");
        game.playRound();
        assertEquals(3, server.getRoundsPlayedForUser(user), "The number of rounds played should be 3");

        // the first user results are not changed:
        assertEquals(2, server.getRoundsPlayedForUser("Ignacio"), "The number of rounds played should be 2");

        // calculate stats for all users
        assertEquals(5, server.getGameStats().getTotalRounds());

        logger.info("The test finished ok.");
    }

}
