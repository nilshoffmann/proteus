/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.cookies;

import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.GelViewerTopComponent;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IGelOpenCookie;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.topComponentRegistry.api.IRegistryFactory;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author hoffmann
 */
@ServiceProvider(service=IGelOpenCookie.class)
public class GelViewerOpenCookie implements IGelOpenCookie {

    @Override
    public void open() {
        IGel gel = Utilities.actionsGlobalContext().lookup(IGel.class);
        if(gel != null) {
            Lookup.getDefault().lookup(IRegistryFactory.class).getDefault().openTopComponent(gel,GelViewerTopComponent.class);
        }
    }

}
