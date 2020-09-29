package com.ciklum.model.player;

import java.util.Random;

import com.ciklum.model.shapes.Shape;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY)
public class RandomStrategy implements PlayerStrategy {
    
    private Random rand;

    public RandomStrategy() {
        this.rand = new Random();
    }

    @Override
    public Shape chooseNewElement() {
        int elem = this.rand.nextInt(3);

        switch (elem) {
            case 0:
                return Shape.ROCK;
            case 1:
                return Shape.PAPER;
            case 2:
                return Shape.SCISSORS;

            default:
                // unexpected behaviour (raise exception or log error)
                return null;
        }
    }

}
