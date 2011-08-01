package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;

/**
 * A 384 well microplate.
 *
 * @author kotte
 */
public interface IPlate384 extends IPropertyChangeSource {

    public String getDescription();

    public String getName();

    public IProject getParent();

    public IWell384[] getWells();

    public IWell384 getWell(char row, int column);

    public int getXdimension();

    public int getYdimension();

    public void setDescription(String description);

    public void setName(String name);

    public void setParent(IProject parent);

    public void setWells(IWell384[] wells);

    public void setWell(IWell384 well, char row, int column);

    public int posToIndex(char row, int column);
}
