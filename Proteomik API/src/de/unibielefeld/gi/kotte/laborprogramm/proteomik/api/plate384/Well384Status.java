package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384;

import java.awt.Color;

/**
 * Represents the status of a well on a 384 well microplate.
 *
 * @author kotte
 */
public enum Well384Status {

    /**
     * This well is empty.
     */
    EMPTY,
    /**
     * This well is filled and has a corresponding well on a 96 well plate.
     */
    FILLED,
    /**
     * This filled well has been successfully identified.
     */
    IDENTIFIED,
    /**
     * This filled well has been attempted to identify,
     * but has no valid identification result.
     */
    UNIDENTIFIED,
    /**
     * This well is in an errand state.
     */
    ERROR;

    public Color getColor() {
        switch(this) {
            case FILLED:
                return Color.BLUE;
            case IDENTIFIED:
                return Color.GREEN;
            case UNIDENTIFIED:
                return Color.MAGENTA;
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
//            case IDENTIFIED:
//                return "identifiziert";
//            case UNCERTAIN:
//                return "unsicher";
//            case UNIDENTIFIED:
//                return "nicht identifiziert";
//            case MULTIPLE_IDENTIFICATIONS:
//                return "uneindeutig iderntifiziert";
//            default:
//                return "fehlerhaft";
//        }
    }
}
