package com.ciklum.model.player;

import com.ciklum.model.element.Element;
import com.ciklum.model.element.Rock;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY)
public class AlwaysRockStrategy implements PlayerStrategy {

    public AlwaysRockStrategy() {
        // nothing needed here
    }

    @Override
    public Element chooseNewElement() {
        return new Rock();
    }

    @Override
    public String getStrategy() {
        return "Always Rock Strategy";
    }
}
