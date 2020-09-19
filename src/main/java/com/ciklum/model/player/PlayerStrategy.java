package com.ciklum.model.player;

import com.ciklum.model.element.Element;

public interface PlayerStrategy {

    public Element chooseNewElement();
    
    public String getStrategy();
}
