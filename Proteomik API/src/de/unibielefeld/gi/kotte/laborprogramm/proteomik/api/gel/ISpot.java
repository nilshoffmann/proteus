package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;

/**
 * A spot on a 2D gel.
 *
 * @author kotte
 */
public interface ISpot {

    public String getLabel();

    public int getNumber();

    public IGel getGel();

    public ISpotGroup getGroup();

    public double getPosX();

    public double getPosY();

    public SpotStatus getStatus();

    public IWell96 getWell();

    public void setWell(IWell96 well);

    public void setLabel(String label);

    public void setNumber(int number);

    public void setGel(IGel gel);

    public void setGroup(ISpotGroup group);

    public void setPosX(double posX);

    public void setPosY(double posY);

    public void setStatus(SpotStatus status);
}
