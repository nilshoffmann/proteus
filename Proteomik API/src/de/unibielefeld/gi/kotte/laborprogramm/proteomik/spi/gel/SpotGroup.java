package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import java.util.List;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpotGroup;
import java.util.ArrayList;

/**
 * Default implementation for ISpotGroup.
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service = ISpotGroup.class)
public class SpotGroup implements ISpotGroup {

    String label;
    int number;
    List<ISpot> spots;

    public SpotGroup() {
        this.label = "";
        this.spots = new ArrayList<ISpot>();
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
}
