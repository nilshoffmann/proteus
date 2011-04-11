package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroupFactory;

/**
 * Factory for creating new groups of technical replications of gels.
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service = ITechRepGelGroupFactory.class)
public class TechRepGelGroupFactory implements ITechRepGelGroupFactory{

    @Override
    public ITechRepGelGroup createTechRepGelGroupFactory() {
        ITechRepGelGroup result = new TechRepGelGroup();
        return result;
    }
}
