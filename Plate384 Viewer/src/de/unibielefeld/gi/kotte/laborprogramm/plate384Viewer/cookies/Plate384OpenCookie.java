package de.unibielefeld.gi.kotte.laborprogramm.plate384Viewer.cookies;

import de.unibielefeld.gi.kotte.laborprogramm.plate384Viewer.Plate384ViewerTopComponent;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IPlate384OpenCookie;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.topComponentRegistry.api.IRegistryFactory;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Konstantin Otte
 */
@ServiceProvider(service=IPlate384OpenCookie.class)
public class Plate384OpenCookie implements IPlate384OpenCookie {

    @Override
    public void open() {
        IPlate384 plate = Utilities.actionsGlobalContext().lookup(IPlate384.class);
        if(plate != null) {
            Lookup.getDefault().lookup(IRegistryFactory.class).getDefault().openTopComponentFor(plate,Plate384ViewerTopComponent.class);
        }
    }
}
