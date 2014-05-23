package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import java.util.List;

/**
 * Proteomic porject containing 2D gels and microplates.
 *
 * @author Konstantin Otte
 */
public interface IProject extends IPropertyChangeSource, IUniqueObject {

    public static final String PROPERTY_DESCRIPTION = "description";

    public static final String PROPERTY_NAME = "name";

    public static final String PROPERTY_GELGROUPS = "logical gel groups";

    public static final String PROPERTY_SPOTGROUPS = "spot groups";

    public static final String PROPERTY_OWNER = "Project owner";

    public static final String PROPERTY_PLATES96 = "96 well plates";

    public static final String PROPERTY_PLATES384 = "384 well plates";

    public List<ILogicalGelGroup> getGelGroups();

    public void setGelGroups(List<ILogicalGelGroup> groups);

    public void addGelGroup(ILogicalGelGroup group);

    public List<ISpotGroup> getSpotGroups();

    public void setSpotGroups(List<ISpotGroup> groups);

    public void addSpotGroup(ISpotGroup group);

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

    public void remove384Plate(IPlate384 plate);

    public void remove96Plate(IPlate96 plate);

    public String toFullyRecursiveString();
}