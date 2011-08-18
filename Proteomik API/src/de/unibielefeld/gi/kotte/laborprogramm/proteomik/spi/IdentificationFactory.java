/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentificationFactory;
import org.openide.util.lookup.ServiceProvider;

/**
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
