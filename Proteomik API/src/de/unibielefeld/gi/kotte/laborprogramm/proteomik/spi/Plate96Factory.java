package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96Factory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;

/**
 * Factory for creating 96 well microplates.
 *
 * @author Konstantin Otte
 */
@org.openide.util.lookup.ServiceProvider(service = IPlate96Factory.class)
public class Plate96Factory implements IPlate96Factory {

    @Override
    public IPlate96 createPlate96() {
        Plate96 plate = new Plate96();
        IWell96[] wells = new IWell96[96];
        for (char row = 'A'; row <= 'H'; row++) {
            for (int column = 1; column <= 12; column++) {
                Well96 well = new Well96();
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
