package com.ciklum.model.element;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * The abstract element (Rock, Paper, Scissors or others...)
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class Element implements Comparable<Element> {

    @Override
    public boolean equals(Object element) {
        if (!(element instanceof Element)) {
            return false;
        }
        return (this.hashCode() == element.hashCode());
    }

    public String getElementName() {
        return this.toString();
    }
}