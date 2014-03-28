package de.unibielefeld.gi.kotte.laborprogramm.pathway.api.base;

import java.util.List;

/**
 * Factory for creating pathways.
 *
 * @author Konstantin Otte
 */
public interface IPathwayFactory {

    public IPathway createPathway(String id, String name, List<String> synonyms);
}
