package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IUniqueObject;

/**
 * A 96 well microplate.
 *
 * @author Konstantin Otte
 */
public interface IPlate96 extends IPropertyChangeSource, IUniqueObject {

    public static final String PROPERTY_DESCRIPTION = "description";

    public static final String PROPERTY_NAME = "name";

    public static final String PROPERTY_PARENT = "parent";

    public static final String PROPERTY_WELLS = "wells";

    public String getDescription();

    public String getName();

    public IProject getParent();

    public IWell96[] getWells();

    public IWell96 getWell(char row, int column);

    public int getXdimension();

    public int getYdimension();

    public void setDescription(String description);

    public void setName(String name);

    public void setParent(IProject parent);

    public void setWells(IWell96[] wells);

    public void setWell(IWell96 well, char row, int column);

    public int posToIndex(char row, int column);

    public String toFullyRecursiveString();
}
