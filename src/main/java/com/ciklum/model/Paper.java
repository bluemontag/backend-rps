package com.ciklum.model;

public final class Paper extends Element {

    @Override 
    public int hashCode() {
        return 2;
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
        if (element instanceof Scissors) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "Paper";
    }
}