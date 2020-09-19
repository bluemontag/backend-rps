package com.ciklum.model.element;

public final class Rock extends Element {

    public static final int ELEMENT_ID = 0;

    @Override 
    public int hashCode() {
        return ELEMENT_ID;
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
        if (element instanceof Paper) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "Rock";
    }
}