package com.ciklum.model.player;

import com.ciklum.model.element.Element;

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
        return "Player[name=" + this.getName() + ", element=" + this.getElement() + "]";
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

    public Element getElement() {
        return this.currentElement;
    }

    public void chooseNewElement() {
        Element e = this.strategy.chooseNewElement();
        this.currentElement = e;
    }
}
