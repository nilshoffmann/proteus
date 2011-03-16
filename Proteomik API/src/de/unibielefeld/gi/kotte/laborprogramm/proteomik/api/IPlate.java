package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api;

/**
 * A microplate containing wells.
 *
 * @author kotte
 */
public interface IPlate {

    public String getDescription();

    public String getName();

    public IProject getParent();

    public IWell[] getWells();

    public IWell getWell(char row, int column);

    public int getXdimension();

    public int getYdimension();

    public void setDescription(String description);

    public void setName(String name);

    public void setParent(IProject parent);

    public void setWells(IWell[] wells);

    public void setWell(IWell well, char row, int column);
}
