package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IWell;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.SpotStatus;

/**
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service = ISpot.class)
public class Spot implements ISpot {

    SpotStatus status;
    IGel parent;
    int posX, posY;
    String label;
    boolean labelDisplayed;
    IWell well;

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public boolean isLabelDisplayed() {
        return labelDisplayed;
    }

    @Override
    public IGel getParent() {
        return parent;
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
    public IWell getWell() {
        return well;
    }

    @Override
    public void setWell(IWell well) {
        this.well = well;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public void setLabelDisplayed(boolean labelDisplayed) {
        this.labelDisplayed = labelDisplayed;
    }

    @Override
    public void setParent(IGel parent) {
        this.parent = parent;
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
}
