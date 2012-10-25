package de.unibielefeld.gi.kotte.laborprogramm.pathway.spi;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPathway;
import java.util.List;

/**
 * Default implementation for IPathway.
 * 
 * @author kotte
 */
public class Pathway implements IPathway {

    private final String id;
    private final String name;
    private final List<String> synonyms;

    public Pathway(String id, String name, List<String> synonyms) {
        this.id = id;
        this.name = name;
        this.synonyms = synonyms;
    }
    
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getSynonyms() {
        return synonyms;
    }
}
