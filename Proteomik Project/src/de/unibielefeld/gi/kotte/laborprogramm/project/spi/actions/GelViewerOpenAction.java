/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions;

import de.unibielefeld.gi.kotte.laborprogramm.centralLookup.CentralLookup;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IGelOpenCookie;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.openide.util.Lookup;

public final class GelViewerOpenAction implements ActionListener {

//    private final OpenCookie context;
    private final IGel context;

    public GelViewerOpenAction(IGel context) {
        assert context != null;
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        CentralLookup.getDefault().add(this.context);
        IGelOpenCookie igoc = Lookup.getDefault().lookup(IGelOpenCookie.class);
        igoc.open();
    }
}
