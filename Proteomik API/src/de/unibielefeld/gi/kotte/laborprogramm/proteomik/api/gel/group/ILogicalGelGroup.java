package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import java.util.List;

/**
 * A group containing logically different gel groups.
 *
 * @author kotte
 */
public interface ILogicalGelGroup {

    public IProject getParent();

    public void setParent(IProject parent);

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public List<IBioRepGelGroup> getGelGroups();

    public void addGelGroup(IBioRepGelGroup group);

    public void setGelGroups(List<IBioRepGelGroup> groups);
}
