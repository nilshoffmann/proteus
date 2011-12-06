package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentificationFactory;
import org.openide.util.lookup.ServiceProvider;

/**
 * Default implementation of IIdentificationFactory
 *
 * @author hoffmann
 */
@ServiceProvider(service=IIdentificationFactory.class)
public class IdentificationFactory implements IIdentificationFactory {

    @Override
    public IIdentification createIdentification() {
        IIdentification identification = new Identification();
        return identification;
    }
    
}
