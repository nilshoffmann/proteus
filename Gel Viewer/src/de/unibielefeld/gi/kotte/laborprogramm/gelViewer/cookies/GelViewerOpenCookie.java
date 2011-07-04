/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.cookies;

import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.GelViewerTopComponent;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IGelOpenCookie;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author hoffmann
 */
@ServiceProvider(service=IGelOpenCookie.class)
public class GelViewerOpenCookie implements IGelOpenCookie {

    @Override
    public void open() {
        GelViewerTopComponent gvtc = new GelViewerTopComponent();
        gvtc.open();
    }

}
