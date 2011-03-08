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
    public IWell getWell(char posX, int posY);
    public void setDescription(String description);
    public void setName(String name);
    public void setParent(IProject parent);
    public void setWells(IWell[] wells);
    public void setWell(IWell well, char posX, int posY);

}
