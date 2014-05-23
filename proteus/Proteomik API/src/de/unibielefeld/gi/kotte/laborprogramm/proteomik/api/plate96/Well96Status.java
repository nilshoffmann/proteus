package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96;

import java.awt.Color;

/**
 * Represents the status of a well on a 96 well microplate.
 *
 * @author Konstantin Otte
 */
public enum Well96Status {

    /**
     * The well is empty.
     */
    EMPTY ,
    /**
     * The well is filled and thus has a corresponding spot on a gel.
     */
    FILLED ,
    /**
     * The well is filled and has been processed,
     * it has a corresponding spot on a gel and
     * a corresponding well on a 384 well plate.
     */
    PROCESSED,
    /**
     * This well is in an errand state.
     */
    ERROR;

    public Color getColor() {
        switch(this) {
            case FILLED:
                return Color.GREEN;
            case PROCESSED:
                return Color.BLUE;
            case ERROR:
                return Color.RED;
            default:
                return Color.BLACK;
        }
    }

    @Override
    public String toString() {
        return name().toLowerCase();
//        switch(this) {
//            case EMPTY:
//                return "leer";
//            case FILLED:
//                return "befüllt";
//            case PROCESSED:
//                return "prozessiert";
//            default:
//                return "fehlerhaft";
//        }
    }
}
