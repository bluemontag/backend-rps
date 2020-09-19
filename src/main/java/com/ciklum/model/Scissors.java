package com.ciklum.model;

public final class Scissors extends Element {


    @Override 
    public int hashCode() {
        return 1;
    }
    
    /**
     * Here the lower element will be the winner,
     *  e.g. when returning -1.
     */
    @Override
    public int compareTo(Element element) {
        if (this.equals(element)) {
            return 0;
        }
        if (element instanceof Rock) {
            return 1;
        } else {
            return -1;
        }
    }

    
    @Override
    public String toString() {
        return "Scissors";
    }
}