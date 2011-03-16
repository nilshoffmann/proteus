package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPlate;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IWell;

/**
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service = IPlate.class)
public class Plate96 implements IPlate {

    String name;
    String description;
    IWell[] wells;
    IProject parent;

    public Plate96() {
        initiateWells();
    }

    private void initiateWells() {
        this.wells = new IWell[96];
        int index = 0;
        for (char row = 'A'; row <= 'H'; row++) {
            for (int column = 1; column <= 12; column++) {
                wells[index] = new Well(row, column, this);
//                wells[index] = WellFactory.getDefault();
//                wells[index].setParent(this);
//                wells[index].setRow(row);
//                wells[index].setColumn(column);
//                wells[index].setStatus(IdentificationStatus.SELECTED);
                index++;
            }
        }
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IProject getParent() {
        return parent;
    }

    @Override
    public IWell[] getWells() {
        return wells;
    }

    @Override
    public IWell getWell(char row, int column) {
        return wells[posToIndex(row, column)];
    }

    @Override
    public int getXdimension() {
        return 12;
    }

    @Override
    public int getYdimension() {
        return 8;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setParent(IProject parent) {
        this.parent = parent;
    }

    @Override
    public void setWells(IWell[] wells) {
        this.wells = wells;
    }

    @Override
    public void setWell(IWell well, char row, int column) {
        this.wells[posToIndex(row, column)] = well;
    }

    private static int posToIndex(char row, int column) {
        assert (column >= 1 && column <= 12);
        //setze x auf 0 fuer A oder a, 1 fuer B oder b, etc.
        int x = row - 65;
        if (x >= 32) {
            x -= 32;
        }
        assert (x >= 0 && x <= 7);

        return x * 12 + column - 1;
    }
}
