package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGelFactory;

/**
 * Factory for creating 2D gels.
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service = IGelFactory.class)
public class GelFactory implements IGelFactory {

    @Override
    public IGel createGel() {
        IGel result = new Gel();
        return result;
    }
}
