package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import java.util.List;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Default implementation for ISpotGroup.
 *
 * @author kotte
 */
public class SpotGroup implements ISpotGroup {

    IProject parent;
    String label;
    int number;
    List<ISpot> spots;

    public SpotGroup() {
        this.label = "";
        this.spots = new ArrayList<ISpot>();
    }

    @Override
    public IProject getParent() {
        return parent;
    }

    @Override
    public void setParent(IProject parent) {
        this.parent = parent;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public List<ISpot> getSpots() {
        return spots;
    }

    @Override
    public void setSpots(List<ISpot> spots) {
        this.spots = spots;
    }

    @Override
    public void addSpot(ISpot spot) {
        this.spots.add(spot);
    }

    @Override
    public String toString() {
        String str = "spot group #" + number + ": " + label;
        if (!spots.isEmpty()) {
            ISpot spot = null;
            for (Iterator<ISpot> it = spots.iterator(); it.hasNext();) {
                spot = it.next();
                str += "\n    > " + spot.toString();
            }
        } else {
            str += "\n    no spots";
        }
        return str;
    }
}
