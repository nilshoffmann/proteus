package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPlate;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IWell;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IdentificationStatus;

/**
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service = IWell.class)
public class Well implements IWell {

    IPlate parent;
    IdentificationStatus status;
    ISpot spot;
    char row;
    int column;

    public Well() {
        this.parent = null;
        this.status = IdentificationStatus.SELECTED;
        this.spot = null;
        this.row = 'X'; //Well Position X0 fuer ausserhalb einer Platte
        this.column = 0;
    }

    public Well(char posX, int posY, IPlate parent) {
        this.parent = parent;
        this.status = IdentificationStatus.SELECTED;
        this.spot = null;
        this.row = posX;
        this.column = posY;
    }

    @Override
    public IPlate getParent() {
        return parent;
    }

    @Override
    public char getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public String getWellPosition() {
        return "" + row + column;
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
    public void setRow(char posX) {
        this.row = posX;
    }

    @Override
    public void setColumn(int posY) {
        this.column = posY;
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
