package com.ciklum.model;

/**
 * The abstract element (Rock, Paper, Scissors or others...)
 */
public abstract class Element implements Comparable<Element> {

    @Override
    public boolean equals(Object element) {
        if (!(element instanceof Element)) {
            return false;
        }
        return (this.hashCode() == element.hashCode());
    }

}