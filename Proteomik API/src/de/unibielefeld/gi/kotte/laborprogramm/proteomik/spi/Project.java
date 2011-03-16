package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPlate;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import java.util.List;

/**
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service = IProject.class)
public class Project implements IProject {

    String owner;
    String name;
    String description;
    List<IGel> gels;
    List<IPlate> plates;

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
    public String getOwner() {
        return owner;
    }

    @Override
    public void setOwner(String owner) {
        this.owner = owner;
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
    public List<IPlate> getPlates() {
        return plates;
    }

    @Override
    public void setPlates(List<IPlate> plates) {
        this.plates = plates;
    }
}
