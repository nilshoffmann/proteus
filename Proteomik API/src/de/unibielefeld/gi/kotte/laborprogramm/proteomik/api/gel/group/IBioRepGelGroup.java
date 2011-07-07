package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;
import java.util.List;

/**
 * A group containing biological replications of groups of technical
 * replications of 2D gels.
 *
 * @author kotte
 */
public interface IBioRepGelGroup extends IPropertyChangeSource{

    public ILogicalGelGroup getParent();

    public void setParent(ILogicalGelGroup parent);

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public List<ITechRepGelGroup> getGelGroups();

    public void addGelGroup(ITechRepGelGroup group);

    public void setGelGroups(List<ITechRepGelGroup> groups);
}
