package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation for IProject.
 *
 * @author kotte
 */
public class Project implements IProject {

    String owner;
    String name;
    String description;
    List<ILogicalGelGroup> groups;
    List<IPlate96> plates96;
    List<IPlate384> plates384;

    public Project() {
        this.name = "";
        this.description = "";
        this.owner = "";
        this.groups = new ArrayList<ILogicalGelGroup>();
        this.plates96 = new ArrayList<IPlate96>();
        this.plates384 = new ArrayList<IPlate384>();
    }

    @Override
    public List<ILogicalGelGroup> getGelGroups() {
        return groups;
    }

    @Override
    public void setGelGroups(List<ILogicalGelGroup> groups) {
        this.groups = groups;
    }

    @Override
    public void addGelGroup(ILogicalGelGroup group) {
        this.groups.add(group);
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

    @Override
    public void add96Plate(IPlate96 plate) {
        this.plates96.add(plate);
    }

    @Override
    public void add384Plate(IPlate384 plate) {
        this.plates384.add(plate);
    }
}
