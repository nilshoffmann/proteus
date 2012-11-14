package de.unibielefeld.gi.kotte.laborprogramm.pathway.spi.base;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.base.IPathway;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.base.IPathwayFactory;
import java.util.List;

/**
 * Factory for creating pathways.
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service = IPathwayFactory.class)
public class PathwayFactory implements IPathwayFactory {

    @Override
    public IPathway createPathway(String id, String name, List<String> synonyms) {
        Pathway result = new Pathway();
        result.setId(id);
        result.setName(name);
        result.setSynonyms(synonyms);
        return result;
    }
}
