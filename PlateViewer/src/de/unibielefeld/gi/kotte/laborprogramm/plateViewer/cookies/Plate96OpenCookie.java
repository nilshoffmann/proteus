/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.plateViewer.cookies;

import de.unibielefeld.gi.kotte.laborprogramm.plateViewer.Plate96ViewerTopComponent;
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
        Plate96ViewerTopComponent gvtc = new Plate96ViewerTopComponent();
        gvtc.open();
    }

}
