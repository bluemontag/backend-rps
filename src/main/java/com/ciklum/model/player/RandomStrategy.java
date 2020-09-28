package com.ciklum.model.player;

import java.util.Random;

import com.ciklum.model.element.Element;
import com.ciklum.model.element.Paper;
import com.ciklum.model.element.Rock;
import com.ciklum.model.element.Scissors;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY)
public class RandomStrategy implements PlayerStrategy {
    
    private Random rand;

    public RandomStrategy() {
        this.rand = new Random();
    }

    @Override
    public Element chooseNewElement() {
        int elem = this.rand.nextInt(3);

        switch (elem) {
            case 0:
                return new Rock();
            case 1:
                return new Paper();
            case 2:
                return new Scissors();

            default:
                // unexpected behaviour (raise exception or log error)
                return null;
        }
    }

}
