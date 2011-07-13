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
     * This filled well has an uncertain identification.
     */
    UNCERTAIN,
    /**
     * This filled well has been attempted to identify,
     * but has no valid identification result.
     */
    UNIDENTIFIED,
    /**
     * This filled well has multiple identifications.
     */
    MULTIPLE_IDENTIFICATIONS,
    /**
     * This well is in an errand state.
     */
    ERROR;

    public static Color getColor(Well384Status status) {
        switch(status) {
            case FILLED:
                return Color.BLUE;
            case IDENTIFIED:
                return Color.GREEN;
            case UNCERTAIN:
                return Color.CYAN;
            case UNIDENTIFIED:
                return Color.MAGENTA;
            case MULTIPLE_IDENTIFICATIONS:
                return Color.YELLOW;
            case ERROR:
                return Color.RED;
            default:
                return Color.BLACK;
        }
    }
}
