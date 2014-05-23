package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroupFactory;

/**
 * Factory for creating new groups of biological replications of groups
 * of technical replications of 2D gels.
 *
 * @author Konstantin Otte
 */
@org.openide.util.lookup.ServiceProvider(service = IBioRepGelGroupFactory.class)
public class BioRepGelGroupFactory implements IBioRepGelGroupFactory{

    @Override
    public IBioRepGelGroup createBioRepGelGroup() {
        IBioRepGelGroup result = new BioRepGelGroup();
        return result;
    }
}
