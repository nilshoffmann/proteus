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
public class Plate96 implements IPlate{

    String name;
    String description;
    IWell[] wells;
    IProject parent;

    public Plate96() {
        initiateWells();
    }

    private void initiateWells() {
        this.wells = new IWell[96];
        for (char posX = 'A'; posX <= 'H'; posX++) {
            for (int posY = 1; posY <= 12; posY++) {
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
    public void setWell(IWell well, char posX, int posY) {
        this.wells[posToIndex(posX, posY)] = well;
    }

    private static int posToIndex(char posX, int posY) {
        assert(posY>0 && posY<13);
        //setze x auf 0 fuer A oder a, 1 fuer B oder b, etc.
        int x = posX-65;
        if (x>=32) x-=32;

        return x*8+posY-1;
    }
    
}
