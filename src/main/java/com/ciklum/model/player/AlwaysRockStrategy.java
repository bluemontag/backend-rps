package com.ciklum.model.player;

import com.ciklum.model.element.Element;
import com.ciklum.model.element.Rock;

public class AlwaysRockStrategy implements PlayerStrategy {

    public AlwaysRockStrategy() {
        // nothing needed here
    }

    @Override
    public Element chooseNewElement() {
        return new Rock();
    }
}
