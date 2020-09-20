package com.ciklum.model.player;

import com.ciklum.model.element.Element;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Player {

    // immutable properties
    private final String name;
    private final PlayerStrategy strategy;

    /**
     * A Player must always have selected an element and a name.
     * This constructor creates a Player with the default strategy: {@link RandomStrategy}
     * 
     * @param name
     * @param element
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

    public Element chooseNewElement() {
        return this.strategy.chooseNewElement();
    }

    /**
     * @return the strategy
     */
    public PlayerStrategy getStrategy() {
        return strategy;
    }

}
