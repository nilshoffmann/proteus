package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.SpotStatus;

/**
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service=ISpot.class)
public class Spot implements ISpot{

    SpotStatus status;
    IGel parent;
    int posX, posY;
    String label;
    boolean labelDisplayed;

    public String getLabel() {
        return label;
    }

    public boolean isLabelDisplayed() {
        return labelDisplayed;
    }

    public IGel getParent() {
        return parent;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public SpotStatus getStatus() {
        return status;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLabelDisplayed(boolean labelDisplayed) {
        this.labelDisplayed = labelDisplayed;
    }

    public void setParent(IGel parent) {
        this.parent = parent;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setStatus(SpotStatus status) {
        this.status = status;
    }

}
