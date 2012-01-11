package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IUniqueObject;
import java.util.List;

/**
 * A group containing logically different gel groups.
 *
 * @author kotte
 */
public interface ILogicalGelGroup extends IPropertyChangeSource, IUniqueObject {

    public static final String PROPERTY_PARENT = "parent";

    public static final String PROPERTY_NAME = "name";

    public static final String PROPERTY_DESCRIPTION = "description";

    public static final String PROPERTY_GROUPS = "gel groups";

    public IProject getParent();

    public void setParent(IProject parent);

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public List<IBioRepGelGroup> getGelGroups();

    public void addGelGroup(IBioRepGelGroup group);

    public void setGelGroups(List<IBioRepGelGroup> groups);

    public String toFullyRecursiveString();
}
