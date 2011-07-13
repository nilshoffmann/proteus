package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel;

import java.awt.Color;

/**
 * Represents the status of a gel spot.
 *
 * @author kotte
 */
public enum SpotStatus {

    /**
     * This spot is not picked.
     */
    UNPICKED,
    /**
     * This spot is picked,
     * thus having a corresponding well on a 96 well plate.
     */
    PICKED;

    public static Color getColor(SpotStatus status) {
        switch(status) {
            case PICKED:
                return Color.GREEN;
            default:
                return Color.BLACK;
        }
    }
}
