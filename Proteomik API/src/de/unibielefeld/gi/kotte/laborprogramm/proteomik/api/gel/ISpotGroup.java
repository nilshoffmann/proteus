package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel;

import java.util.List;

/**
 * A group of spot objects representing the same spot on different gels.
 *
 * @author kotte
 */
public interface ISpotGroup {
    public String getLabel();

    public int getNumber();

    public void setLabel(String label);

    public void setNumber(int number);

    public List<ISpot> getSpots();

    public void addSpot(ISpot spot);

    public void setSpots(List<ISpot> spots);
}
