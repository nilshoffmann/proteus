/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.registry;

import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.GelViewerTopComponent;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO refactor to TopComponentRegistry
 * @author hoffmann
 */
public class GelViewerRegistry {

    private static GelViewerRegistry gelViewerRegistry = null;

    public static GelViewerRegistry getInstance() {
        if(gelViewerRegistry==null) {
            gelViewerRegistry = new GelViewerRegistry();
        }
        return gelViewerRegistry;
    }

    private Map<IGel,GelViewerTopComponent> gelToTopComponent = new HashMap<IGel,GelViewerTopComponent>();

    private GelViewerRegistry() {

    }

    public GelViewerTopComponent openTopComponent(IGel gel) {
        GelViewerTopComponent tc;
        if(gelToTopComponent.containsKey(gel)) {
            System.out.println("Found gel viewer instance");
            tc = gelToTopComponent.get(gel);
            tc.requestActive();
        }else{
            System.out.println("Creating new gel viewer instance");
            tc = new GelViewerTopComponent();
            tc.open();
        }
        return tc;
    }

    public void closeTopComponent(IGel gel) {
        gelToTopComponent.remove(gel);
    }


}
