package de.unibielefeld.gi.kotte.laborprogramm.pathway.spi.sbml;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.sbml.IPathwayMap;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.sbml.IPathwayMapFactory;

@org.openide.util.lookup.ServiceProvider(service = IPathwayMapFactory.class)
public class PathwayMapFactory implements IPathwayMapFactory {

    @Override
    public IPathwayMap createPathwayMap() {
        PathwayMap result = new PathwayMap();
        return result;
    }
}
