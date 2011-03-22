package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.Plate384;

/**
 * Factory for creating microplates.
 *
 * @author kotte
 */
public class Plate384Factory {

    public static IPlate384 getDefault() {
        IPlate384 result = new Plate384();
        return result;
    }
}
