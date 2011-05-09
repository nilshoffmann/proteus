package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import java.util.List;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Default implementation for ILogicalGelGroup.
 *
 * @author kotte
 */
public class LogicalGelGroup implements ILogicalGelGroup {

    IProject parent;
    String name;
    String description;
    List<IBioRepGelGroup> groups;

    public LogicalGelGroup() {
        this.name = "";
        this.description = "";
        this.groups = new ArrayList<IBioRepGelGroup>();
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public List<IBioRepGelGroup> getGelGroups() {
        return groups;
    }

    @Override
    public void setGelGroups(List<IBioRepGelGroup> groups) {
        this.groups = groups;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public IProject getParent() {
        return parent;
    }

    @Override
    public void setParent(IProject parent) {
        this.parent = parent;
    }

    @Override
    public void addGelGroup(IBioRepGelGroup group) {
        this.groups.add(group);
    }

    @Override
    public String toString() {
        String str = "logical gel group '" + name + "': " + description;
        if (!groups.isEmpty()) {
            IBioRepGelGroup group = null;
            for (Iterator<IBioRepGelGroup> it = groups.iterator(); it.hasNext();) {
                group = it.next();
                str += "\n    > " + group.toString();
            }
        } else {
            str += "\n    no bio rep gel groups";
        }
        return str;
    }
}
