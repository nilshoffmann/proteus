package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import java.util.List;

/**
 * A group of spot objects representing the same spot on different gels.
 *
 * @author kotte
 */
public interface ISpotGroup extends IPropertyChangeSource {

    public String getLabel();

    public int getNumber();

    public IProject getParent();

    public void setParent(IProject parent);

    public void setLabel(String label);

    public void setNumber(int number);

    public List<ISpot> getSpots();

    public void addSpot(ISpot spot);

    public void setSpots(List<ISpot> spots);
}
