package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IUniqueObject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import java.io.File;
import java.util.List;

/**
 * A 2D protein gel containing gel spots.
 *
 * @author kotte
 */
public interface IGel extends IPropertyChangeSource, IUniqueObject {

    public static final String PROPERTY_PARENT = "parent";

    public static final String PROPERTY_DESCRIPTION = "description";

    public static final String PROPERTY_FILENAME = "image file name";

    public static final String PROPERTY_LOCATION = "location";

    public static final String PROPERTY_NAME = "name";

    public static final String PROPERTY_SPOTS = "gel spots";

    public static final String PROPERTY_VIRTUAL = "virtual";

    public ITechRepGelGroup getParent();

    public void setParent(ITechRepGelGroup parent);

    public String getDescription();

    public void setDescription(String description);

    public String getFilename();

    public void setFilename(String filename);

    public File getLocation();

    public void setLocation(File file);

    public String getName();

    public void setName(String name);

    public List<ISpot> getSpots();

    public void addSpot(ISpot spot);

    public void setSpots(List<ISpot> spots);

    public boolean isVirtual();
    
    public void setVirtual(boolean virtual);

    public String toFullyRecursiveString();
}
