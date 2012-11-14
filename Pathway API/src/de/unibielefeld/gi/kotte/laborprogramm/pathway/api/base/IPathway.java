package de.unibielefeld.gi.kotte.laborprogramm.pathway.api.base;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IUniqueObject;
import java.util.List;

public interface IPathway extends IPropertyChangeSource, IUniqueObject {

    public String getMetacycId();

    public String getName();

    public List<String> getSynonyms();
}
