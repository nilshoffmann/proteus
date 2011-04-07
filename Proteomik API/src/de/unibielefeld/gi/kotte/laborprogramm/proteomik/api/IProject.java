package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import java.util.List;

/**
 * Proteomic porject containing 2D gels and microplates.
 *
 * @author kotte
 */
public interface IProject {

    public List<ILogicalGelGroup> getGelGroups();

    public void setGelGroups(List<ILogicalGelGroup> groups);

    public void addGelGroup(ILogicalGelGroup group);

    public String getName();

    public void setName(String name);

    public String getOwner();

    public void setOwner(String owner);

    public String getDescription();

    public void setDescription(String description);

    public List<IPlate96> get96Plates();

    public void set96Plates(List<IPlate96> plates);

    public List<IPlate384> get384Plates();

    public void set384Plates(List<IPlate384> plates);

    public void add96Plate(IPlate96 plate);

    public void add384Plate(IPlate384 plate);
}
