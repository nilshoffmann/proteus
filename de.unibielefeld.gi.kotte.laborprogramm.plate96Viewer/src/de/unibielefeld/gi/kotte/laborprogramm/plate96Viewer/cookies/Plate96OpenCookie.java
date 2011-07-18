package de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer.cookies;

import de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer.Plate96ViewerTopComponent;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IPlate96OpenCookie;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author hoffmann
 */
@ServiceProvider(service=IPlate96OpenCookie.class)
public class Plate96OpenCookie implements IPlate96OpenCookie{

    @Override
    public void open() {
        Plate96ViewerTopComponent pvtc = new Plate96ViewerTopComponent();
        pvtc.open();
    }

}
