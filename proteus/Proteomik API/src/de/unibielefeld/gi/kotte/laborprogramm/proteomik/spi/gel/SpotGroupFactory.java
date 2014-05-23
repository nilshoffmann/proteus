package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroupFactory;

/**
 * Factory for creating new spot groups.
 *
 * @author Konstantin Otte
 */
@org.openide.util.lookup.ServiceProvider(service = ISpotGroupFactory.class)
public class SpotGroupFactory implements ISpotGroupFactory {

    @Override
    public ISpotGroup createSpotGroup() {
        ISpotGroup result = new SpotGroup();
        result.setLabel("");
        return result;
    }
}
