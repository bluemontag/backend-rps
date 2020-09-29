package com.ciklum.model.player;

import com.ciklum.model.shapes.Shape;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Player {

    // immutable properties
    private final String name;
    private final PlayerStrategy strategy;

    /**
     * A Player must always have selected an shape and a name.
     * This constructor creates a Player with the default strategy: {@link RandomStrategy}
     * 
     * @param name
     * @param shape
     */
    public Player(String name) {
        this.name = name;
        this.strategy = new RandomStrategy();
    }

    public Player(String name, PlayerStrategy strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    @Override
    public String toString() {
        return "Player[name=" + this.getName() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        }
        Player player2 = (Player) obj;
        return this.getName().equals(player2.getName());
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }
    
    public String getName() {
        return this.name;
    }

    public Shape chooseNewShape() {
        return this.getStrategy().chooseNewShape();
    }

    /**
     * @return the strategy
     */
    public PlayerStrategy getStrategy() {
        return strategy;
    }

}
