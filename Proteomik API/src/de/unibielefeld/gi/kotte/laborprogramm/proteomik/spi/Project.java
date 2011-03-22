package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
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
    List<IPlate96> plates96;
    List<IPlate384> plates384;

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
    public List<IPlate96> get96Plates() {
        return plates96;
    }

    @Override
    public void set96Plates(List<IPlate96> plates) {
        this.plates96 = plates;
    }

    @Override
    public List<IPlate384> get384Plates() {
        return plates384;
    }

    @Override
    public void set384Plates(List<IPlate384> plates) {
        this.plates384 = plates;
    }
}
