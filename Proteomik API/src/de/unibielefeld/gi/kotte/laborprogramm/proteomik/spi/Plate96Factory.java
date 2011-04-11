package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96Factory;

/**
 * Factory for creating 96 well microplates.
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service = IPlate96Factory.class)
public class Plate96Factory implements IPlate96Factory {

    @Override
    public IPlate96 createPlate96() {
        IPlate96 result = new Plate96();
        return result;
    }
}
