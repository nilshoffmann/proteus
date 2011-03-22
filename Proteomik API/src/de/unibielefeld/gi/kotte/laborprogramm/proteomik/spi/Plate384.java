package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;

/**
 * Default implementation for IPlate384.
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service = IPlate384.class)
public class Plate384 implements IPlate384 {

    String name;
    String description;
    IWell384[] wells;
    IProject parent;

    public Plate384() {
        initiateWells();
    }

    private void initiateWells() {
        this.wells = new IWell384[384];
        for (char row = 'A'; row <= 'P'; row++) {
            for (int column = 1; column <= 24; column++) {
                wells[posToIndex(row, column)] = new Well384(row, column, this);
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
    public IWell384[] getWells() {
        return wells;
    }

    @Override
    public IWell384 getWell(char row, int column) {
        return wells[posToIndex(row, column)];
    }

    @Override
    public int getXdimension() {
        return 24;
    }

    @Override
    public int getYdimension() {
        return 16;
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
    public void setWells(IWell384[] wells) {
        this.wells = wells;
    }

    @Override
    public void setWell(IWell384 well, char row, int column) {
        this.wells[posToIndex(row, column)] = well;
    }

    private static int posToIndex(char row, int column) {
        assert (column >= 1 && column <= 24);
        //setze x auf 0 fuer A oder a, 1 fuer B oder b, etc.
        int x = row - 65;
        if (x >= 32) {
            x -= 32;
        }
        assert (x >= 0 && x <= 15);

        return x + column * 16 - 1;
    }
}
