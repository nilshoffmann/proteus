package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPlate;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IWell;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IdentificationStatus;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.WellFactory;

/**
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service=IPlate.class)
public class Plate384 implements IPlate{

    String name;
    String description;
    IWell[] wells;
    IProject parent;

    public Plate384() {
        initiateWells();
    }

    private void initiateWells() {
        this.wells = new IWell[384];
        for (char posX = 'A'; posX <= 'P'; posX++) {
            for (int posY = 1; posY <= 24; posY++) {
                int index = posToIndex(posX, posY);
                wells[index] = WellFactory.getDefault();
                wells[index].setParent(this);
                wells[index].setPosX(posX);
                wells[index].setPosY(posY);
                wells[index].setStatus(IdentificationStatus.SELECTED);
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
    public IWell getWell(char posX, int posY) {
        return wells[posToIndex(posX, posY)];
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
    public void setWells(IWell[] wells) {
        this.wells = wells;
    }

    @Override
    public void setWell(IWell well, char posX, int posY) {
        this.wells[posToIndex(posX, posY)] = well;
    }

    private static int posToIndex(char posX, int posY) {
        //setze x auf 0 fuer A oder a, 1 fuer B oder b, etc.
        int x = posX-65;
        if (x>=32) x-=32;

        return x*16+posY-1;
    }
}
