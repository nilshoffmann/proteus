package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IUniqueObject;
import java.util.List;

/**
 * A group containing biological replications of groups of technical
 * replications of 2D gels.
 *
 * @author kotte
 */
public interface IBioRepGelGroup extends IPropertyChangeSource, IUniqueObject {

    public static final String PROPERTY_PARENT = "parent";

    public static final String PROPERTY_NAME = "name";

    public static final String PROPERTY_DESCRIPTION = "description";

    public static final String PROPERTY_GROUPS = "gel groups";

    public ILogicalGelGroup getParent();

    public void setParent(ILogicalGelGroup parent);

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public List<ITechRepGelGroup> getGelGroups();

    public void addGelGroup(ITechRepGelGroup group);

    public void setGelGroups(List<ITechRepGelGroup> groups);

    public String toFullyRecursiveString();
}
