package de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer.cookies;

import de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer.Plate96ViewerTopComponent;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IPlate96OpenCookie;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.topComponentRegistry.api.IRegistryFactory;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author hoffmann
 */
@ServiceProvider(service=IPlate96OpenCookie.class)
public class Plate96OpenCookie implements IPlate96OpenCookie{

    @Override
    public void open() {
        IPlate96 plate = Utilities.actionsGlobalContext().lookup(IPlate96.class);
        if(plate != null) {
            Lookup.getDefault().lookup(IRegistryFactory.class).getDefault().openTopComponentFor(plate,Plate96ViewerTopComponent.class);
        }
    }

}
