package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IGel;

/**
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service=IGel.class)
public class Gel implements IGel{

    @Override
    public String getDescription() {
        return "I have no description";
    }

}
