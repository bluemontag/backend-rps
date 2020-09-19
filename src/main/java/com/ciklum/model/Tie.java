package com.ciklum.model;

public final class Tie {
    
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Tie);
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "Tie";
    }
}