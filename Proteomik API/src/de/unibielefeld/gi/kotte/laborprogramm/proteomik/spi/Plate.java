package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPlate;

/**
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service=IPlate.class)
public class Plate implements IPlate{

    @Override
    public String getDescription() {
        return "I have no description";
    }
}
