/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.cookies;

import de.unibielefeld.gi.kotte.laborprogramm.centralLookup.CentralLookup;
import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.registry.GelViewerRegistry;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IGelOpenCookie;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author hoffmann
 */
@ServiceProvider(service=IGelOpenCookie.class)
public class GelViewerOpenCookie implements IGelOpenCookie {

    @Override
    public void open() {
        IGel gel = CentralLookup.getDefault().lookup(IGel.class);
        if(gel==null) {
            throw new NullPointerException("Gel instance in actionsGlobalContext was null!");
        }else{
            System.out.println("Found gel instance in central lookup: "+gel);
        }
        CentralLookup.getDefault().remove(gel);
        GelViewerRegistry.getInstance().openTopComponent(gel);
    }

}
