package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.Plate96;

/**
 * Factory for creating microplates.
 *
 * @author kotte
 */
public class Plate96Factory {

    public static IPlate96 getDefault() {
        IPlate96 result = new Plate96();
        return result;
    }
}
