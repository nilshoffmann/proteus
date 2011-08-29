package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IWellIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IWellIdentificationFactory;
import org.openide.util.lookup.ServiceProvider;

/**
 * Default implementation of IWellIdentificationFactory
 *
 * @author kotte
 */
@ServiceProvider(service=IWellIdentificationFactory.class)
public class WellIdentificationFactory implements IWellIdentificationFactory {

    @Override
    public IWellIdentification createWellIdentification() {
        IWellIdentification wellIdentification = new WellIdentification();
        return wellIdentification;
    }
    
}
