package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import java.util.List;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ITechRepGelGroup;
import java.util.ArrayList;

/**
 * Default implementation for ITechRepGelGroup.
 *
 * @author kotte
 */
public class TechRepGelGroup implements ITechRepGelGroup {

    IBioRepGelGroup parent;
    String name;
    String description;
    List<IGel> gels;

    public TechRepGelGroup() {
        this.name = "";
        this.description = "";
        this.gels = new ArrayList<IGel>();
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
    public List<IGel> getGels() {
        return gels;
    }

    @Override
    public void setGels(List<IGel> gels) {
        this.gels = gels;
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
    public IBioRepGelGroup getParent() {
        return parent;
    }

    @Override
    public void setParent(IBioRepGelGroup parent) {
        this.parent = parent;
    }

    @Override
    public void addGel(IGel gel) {
        this.gels.add(gel);
    }
}
