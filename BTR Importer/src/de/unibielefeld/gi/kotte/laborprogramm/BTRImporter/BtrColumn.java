/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.BTRImporter;

/**
 *
 * @author hoffmann
 */
public enum BtrColumn {

    POS_ON_SCOUT,
    ACCESSION,
    TITLE,
    MASCOT_SCORE,
    STATUS,
    PROTEIN_MW,
    PI_VALUE,
    MS_COVERAGE,
    DIFFERENCE,
    METHOD;

    public static BtrColumn normalizeColumnName(String name) {
        String key = name.trim().toUpperCase();
        key = key.replaceAll("\\s+", "_");
        key = key.replaceAll("\\-+","_");
        return valueOf(key);
    }
}
