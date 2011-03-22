package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384;

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
}
