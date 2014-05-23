package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpotFactory;

/**
 * Factory for creating new gel spots.
 *
 * @author Konstantin Otte
 */
@org.openide.util.lookup.ServiceProvider(service = ISpotFactory.class)
public class SpotFactory implements ISpotFactory {

    @Override
    public ISpot createSpot() {
        ISpot result = new Spot();
        result.setLabel("");
        return result;
    }
}
