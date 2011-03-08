package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.Plate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.Plate384;
import org.openide.util.Lookup;

/**
 * Factory for creating microplates.
 *
 * @author kotte
 */
public class PlateFactory {

    public static IPlate getDefault() {
        IPlate result = Lookup.getDefault().lookup(IPlate.class);
        return result;
    }

    public static IPlate get96WellPlate() {
        IPlate result = new Plate96();
        return result;
    }

    public static IPlate get384WellPlate() {
        IPlate result = new Plate384();
        return result;
    }
}
