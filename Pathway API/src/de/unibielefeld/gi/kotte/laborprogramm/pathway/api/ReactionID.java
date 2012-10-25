package de.unibielefeld.gi.kotte.laborprogramm.pathway.api;

/**
 * Defines the type of an ID String to identify a reaction.
 *
 * @author kotte
 */
public enum ReactionID {
    /*
     * The corresponding ID String is an EC number.
     */
    EC,
    /**
     * The corresponding ID String is a KEGG reaction id.
     */
    KEGG;
}
