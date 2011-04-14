package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.Well96Status;
import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation for IWell96.
 *
 * @author kotte
 */
public class Well96 implements IWell96 {

    IPlate96 parent;
    Well96Status status;
    ISpot spot;
    char row;
    int column;
    List<IWell384> wells384;

    public Well96() {
        this.parent = null;
        this.status = Well96Status.EMPTY;
        this.spot = null;
        this.row = 'X'; //Well96 Position X0 fuer ausserhalb einer Platte
        this.column = 0;
        this.wells384 = new ArrayList<IWell384>();
    }

    public Well96(char posX, int posY, IPlate96 parent) {
        this.parent = parent;
        this.status = Well96Status.EMPTY;
        this.spot = null;
        this.row = posX;
        this.column = posY;
        this.wells384 = new ArrayList<IWell384>();
    }

    @Override
    public IPlate96 getParent() {
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
    public Well96Status getStatus() {
        return status;
    }

    @Override
    public ISpot getSpot() {
        return spot;
    }

    @Override
    public void setParent(IPlate96 parent) {
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
    public void setStatus(Well96Status status) {
        this.status = status;
    }

    @Override
    public void setSpot(ISpot spot) {
        this.spot = spot;
    }

    @Override
    public List<IWell384> get384Wells() {
        return wells384;
    }

    @Override
    public void set384Wells(List<IWell384> wells) {
        this.wells384 = wells;
    }

    @Override
    public void add384Well(IWell384 well) {
        this.wells384.add(well);
    }

    @Override
    public String toString() {
        return "well " + row + column + " is " + status;
    }
}
