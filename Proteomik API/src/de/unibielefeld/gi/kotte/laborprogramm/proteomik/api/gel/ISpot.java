package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;

/**
 * A spot on a 2D gel.
 *
 * @author kotte
 */
public interface ISpot {

    public String getLabel();

    public int getNumber();

    public IGel getParent();

    public int getPosX();

    public int getPosY();

    public SpotStatus getStatus();

    public IWell96 getWell();

    public void setWell(IWell96 well);

    public void setLabel(String label);

    public void setNumber(int number);

    public void setParent(IGel parent);

    public void setPosX(int posX);

    public void setPosY(int posY);

    public void setStatus(SpotStatus status);
}
