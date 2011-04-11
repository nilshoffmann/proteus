package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384Factory;

/**
 * Factory for creating 384 well microplates.
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service = IPlate384Factory.class)
public class Plate384Factory implements IPlate384Factory {

    @Override
    public IPlate384 createPlate384() {
        IPlate384 result = new Plate384();
        return result;
    }
}
