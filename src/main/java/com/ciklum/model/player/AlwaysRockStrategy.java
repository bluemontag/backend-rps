package com.ciklum.model.player;

import com.ciklum.model.shapes.Shape;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY)
public class AlwaysRockStrategy implements PlayerStrategy {

    public AlwaysRockStrategy() {
        // nothing needed here
    }

    @Override
    public Shape chooseNewShape() {
        return Shape.ROCK;
    }

}
