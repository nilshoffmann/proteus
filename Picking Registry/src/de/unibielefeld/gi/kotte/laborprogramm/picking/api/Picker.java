package de.unibielefeld.gi.kotte.laborprogramm.picking.api;

/**
 * A Picker is a class (usually a TopComponent) that offers picking functionality.
 * Pickers can register themselves at the Picking Registry to prevent multiple active Pickers.
 *
 * @author kotte
 */
public interface Picker {
    public void resetPicking();
}
