package com.ciklum.model.game;

import java.util.Optional;

import com.ciklum.model.player.Player;

/**
 * Game is an instance of a RPS Game in a GameServer,
 *  between the same pair of players, and for a given userName.
 * 
 * It contains the logic for playing one round of the game.
 * 
 */
public class Game {
    
    private GameServer server;
    private String userName;
    private Player player1;
    private Player player2;


    public Game(GameServer server, String userName, Player player1, Player player2) {
        this.server = server;
        this.userName = userName;
        this.player1 = player1;
        this.player2 = player2;
    }

    public Player getPlayer1() {
        return this.player1;
    }
    
    public Player getPlayer2() {
        return this.player2;
    }
    
    /**
     * Plays a round for the current selected elements for each player,
     *  then adds the result to the server (for the current userName).
     * 
     * @return the result in case you need to test the value from the outside
     */
    public RoundResult playRound() {

        System.out.println(this.player1.getName() + " is playing with " + this.player2.getName());

        int outcome = this.player1.getCurrentElement().compareTo(this.player2.getCurrentElement());
        RoundResult result = null;

        switch (outcome) {
            case 0:
                // Tie        
                System.out.println("There is a tie");
                result = new RoundResult(this.player1, this.player2, Optional.empty());
                break;

            case -1:
                // The first element wins
                System.out.println(this.player1.getCurrentElement() + " beats " + this.player2.getCurrentElement());
                result = new RoundResult(this.player1, this.player2, Optional.of(this.player1));
                break;

            case 1:
                // The second element wins
                System.out.println(this.player2.getCurrentElement() + " beats " + this.player1.getCurrentElement());
                result = new RoundResult(this.player1, this.player2, Optional.of(this.player2));
                break;

            default:
                // raise exception or return null (there is some unexpected behaviour)
                return null;
        }
        server.addNewResult(this.userName, result);
        
        return result;
    }

    public void chooseNewElementsForEachPlayer() {
        // choose a new element for each user
        this.player1.chooseNewElement();
        this.player2.chooseNewElement();
    }
}
