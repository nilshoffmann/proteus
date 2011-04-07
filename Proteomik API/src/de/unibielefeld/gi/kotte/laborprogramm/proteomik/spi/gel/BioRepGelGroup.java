package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import java.util.List;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ITechRepGelGroup;
import java.util.ArrayList;

/**
 * Default implementation for IBioRepGelGroup.
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service = IBioRepGelGroup.class)
public class BioRepGelGroup implements IBioRepGelGroup {

    ILogicalGelGroup parent;
    String name;
    String description;
    List<ITechRepGelGroup> groups;

    public BioRepGelGroup() {
        this.name = "";
        this.description = "";
        this.groups = new ArrayList<ITechRepGelGroup>();
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
    public List<ITechRepGelGroup> getGelGroups() {
        return groups;
    }

    @Override
    public void setGelGroups(List<ITechRepGelGroup> groups) {
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
    public ILogicalGelGroup getParent() {
        return parent;
    }

    @Override
    public void setParent(ILogicalGelGroup parent) {
        this.parent = parent;
    }

    @Override
    public void addGelGroup(ITechRepGelGroup group) {
        this.groups.add(group);
    }
}
