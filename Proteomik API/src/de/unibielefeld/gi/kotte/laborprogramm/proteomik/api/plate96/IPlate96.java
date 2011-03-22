package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;

/**
 * A 96 well microplate.
 *
 * @author kotte
 */
public interface IPlate96 {

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
}
