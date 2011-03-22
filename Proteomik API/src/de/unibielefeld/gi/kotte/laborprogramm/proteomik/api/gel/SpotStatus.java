package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel;

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
}
