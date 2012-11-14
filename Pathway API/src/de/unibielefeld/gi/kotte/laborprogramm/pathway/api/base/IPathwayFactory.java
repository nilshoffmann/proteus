package de.unibielefeld.gi.kotte.laborprogramm.pathway.api.base;

import java.util.List;

/**
 * Factory for creating pathways.
 *
 * @author kotte
 */
public interface IPathwayFactory {

    public IPathway createPathway(String id, String name, List<String> synonyms);
}
