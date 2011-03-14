package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPlate;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IWell;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IdentificationStatus;

/**
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service=IWell.class)
public class Well implements IWell{
    IPlate parent;
    IdentificationStatus status;
    ISpot spot;
    char posX;
    int posY;

    @Override
    public IPlate getParent() {
        return parent;
    }

    @Override
    public char getPosX() {
        return posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public String getWellPosition() {
        return "" + posX + posY;
    }

    @Override
    public IdentificationStatus getStatus() {
        return status;
    }

    @Override
    public ISpot getSpot() {
        return spot;
    }

    @Override
    public void setParent(IPlate parent) {
        this.parent = parent;
    }

    @Override
    public void setPosX(char posX) {
        this.posX = posX;
    }

    @Override
    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public void setStatus(IdentificationStatus status) {
        this.status = status;
    }

    @Override
    public void setSpot(ISpot spot) {
        this.spot = spot;
    }
}
