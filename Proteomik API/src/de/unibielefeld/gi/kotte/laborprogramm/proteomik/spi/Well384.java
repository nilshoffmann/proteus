package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.Well384Status;

/**
 * Default implementation for IWell384.
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service = IWell384.class)
public class Well384 implements IWell384 {

    IPlate384 parent;
    Well384Status status;
    IWell96 well96;
    char row;
    int column;

    public Well384() {
        this.parent = null;
        this.status = Well384Status.EMPTY;
        this.well96 = null;
        this.row = 'X'; //Well96 Position X0 fuer ausserhalb einer Platte
        this.column = 0;
    }

    public Well384(char posX, int posY, IPlate384 parent) {
        this.parent = parent;
        this.status = Well384Status.EMPTY;
        this.well96 = null;
        this.row = posX;
        this.column = posY;
    }

    @Override
    public IPlate384 getParent() {
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
    public Well384Status getStatus() {
        return status;
    }

    @Override
    public IWell96 getWell96() {
        return well96;
    }

    @Override
    public void setParent(IPlate384 parent) {
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
    public void setStatus(Well384Status status) {
        this.status = status;
    }

    @Override
    public void setWell96(IWell96 well96) {
        this.well96 = well96;
    }
}
