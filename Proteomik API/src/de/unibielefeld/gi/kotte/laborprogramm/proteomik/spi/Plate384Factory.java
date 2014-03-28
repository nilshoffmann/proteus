package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384Factory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;

/**
 * Factory for creating 384 well microplates.
 *
 * @author Konstantin Otte
 */
@org.openide.util.lookup.ServiceProvider(service = IPlate384Factory.class)
public class Plate384Factory implements IPlate384Factory {

    @Override
    public IPlate384 createPlate384() {
        Plate384 plate = new Plate384();
        IWell384[] wells = new IWell384[384];
        for (char row = 'A'; row <= 'P'; row++) {
            for (int column = 1; column <= 24; column++) {
                Well384 well = new Well384(row, column, plate);
                well.setRow(row);
                well.setColumn(column);
                well.setParent(plate);
                wells[plate.posToIndex(row, column)] = well;
            }
        }
        plate.setWells(wells);
        return plate;
    }
}
