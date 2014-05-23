package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384;

import java.awt.Color;

/**
 * Represents the status of a well on a 384 well microplate.
 *
 * @author Konstantin Otte
 */
public enum Well384Status {

    /**
     * This well is empty.
     */
    EMPTY {

        @Override
        public Color getColor() {
            return Color.BLACK;
        }
    },
    /**
     * This well is filled and has a corresponding well on a 96 well plate.
     */
    FILLED {
        @Override
        public Color getColor() {
            return Color.BLUE;
        }
    },
    /**
     * This filled well has been successfully identified.
     */
    IDENTIFIED {
        @Override
        public Color getColor() {
            return Color.GREEN;
        }
    },
    /**
     * This filled well has been attempted to identify,
     * but has no valid identification result.
     */
    UNIDENTIFIED {
        @Override
        public Color getColor() {
            return Color.YELLOW;
        }
    },
    /**
     * This well is in an errand state.
     */
    ERROR {
        @Override
        public Color getColor() {
            return Color.RED;
        }
    };

    public abstract Color getColor();

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
