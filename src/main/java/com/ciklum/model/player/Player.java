package com.ciklum.model.player;

import com.ciklum.model.element.Element;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Player {

    private String name;
    private Element currentElement;
    private PlayerStrategy strategy;

    /**
     * A Player must always have selected an element and a name.
     * This constructor creates a Player with the default strategy: {@link RandomStrategy}
     * 
     * @param name
     * @param element
     */
    public Player(String name, Element element) {
        this.name = name;
        this.currentElement = element;
        this.strategy = new RandomStrategy();
    }

    public Player(String name, PlayerStrategy strategy, Element element) {
        this.name = name;
        this.currentElement = element;
        this.strategy = strategy;
    }

    @Override
    public String toString() {
        return "Player[name=" + this.getName() + ", element=" + this.getCurrentElement() + "]";
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

    public Element getCurrentElement() {
        return this.currentElement;
    }

    public void chooseNewElement() {
        Element e = this.strategy.chooseNewElement();
        this.currentElement = e;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param currentElement the currentElement to set
     */
    public void setCurrentElement(Element currentElement) {
        this.currentElement = currentElement;
    }

    /**
     * @return the strategy
     */
    public PlayerStrategy getStrategy() {
        return strategy;
    }

    /**
     * @param strategy the strategy to set
     */
    public void setStrategy(PlayerStrategy strategy) {
        this.strategy = strategy;
    }
}
