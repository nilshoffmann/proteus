package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.SpotStatus;

/**
 * Default implementation for ISpot.
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service = ISpot.class)
public class Spot implements ISpot {

    SpotStatus status;
    IGel gel;
    ISpotGroup group;
    int posX, posY;
    String label;
    int number;
    IWell96 well;

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public IGel getGel() {
        return gel;
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public SpotStatus getStatus() {
        return status;
    }

    @Override
    public IWell96 getWell() {
        return well;
    }

    @Override
    public void setWell(IWell96 well) {
        this.well = well;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public void setGel(IGel parent) {
        this.gel = parent;
    }

    @Override
    public void setPosX(int posX) {
        this.posX = posX;
    }

    @Override
    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public void setStatus(SpotStatus status) {
        this.status = status;
    }

    @Override
    public ISpotGroup getGroup() {
        return this.group;
    }

    @Override
    public void setGroup(ISpotGroup group) {
        this.group = group;
    }
}
