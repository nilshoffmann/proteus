package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service = IGel.class)
public class Gel implements IGel {

    IProject parent;
    String name;
    String description;
    String filename;
    List<ISpot> spots;

    public Gel() {
        this.spots = new ArrayList<ISpot>();
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
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public void setFilename(String filename) {
        this.filename = filename;
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
    public List<ISpot> getSpots() {
        return spots;
    }

    @Override
    public void setSpots(List<ISpot> spots) {
        this.spots = spots;
    }
}
