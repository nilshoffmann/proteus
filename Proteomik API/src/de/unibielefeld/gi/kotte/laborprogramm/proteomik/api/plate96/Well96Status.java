package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96;

/**
 * Represents the status of a well on a 96 well microplate.
 *
 * @author kotte
 */
public enum Well96Status {

    /**
     * The well is empty.
     */
    EMPTY,
    /**
     * The well is filled and thus has a corresponding spot on a gel.
     */
    FILLED,
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
}
