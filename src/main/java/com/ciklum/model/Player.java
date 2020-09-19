package com.ciklum.model;

import com.ciklum.utils.Either;

public class Player {

    private String name;
    private Element element;

    /**
     * A Player must always have selected an element and a name.
     * 
     * @param name
     * @param element
     */
    public Player(String name, Element element) {
        this.name = name;
        this.element = element;
    }

    public Either<Player, Tie> playWith(Player player2) {

        System.out.println(this.getName() + " is playing with " + player2.getName());

        int outcome = this.getElement().compareTo(player2.getElement());

        switch (outcome) {
            case 0:
                // Tie        
                System.out.println("There is a tie");
                return Either.right(new Tie());
        
            case -1:
                // The first element wins
                System.out.println(this.getElement() + " beats " + player2.getElement());
                return Either.left(this);

            case 1:
                // The second element wins
                System.out.println(player2.getElement() + " beats " + this.getElement());
                return Either.left(player2);

            default:
                // raise exception or return null (there is some unexpected behaviour)
                return null;
        }
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
        return this.element;
    }

    public void chooseNewElement(Element e) {
        this.element = e;
    }
}
